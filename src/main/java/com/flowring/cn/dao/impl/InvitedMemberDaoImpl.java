package com.flowring.cn.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.InvitedMemberDao;
import com.flowring.cn.entity.InvitedMember;

@Repository
public class InvitedMemberDaoImpl extends NamedParameterJdbcDaoSupport implements InvitedMemberDao {

	@Autowired
	InvitedMemberDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertInvitedMember(InvitedMember invitedMember) {
		StringBuffer sql = new StringBuffer("INSERT INTO invited_member (member_id, login_id, email_token, deleted) VALUES(:member_id, :login_id, :email_token, :deleted)");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", invitedMember.getMemberId())
			.addValue("login_id", invitedMember.getLoginId())
			.addValue("email_token", invitedMember.getEmailToken())
			.addValue("deleted", invitedMember.isDeleted());
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		invitedMember.setId(id);
		return id;	
	}

	@Override
	public int updateInvitedMember(InvitedMember invitedMember) {
		StringBuffer sql = new StringBuffer("UPDATE invited_member SET member_id=:member_id, login_id=:login_id, email_token=:email_token, deleted=:deleted WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", invitedMember.getMemberId())
			.addValue("login_id", invitedMember.getLoginId())
			.addValue("email_token", invitedMember.getEmailToken())
			.addValue("deleted", invitedMember.isDeleted())
			.addValue("id", invitedMember.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}
	
	@Override
	public InvitedMember getInvitedMemberByLoginId(String loginId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM invited_member WHERE login_id=:login_id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("login_id", loginId);
		return (InvitedMember) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(InvitedMember.class));
	}
	
	@Override
	public InvitedMember getInvitedMemberById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * FROM invited_member WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		InvitedMember invitedMember = (InvitedMember) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(InvitedMember.class));
		return invitedMember;
	}
	
	@Override
	public InvitedMember getInvitedMemberByEmailToken(String emailToken) {
		StringBuffer sql = new StringBuffer("SELECT * FROM invited_member WHERE email_token=:email_token ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("email_token", emailToken);
		InvitedMember invitedMember = (InvitedMember) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(InvitedMember.class));
		return invitedMember;
	}

	@Override
	public List<InvitedMember> getInvitedMembersByMemberId(int memberId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM invited_member WHERE member_id=:member_id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", memberId);
		List<InvitedMember> invitedMemberList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(InvitedMember.class));
		return invitedMemberList;
	}
	
	@Override
	public List<InvitedMember> getAllInvitedMembers() {
		StringBuffer sql = new StringBuffer("SELECT * FROM invited_member ORDER BY id DESC");
		List<InvitedMember> invitedMemberList = this.getNamedParameterJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(InvitedMember.class));
		return invitedMemberList;
	}

	@Override
	public boolean deleteInvitedMemberByLoginId(String loginId) {
		StringBuffer dsql = new StringBuffer("UPDATE invited_member SET deleted=:deleted WHERE login_id=:login_id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("deleted", true)
			.addValue("login_id", loginId);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

}
