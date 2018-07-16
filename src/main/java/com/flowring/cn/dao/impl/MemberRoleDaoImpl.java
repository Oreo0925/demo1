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

import com.flowring.cn.dao.MemberRoleDao;
import com.flowring.cn.entity.MemberRole;

@Repository
public class MemberRoleDaoImpl extends NamedParameterJdbcDaoSupport implements MemberRoleDao {
	
	@Autowired
	MemberRoleDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public int insertMemberRole(MemberRole memberRole) {
		StringBuffer sql = new StringBuffer("INSERT INTO member_role (member_id, role_id) VALUES(:member_id, :role_id)");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", memberRole.getMemberId())
			.addValue("role_id", memberRole.getRoleId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}
	
	@Override
	public int insertMemberRoleList(List<MemberRole> memberRoleList) {
		StringBuffer sql = new StringBuffer("INSERT INTO member_role (member_id, role_id) VALUES(:member_id, :role_id)");
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (MemberRole memberRole : memberRoleList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("member_id", memberRole.getMemberId())
					.addValue("role_id", memberRole.getRoleId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				sql.toString(), parameters.toArray(new SqlParameterSource[0]));
		return rs.length;
	}

	@Override
	public int deleteMemberRoleList(List<MemberRole> memberRoleList) {
		StringBuffer dSql = new StringBuffer("DELETE FROM member_role WHERE member_id=:member_id AND role_id=:role_id");
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (MemberRole memberRole : memberRoleList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("member_id", memberRole.getMemberId())
					.addValue("role_id", memberRole.getRoleId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				dSql.toString(), parameters.toArray(new SqlParameterSource[0]));
		int resultSize = 0;
		for (int i = 0; i < rs.length; i++) {
			resultSize += rs[i];
		}
		return resultSize;
	}

	@Override
	public List<MemberRole> getMemberRoleByMemberId(int memberId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM member_role WHERE member_id=:member_id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("member_id", memberId); 	
		List<MemberRole> memberRoleList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(MemberRole.class));
		return memberRoleList;
	}

	@Override
	public List<MemberRole> getMemberRoleByRoleId(int roleId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM member_role WHERE role_id=:role_id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("role_id", roleId); 	
		List<MemberRole> memberRoleList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(MemberRole.class));
		return memberRoleList;
	}

}
