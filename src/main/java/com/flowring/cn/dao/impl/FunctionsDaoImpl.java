package com.flowring.cn.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.FunctionsDao;
import com.flowring.cn.entity.Functions;

@Repository
public class FunctionsDaoImpl extends NamedParameterJdbcDaoSupport implements FunctionsDao {
	
	@Autowired
	FunctionsDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertFunctions(Functions functions) {
		StringBuffer sql = new StringBuffer("INSERT INTO functions (menu_id, name, description) values(:menuId, :name, :description)");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("menuId", functions.getMenuId())
				.addValue("name", functions.getName())
				.addValue("description", functions.getDescription());
		
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		functions.setId(id);
		return id;
	}

	@Override
	public int updateFunctions(Functions functions) {
		StringBuffer sql = new StringBuffer("UPDATE functions SET menu_id=:menuId, name=:name, description=:description WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("menuId", functions.getMenuId())
				.addValue("name", functions.getName())
				.addValue("description", functions.getDescription())
				.addValue("id", functions.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public boolean deleteFunctionsById(int id) {
		StringBuffer dsql = new StringBuffer("DELETE FROM functions WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

	@Override
	public Functions getFunctionsById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * from functions WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		return (Functions) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(Functions.class));
	}

	@Override
	public List<Functions> getFunctionsByMenuId(int menuId) {
		StringBuffer sql = new StringBuffer("SELECT * from functions WHERE menu_id=:menuId ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("menuId", menuId); 	
		List<Functions> functionsList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(Functions.class));
		return functionsList;
	}

}
