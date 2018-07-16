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

import com.flowring.cn.dao.MenuDao;
import com.flowring.cn.entity.Menu;

@Repository
public class MenuDaoImpl extends NamedParameterJdbcDaoSupport implements MenuDao{
	
	public final static String GET_ALL_MENU = "SELECT id, parent_id, name, description, sequence FROM menu";
	public final static String GET_MENU_BY_ID = "SELECT id, parent_id, name, description, sequence FROM menu WHERE id=:id";
	public final static String GET_MENU_BY_PARENT_ID = "SELECT id, parent_id, name, description, sequence FROM menu WHERE parent_id=:parent_id";
	public final static String INSERT_MENU = "INSERT INTO menu(parent_id, name, description, sequence) VALUES (:parent_id, :name, :description, :sequence)";
	public final static String UPDATE_MENU = "UPDATE menu SET parent_id=:parent_id, name=:name, description=:description, sequence=:sequence WHERE id=:id ";
	public final static String DELETE_MENU = "DELETE FROM menu WHERE (id=:id) ";
	
	@Autowired
	MenuDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<Menu> getAllMenu() {
		List<Menu> rtnValue = new ArrayList<Menu>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_ALL_MENU,
				parameters, new BeanPropertyRowMapper<Menu>(Menu.class));
		return rtnValue;
	}

	@Override
	public Menu getMenuById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		Menu rtnValue = this.getNamedParameterJdbcTemplate()
				.queryForObject(GET_MENU_BY_ID, parameters,
						new BeanPropertyRowMapper<Menu>(Menu.class));
		return rtnValue;
	}

	@Override
	public List<Menu> getMenuByParentId(int parentId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"parent_id", parentId);
		List<Menu> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_MENU_BY_PARENT_ID, parameters,
				new BeanPropertyRowMapper<Menu>(Menu.class));
		return rtnValue;
	}

	@Override
	public int insertMenu(Menu menu) {
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("parent_id", menu.getParentId())
			.addValue("name", menu.getName())
			.addValue("description", menu.getDescription())
			.addValue("sequence", menu.getSequence());
		this.getNamedParameterJdbcTemplate().update(INSERT_MENU, parameters);
		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		menu.setId(id);
		return id;
	}

	@Override
	public int updateMenu(Menu menu) {
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("parent_id", menu.getParentId())
			.addValue("id", menu.getId())
			.addValue("name", menu.getName())
			.addValue("description", menu.getDescription())
			.addValue("sequence", menu.getSequence());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_MENU, parameters);
		return result;
	}

	@Override
	public int deleteMenuById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_MENU,
				parameters);
		return rs;
	}
}
