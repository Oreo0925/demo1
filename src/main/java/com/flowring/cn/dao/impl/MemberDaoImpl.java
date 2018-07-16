package com.flowring.cn.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import com.flowring.cn.dao.MemberDao;
import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.RoleGroup;

@Repository
public class MemberDaoImpl extends NamedParameterJdbcDaoSupport implements
		MemberDao {

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private final static String INSERT_MEM = "INSERT INTO member (full_id, login_id, name, email, parent_id, password, description, af_member_id, address, enable, last_login_ip, last_login_time, email_token, sso, oauth_token, event_priority, phone, deleted) VALUES(:full_id, :login_id, :name, :email, :parent_id, :password, :description, :af_member_id, :address, :enable, :last_login_ip, :last_login_time, :email_token, :sso, :oauth_token, :event_priority, :phone, :deleted)";
	private final static String UPDATE_MEM = "UPDATE member SET full_id=:full_id, login_id=:login_id, name=:name, email=:email, parent_id=:parent_id, password=:password, description=:description, af_member_id=:af_member_id, address=:address, enable=:enable, last_login_ip=:last_login_ip, last_login_time=:last_login_time, email_token=:email_token, sso=:sso, oauth_token=:oauth_token, event_priority=:event_priority, phone=:phone, deleted=:deleted WHERE id=:id";
	private final static String UPDATE_MEM_NO_PW = "UPDATE member SET full_id=:full_id, login_id=:login_id, name=:name, email=:email, parent_id=:parent_id, description=:description, af_member_id=:af_member_id, address=:address, enable=:enable, last_login_ip=:last_login_ip, last_login_time=:last_login_time, email_token=:email_token, sso=:sso, oauth_token=:oauth_token, event_priority=:event_priority, phone=:phone, deleted=:deleted WHERE id=:id";
	private final static String GET_MEM_BY_ID = "SELECT * FROM member WHERE id=:id ORDER BY id DESC";
	private final static String GET_MEM_BY_LOGIN_ID = "SELECT * FROM member WHERE login_id=:login_id";
	private final static String GET_ALL_MEM = "SELECT * FROM member ORDER BY id DESC";
	private final static String GET_MEM_BY_PARENT_ID = "SELECT * FROM member WHERE parent_id=:parent_id ORDER BY id DESC";
	private final static String GET_MEM_BY_EMAIL_TOKEN = "SELECT * FROM member WHERE email_token=:email_token";
	private final static String GET_MEM_LIST_BY_PROP_VALUE = "SELECT m.id, m.full_id, m.login_id, m.name, m.email, m.parent_id, m.password, m.description, m.af_member_id, m.address, m.enable, m.last_login_ip, m.last_login_time, m.email_token, m.sso, m.oauth_token, m.event_priority, m.phone, m.deleted FROM member m LEFT OUTER JOIN member_properties prop ON m.id = prop.member_id WHERE english_name = :english_name AND value = :value";
	private final static String GET_MEM_LIST_BY_PROP_VALUE_AND_GROUP_ID = "SELECT mmrr.id, mmrr.full_id, mmrr.login_id, mmrr.name, mmrr.email, mmrr.parent_id, mmrr.password, mmrr.description, mmrr.af_member_id, mmrr.address, mmrr.enable, mmrr.last_login_ip, mmrr.last_login_time, mmrr.email_token, mmrr.sso, mmrr.oauth_token, mmrr.event_priority, mmrr.phone, mmrr.deleted FROM (SELECT g.id group_id, rg.role_id  role_id FROM cn_group g LEFT OUTER JOIN role_group rg ON g.id = rg.group_id WHERE g.id = :group_id ) rrgg LEFT JOIN (SELECT m.id id, m.full_id full_id, m.login_id login_id, m.name name, m.email email, m.parent_id parent_id, m.password password, m.description description, m.af_member_id af_member_id, m.address address, m.enable enable, m.last_login_ip last_login_ip, m.last_login_time last_login_time, m.email_token email_token, m.sso sso, m.oauth_token oauth_token, rm.role_id role_id, m.event_priority, m.phone, m.deleted FROM member m LEFT OUTER JOIN member_role rm ON m.id = rm.member_id ) mmrr ON rrgg.role_id = mmrr.role_id LEFT JOIN member_properties prop ON mmrr.id = prop.member_id WHERE english_name = :english_name AND value = :value";
	private final static String UPDATE_MEM_EVENT_PRIORITY = "UPDATE member SET event_priority=:event_priority WHERE id=:id";
	private final static String GET_MAX_EVENT_PRIORITY = "SELECT MAX(event_priority) FROM member";
	@Autowired
	MemberDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public int insertMember(Member member) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("full_id", member.getFullId())
				.addValue("login_id", member.getLoginId())
				.addValue("name", member.getName())
				.addValue("email", member.getEmail())
				.addValue("parent_id", member.getParentId())
				.addValue("password", member.getPassword().getBytes())
				.addValue("description", member.getDescription())
				.addValue("af_member_id", member.getAfMemberId())
				.addValue("address", member.getAddress())
				.addValue("enable", member.isEnable())
				.addValue("last_login_ip", member.getLastLoginIp())
				.addValue("last_login_time", sdf.format(new Date()))
				.addValue("email_token", member.getEmailToken())
				.addValue("sso", member.getSso())
				.addValue("oauth_token", member.getOauthToken())
				.addValue("event_priority", member.getEventPriority())
				.addValue("phone", member.getPhone())
				.addValue("deleted", member.isDeleted());

		this.getNamedParameterJdbcTemplate().update(INSERT_MEM, parameters);

		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		member.setId(id);
		return id;
	}

	@Override
	public int updateMember(Member member) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("full_id", member.getFullId())
				.addValue("login_id", member.getLoginId())
				.addValue("name", member.getName())
				.addValue("email", member.getEmail())
				.addValue("parent_id", member.getParentId())
				.addValue("password", member.getPassword())
				.addValue("description", member.getDescription())
				.addValue("af_member_id", member.getAfMemberId())
				.addValue("address", member.getAddress())
				.addValue("enable", member.isEnable())
				.addValue("last_login_ip", member.getLastLoginIp())
				.addValue("last_login_time", sdf.format(new Date()))
				.addValue("email_token", member.getEmailToken())
				.addValue("sso", member.getSso())
				.addValue("oauth_token", member.getOauthToken())
				.addValue("event_priority", member.getEventPriority())
				.addValue("phone", member.getPhone())
				.addValue("deleted", member.isDeleted())
				.addValue("id", member.getId());
		return this.getNamedParameterJdbcTemplate().update(UPDATE_MEM,
				parameters);
	}

	@Override
	public int updateMemberNoPassword(Member member) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("full_id", member.getFullId())
				.addValue("login_id", member.getLoginId())
				.addValue("name", member.getName())
				.addValue("email", member.getEmail())
				.addValue("parent_id", member.getParentId())
				.addValue("description", member.getDescription())
				.addValue("af_member_id", member.getAfMemberId())
				.addValue("address", member.getAddress())
				.addValue("enable", member.isEnable())
				.addValue("last_login_ip", member.getLastLoginIp())
				.addValue("last_login_time", sdf.format(new Date()))
				.addValue("email_token", member.getEmailToken())
				.addValue("sso", member.getSso())
				.addValue("oauth_token", member.getOauthToken())
				.addValue("event_priority", member.getEventPriority())
				.addValue("phone", member.getPhone())
				.addValue("deleted", member.isDeleted())
				.addValue("id", member.getId());
		return this.getNamedParameterJdbcTemplate().update(UPDATE_MEM_NO_PW,
				parameters);
	}

	@Override
	public Member getMemberById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		return this.getNamedParameterJdbcTemplate().queryForObject(
				GET_MEM_BY_ID, parameters,
				new BeanPropertyRowMapper<Member>(Member.class));
	}

	@Override
	public Member getMemberByLoginId(String loginId) {
		// StringBuffer sql = new
		// StringBuffer("SELECT m.*, r.name FROM member m, member_role mr, role r WHERE m.login_id=:login_id AND m.id = mr.member_id AND mr.role_id = r.id ORDER BY r.priority ASC ");
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"login_id", loginId);
		return this.getNamedParameterJdbcTemplate().queryForObject(
				GET_MEM_BY_LOGIN_ID, parameters,
				new BeanPropertyRowMapper<Member>(Member.class));
	}

	@Override
	public List<Member> getAllMembers() {
		List<Member> memberList = this.getNamedParameterJdbcTemplate().query(
				GET_ALL_MEM, new BeanPropertyRowMapper<Member>(Member.class));
		return memberList;
	}

	@Override
	public List<Member> getMemberByParentId(int parentId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"parent_id", parentId);
		List<Member> memberList = this.getNamedParameterJdbcTemplate().query(
				GET_MEM_BY_PARENT_ID, parameters,
				new BeanPropertyRowMapper<Member>(Member.class));
		return memberList;
	}

	@Override
	public Member getMemberByEmailToken(String emailToken) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"email_token", emailToken);
		return this.getNamedParameterJdbcTemplate().queryForObject(
				GET_MEM_BY_EMAIL_TOKEN, parameters,
				new BeanPropertyRowMapper<Member>(Member.class));
	}

	/*
	 * 依人員屬性值查詢人員列表 add by oreo 2018.06.15.
	 */
	@Override
	public List<Member> getMemberListByPropValue(String englishName,
			String value) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"english_name", englishName).addValue("value", value);
		List<Member> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_MEM_LIST_BY_PROP_VALUE, parameters,
				new BeanPropertyRowMapper<Member>(Member.class));
		return rtnValue;
	}

	/*
	 * 查詢該群組中，符合人員屬性值的人員列表 add by oreo 2018.06.17. (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.MemberDao#getMemberListByPropValueAndGroupId(java
	 * .lang.String, java.lang.String, int)
	 */
	@Override
	public List<Member> getMemberListByPropValueAndGroupId(String englishName,
			String value, int groupId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("english_name", englishName).addValue("value", value)
				.addValue("group_id", groupId);
		List<Member> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_MEM_LIST_BY_PROP_VALUE_AND_GROUP_ID, parameters,
				new BeanPropertyRowMapper<Member>(Member.class));
		return rtnValue;
	}

	/*
	 * 修改 member eventPriority 欄位(慶魚塘客製欄位) (non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.MemberDao#updateMemberEventPriority(int, long)
	 */
	@Override
	public int updateMemberEventPriority(int id, BigInteger eventPriority) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"event_priority", eventPriority).addValue("id", id);
		return this.getNamedParameterJdbcTemplate().update(UPDATE_MEM_EVENT_PRIORITY,
				parameters);
	}
	
	/*
	 * 查詢 member eventPriority 欄位最大的值 (non-Javadoc)
	 * @see com.flowring.cn.dao.MemberDao#getMaxEventPriority()
	 */
	@Override
	public BigInteger getMaxEventPriority() {
		BigInteger maxEventPriority = this.getJdbcTemplate().queryForObject(
				GET_MAX_EVENT_PRIORITY, BigInteger.class);
		return maxEventPriority;
	}
}
