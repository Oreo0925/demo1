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

import com.flowring.cn.dao.RoleGroupDao;
import com.flowring.cn.entity.RoleGroup;

@Repository
public class RoleGroupDaoImpl extends NamedParameterJdbcDaoSupport implements RoleGroupDao {
	
	@Autowired
	RoleGroupDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public int insertRoleGroup(RoleGroup roleGroup) {
		StringBuffer sql = new StringBuffer("INSERT INTO role_group (role_id, group_id) VALUES(:roleId, :groupId)");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("roleId", roleGroup.getRoleId())
			.addValue("groupId", roleGroup.getGroupId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public int insertRoleGroupList(List<RoleGroup> roleGroupList) {
		StringBuffer sql = new StringBuffer("INSERT INTO role_group (role_id, group_id) VALUES(:roleId, :groupId)");
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RoleGroup roleGroup : roleGroupList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("roleId", roleGroup.getRoleId())
					.addValue("groupId", roleGroup.getGroupId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				sql.toString(), parameters.toArray(new SqlParameterSource[0]));
		return rs.length;
	}

	@Override
	public int deleteRoleGroupList(List<RoleGroup> roleGroupList) {
		StringBuffer dSql = new StringBuffer("DELETE FROM role_group WHERE role_id=:roleId AND group_id=:groupId");
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RoleGroup roleGroup : roleGroupList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("roleId", roleGroup.getRoleId())
					.addValue("groupId", roleGroup.getGroupId()));
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
	public List<RoleGroup> getRoleGroupByRoleId(int roleId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM role_group WHERE role_id=:roleId");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("roleId", roleId); 	
		List<RoleGroup> roleGroupList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(RoleGroup.class));
		return roleGroupList;
	}

	@Override
	public List<RoleGroup> getRoleGroupByGroupId(int groupId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM role_group WHERE group_id=:groupId");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("groupId", groupId); 	
		List<RoleGroup> roleGroupList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(RoleGroup.class));
		return roleGroupList;
	}

}
