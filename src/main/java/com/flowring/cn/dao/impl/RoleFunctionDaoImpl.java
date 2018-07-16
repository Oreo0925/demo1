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

import com.flowring.cn.dao.RoleFunctionDao;
import com.flowring.cn.entity.RoleFunction;

@Repository
public class RoleFunctionDaoImpl extends NamedParameterJdbcDaoSupport implements RoleFunctionDao {
	
	@Autowired
	RoleFunctionDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertRoleFunction(RoleFunction roleFunction) {
		StringBuffer sql = new StringBuffer("INSERT INTO role_function (role_id, function_id) VALUES(:roleId, :functionId)");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("roleId", roleFunction.getRoleId())
			.addValue("functionId", roleFunction.getFunctionId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public int insertRoleFunctionList(List<RoleFunction> roleFunctionList) {
		StringBuffer sql = new StringBuffer("INSERT INTO role_function (role_id, function_id) VALUES(:roleId, :functionId)");
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RoleFunction roleFunction : roleFunctionList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("roleId", roleFunction.getRoleId())
					.addValue("functionId", roleFunction.getFunctionId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				sql.toString(), parameters.toArray(new SqlParameterSource[0]));
		return rs.length;
	}

	@Override
	public int deleteRoleFunctionList(List<RoleFunction> roleFunctionList) {
		StringBuffer dSql = new StringBuffer("DELETE FROM role_function WHERE role_id=:roleId AND function_id=:functionId");
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RoleFunction roleFunction : roleFunctionList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("roleId", roleFunction.getRoleId())
					.addValue("functionId", roleFunction.getFunctionId()));
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
	public List<RoleFunction> getRoleFunctionByRoleId(int roleId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM role_function WHERE role_id=:roleId");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("roleId", roleId); 	
		List<RoleFunction> roleFunctionsList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(RoleFunction.class));
		return roleFunctionsList;
	}

}
