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

import com.flowring.cn.dao.RoleMenuDao;
import com.flowring.cn.entity.RoleMenu;

@Repository
public class RoleMenuDaoImpl extends NamedParameterJdbcDaoSupport implements
		RoleMenuDao {
	
	public final static String GET_ROLE_MENU_BY_ID = "SELECT role_id, menu_id FROM role_menu WHERE role_id=:role_id";
	public final static String GET_ROLE_MENU_BY_MENU_ID = "SELECT role_id, menu_id FROM role_menu WHERE menu_id=:menu_id";
	public final static String INSERT_ROLE_MENU = "INSERT INTO role_menu(role_id, menu_id) VALUES (:role_id, :menu_id)";
	public final static String INSERT_ROLE_MENU_LIST = "INSERT INTO role_menu(role_id, menu_id) VALUES (:role_id, :menu_id) ";
	public final static String DELETE_ROLE_MENU_BY_ROLE_ID = "DELETE FROM role_menu WHERE role_id=:role_id ";
	public final static String DELETE_ROLE_MENU_BY_ID = "DELETE FROM role_menu WHERE menu_id=:menu_id ";
	
	@Autowired
	RoleMenuDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<RoleMenu> getRoleMenuByRoleId(int roleId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"role_id", roleId);

		List<RoleMenu> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ROLE_MENU_BY_ID, parameters,
				new BeanPropertyRowMapper<RoleMenu>(RoleMenu.class));

		return rtnValue;
	}

	@Override
	public List<RoleMenu> getRoleMenuByMenuId(int menuId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"menu_id", menuId);
		List<RoleMenu> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ROLE_MENU_BY_MENU_ID, parameters,
				new BeanPropertyRowMapper<RoleMenu>(RoleMenu.class));

		return rtnValue;
	}

	@Override
	public int insertRoleMenu(RoleMenu roleMenu) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"role_id", roleMenu.getRoleId()).addValue("menu_id",
				roleMenu.getMenuId());
		int rs = this.getNamedParameterJdbcTemplate().update(INSERT_ROLE_MENU,
				parameters);
		return rs;
	}

	@Override
	public int insertRoleMenu(List<RoleMenu> roleMenuList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RoleMenu roleMenu : roleMenuList) {
			parameters.add(new MapSqlParameterSource().addValue("role_id",
					roleMenu.getRoleId()).addValue("menu_id",
					roleMenu.getMenuId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_ROLE_MENU_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int deleteRoleMenuByRoleId(int roleId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"role_id", roleId);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_ROLE_MENU_BY_ROLE_ID,
				parameters);
		return rs;
	}

	@Override
	public int deleteRoleMenuByMenuId(int menuId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"menu_id", menuId);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_ROLE_MENU_BY_ID,
				parameters);
		return rs;
	}

}
