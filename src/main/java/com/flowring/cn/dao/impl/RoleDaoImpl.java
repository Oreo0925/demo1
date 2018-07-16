package com.flowring.cn.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.RoleDao;
import com.flowring.cn.entity.Role;

@Repository
public class RoleDaoImpl extends NamedParameterJdbcDaoSupport implements RoleDao {
	
	@Autowired
	RoleDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertRole(Role role) {
		StringBuffer sql = new StringBuffer("INSERT INTO role (name, description, creator, priority) VALUES(:name, :description, :creator, :priority)");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("name", role.getName())
			.addValue("description", role.getDescription())
			.addValue("creator", role.getCreater())
			.addValue("priority", role.getPriority());
		
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		role.setId(id);
		return id;	
	}

	@Override
	public int updateRole(Role role) {
		StringBuffer sql = new StringBuffer("UPDATE role SET name=:name, description=:description, creator=:creator, priority=:priority WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", role.getName())
				.addValue("description", role.getDescription())
				.addValue("creator", role.getCreater())
				.addValue("priority", role.getPriority())
				.addValue("id", role.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public Role getRoleById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * from role WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		return (Role) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(Role.class));
	}

	@Override
	public List<Role> getRoleByName(String name) {
		StringBuffer sql = new StringBuffer("SELECT * from role WHERE name=:name ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("name", name);
		List<Role> roleList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(Role.class));
		return roleList;
	}

	@Override
	public List<Role> getRoleByCreator(int creator) {
		StringBuffer sql = new StringBuffer("SELECT * from role WHERE creator=:creator ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("creator", creator); 	
		List<Role> roleList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(Role.class));
		return roleList;
	}

	@Override
	public Map<Integer, Role> getRoleByMemberId(int memberId) {
		StringBuffer sql = new StringBuffer("SELECT r.*  FROM role r, member_role mr WHERE mr.member_id=:memberId AND mr.role_id = r.id ORDER BY r.priority ASC ");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("memberId", memberId); 	
		List<Role> roleList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(Role.class));
		Map<Integer, Role> roleMap = new HashMap<Integer, Role>();	
		for(int i = 0; i < roleList.size(); i++) {
			roleMap.put(i, roleList.get(i));
		}
		return roleMap;
	}

	@Override
	public boolean deleteRole(int roleId) {
		StringBuffer dsql = new StringBuffer("DELETE FROM role WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", roleId);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

}
