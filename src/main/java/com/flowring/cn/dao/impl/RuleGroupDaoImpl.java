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

import com.flowring.cn.dao.RuleGroupDao;
import com.flowring.cn.entity.RuleGroup;

@Repository
public class RuleGroupDaoImpl extends NamedParameterJdbcDaoSupport implements
		RuleGroupDao {
	
	public final static String GET_RULE_GROUP_BY_RULE_ID = "SELECT rule_id, group_id FROM rule_group WHERE rule_id=:rule_id";
	public final static String GET_RULE_GROUP_BY_GROUP_ID = "SELECT rule_id, group_id FROM rule_group WHERE group_id=:group_id";
	public final static String INSERT_RULE_GROUP = "INSERT INTO rule_group(rule_id, group_id) VALUES (:rule_id, :group_id)";
	public final static String INSERT_RULE_GROUP_LIST = "INSERT INTO rule_group(rule_id, group_id) VALUES (:rule_id, :group_id) ";
	public final static String DELETED_RULE_GROUP_BY_RULE_ID = "DELETE FROM rule_group WHERE rule_id=:rule_id ";
	public final static String DELETED_RULE_GROUP_BY_GROUP_ID = "DELETE FROM rule_group WHERE group_id=:group_id ";
	
	@Autowired
	RuleGroupDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<RuleGroup> getRuleGroupByRuleId(int ruleId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"rule_id", ruleId);
		List<RuleGroup> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_RULE_GROUP_BY_RULE_ID, parameters,
				new BeanPropertyRowMapper<RuleGroup>(RuleGroup.class));
		return rtnValue;
	}

	@Override
	public List<RuleGroup> getRuleGroupByGroupId(int groupId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"group_id", groupId);
		List<RuleGroup> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_RULE_GROUP_BY_GROUP_ID, parameters,
				new BeanPropertyRowMapper<RuleGroup>(RuleGroup.class));
		return rtnValue;
	}

	@Override
	public int insertRuleGroup(RuleGroup ruleGroup) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"rule_id", ruleGroup.getRuleId()).addValue("group_id",
				ruleGroup.getGroupId());
		int rs = this.getNamedParameterJdbcTemplate().update(INSERT_RULE_GROUP,
				parameters);
		return rs;
	}

	@Override
	public int insertRuleGroup(List<RuleGroup> ruleGroupList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RuleGroup ruleGroup : ruleGroupList) {
			parameters.add(new MapSqlParameterSource().addValue("rule_id",
					ruleGroup.getRuleId()).addValue("group_id",
					ruleGroup.getGroupId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_RULE_GROUP_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int deleteRuleGroupByRuleId(int ruleId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"rule_id", ruleId);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETED_RULE_GROUP_BY_RULE_ID,
				parameters);
		return rs;
	}

	@Override
	public int deleteRuleGroupByGroupId(int groupId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"group_id", groupId);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETED_RULE_GROUP_BY_GROUP_ID,
				parameters);
		return rs;
	}

}
