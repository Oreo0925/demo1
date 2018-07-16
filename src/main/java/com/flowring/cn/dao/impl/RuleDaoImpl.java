package com.flowring.cn.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.RuleDao;
import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.Rule;

@Repository
public class RuleDaoImpl extends NamedParameterJdbcDaoSupport implements RuleDao {
	
	@Autowired
	RuleDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public int insertRule(Rule rule) {
		StringBuffer sql = new StringBuffer("INSERT INTO rule (device_type_id, title, modes, conditions, action_type, status, action_id) VALUES(:deviceTypeId, :title, :modes, :conditions, :actionType, :status, :actionId)");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceTypeId", rule.getDeviceTypeId())
				.addValue("title", rule.getTitle())
				.addValue("modes", rule.getModes())
				.addValue("conditions", rule.getConditions())
				.addValue("actionType", rule.getActionType())
				.addValue("status", rule.getStatus())
				.addValue("actionId", rule.getActionId());
		
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		rule.setId(id);
		return id;	
	}

	@Override
	public int updateRule(Rule rule) {
		StringBuffer sql = new StringBuffer("UPDATE rule SET device_type_id=:deviceTypeId, title=:title, modes=:modes, conditions=:conditions, action_type=:actionType, status=:status, action_id=:actionId WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceTypeId", rule.getDeviceTypeId())
				.addValue("title", rule.getTitle())
				.addValue("modes", rule.getModes())
				.addValue("conditions", rule.getConditions())
				.addValue("actionType", rule.getActionType())
				.addValue("status", rule.getStatus())
				.addValue("actionId", rule.getActionId())
				.addValue("id", rule.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public boolean deleteRuleById(int id) {
		StringBuffer dsql = new StringBuffer("DELETE FROM rule WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

	@Override
	public Rule getRuleById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * FROM rule WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		return (Rule) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(Rule.class));
	}

	@Override
	public List<Rule> getRuleByDeviceTypeId(int deviceTypeId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM rule WHERE device_type_id=:deviceTypeId ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceTypeId", deviceTypeId);
		List<Rule> ruleList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(Rule.class));
		return ruleList;
	}
	
}
