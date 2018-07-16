package com.flowring.cn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import com.flowring.cn.dao.MemberPropertiesDao;
import com.flowring.cn.entity.DeviceProperties;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.MemberProperties;

@Repository
public class MemberPropertiesDaoImpl extends NamedParameterJdbcDaoSupport
		implements MemberPropertiesDao {

	private final static String INSERT_MEM_PROP_LIST = "insert into member_properties (member_id, english_name, name, value, fix, deleted) values(:member_id, :english_name, :name, :value, :fix, :deleted)";
	private final static String UPDATE_MEM_PROP_LIST = "update member_properties set member_id=:member_id, english_name=:english_name, name=:name, value=:value, fix=:fix, deleted=:deleted where id=:id";
	private final static String GET_MEM_PROP_BY_ID = "select * from member_properties where id=:id order by id desc";
	private final static String GET_MEM_PROP_BY_MEM_ID = "select * from member_properties where member_id=:member_id order by id desc";
	private final static String DELETE_MEM_PROP_LIST = "delete from member_properties WHERE id=:id ";
	private final static String UPDATE_MEM_PROP_VALUE = "update member_properties set value=:value where id=:id";
	
	@Autowired
	MemberPropertiesDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public int insertMemberProperties(
			List<MemberProperties> memberPropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (MemberProperties memberProperties : memberPropertiesList) {
			parameters
					.add(new MapSqlParameterSource()
							.addValue("member_id",
									memberProperties.getMemberId())
							.addValue("english_name",
									memberProperties.getEnglishName())
							.addValue("name", memberProperties.getName())
							.addValue("value", memberProperties.getValue())
							.addValue("fix", memberProperties.isFix())
							.addValue("deleted", memberProperties.isDeleted()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_MEM_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		return rs.length;
	}

	@Override
	public int updateMemberProperties(
			List<MemberProperties> memberPropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (MemberProperties memberProperties : memberPropertiesList) {
			parameters
					.add(new MapSqlParameterSource()
							.addValue("id", memberProperties.getId())
							.addValue("member_id",
									memberProperties.getMemberId())
							.addValue("english_name",
									memberProperties.getEnglishName())
							.addValue("name", memberProperties.getName())
							.addValue("value", memberProperties.getValue())
							.addValue("fix", memberProperties.isFix())
							.addValue("deleted", memberProperties.isDeleted()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_MEM_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		int resultSize = 0;
		for (int i = 0; i < rs.length; i++) {
			resultSize += rs[i];
		}
		return resultSize;
	}

	@Override
	public MemberProperties getMemberPropertiesById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		return this.getNamedParameterJdbcTemplate().queryForObject(
				GET_MEM_PROP_BY_ID,
				parameters,
				new BeanPropertyRowMapper<MemberProperties>(
						MemberProperties.class));
	}

	@Override
	public List<MemberProperties> getMemberPropertiesByMemberId(int memberId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"member_id", memberId);
		List<MemberProperties> memberPropertiesList = this
				.getNamedParameterJdbcTemplate().query(
						GET_MEM_PROP_BY_MEM_ID,
						parameters,
						new BeanPropertyRowMapper<MemberProperties>(
								MemberProperties.class));
		return memberPropertiesList;
	}

	@Override
	public int deleteMemberPropertiesList(
			List<MemberProperties> memberPropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (MemberProperties memberProperties : memberPropertiesList) {
			parameters.add(new MapSqlParameterSource().addValue("id",
					memberProperties.getId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				DELETE_MEM_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		int resultSize = 0;
		for (int i = 0; i < rs.length; i++) {
			resultSize += rs[i];
		}
		return resultSize;
	}

	@Override
	public int updateMemberPropValueById(int id, String value) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id).addValue("value", value);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_MEM_PROP_VALUE, parameters);
		return result;
	}

}
