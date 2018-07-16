package com.flowring.cn.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.RuleLogDao;
import com.flowring.cn.entity.RuleLog;

@Repository
public class RuleLogDaoImpl extends NamedParameterJdbcDaoSupport implements RuleLogDao {
	
	@Autowired
	RuleLogDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertRuleLog(RuleLog ruleLog) {
		StringBuffer sql = new StringBuffer("INSERT INTO rule_log (ip, member_id, device_id, groups_id, groups_name, rule_id, rule_action_id, rule_conditions, rule_status, feedback, payload_data, trigger_time) VALUES(:ip, :memberId, :deviceId, :groupsId, :groupsName, :ruleId, :ruleActionId, :ruleConditions, :ruleStatus, :feedback, :payloadData, :triggerTime)");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("ip", ruleLog.getIp())
				.addValue("memberId", ruleLog.getMemberId())
				.addValue("deviceId", ruleLog.getDeviceId())
				.addValue("groupsId", ruleLog.getGroupsId())
				.addValue("groupsName", ruleLog.getGroupsName())
				.addValue("ruleId", ruleLog.getRuleId())
				.addValue("ruleActionId", ruleLog.getRuleActionId())
				.addValue("ruleConditions", ruleLog.getRuleConditions())
				.addValue("ruleStatus", ruleLog.getRuleStatus())
				.addValue("feedback", ruleLog.getFeedback())
				.addValue("payloadData", ruleLog.getPayloadData())
				.addValue("triggerTime", ruleLog.getTriggerTime());
		
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		ruleLog.setId(id);
		return id;	
	}

	@Override
	public int updateRuleLog(RuleLog ruleLog) {
		StringBuffer sql = new StringBuffer("UPDATE rule_log SET ip=:ip, member_id=:memberId, device_id=:deviceId, groups_id=:groupsId, groups_name=:groupsName, rule_id=:ruleId, rule_action_id=:ruleActionId, rule_conditions=:ruleConditions, rule_status=:ruleStatus, feedback=:feedback, payload_data=:payloadData, trigger_time=:triggerTime WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("ip", ruleLog.getIp())
				.addValue("memberId", ruleLog.getMemberId())
				.addValue("deviceId", ruleLog.getDeviceId())
				.addValue("groupsId", ruleLog.getGroupsId())
				.addValue("groupsName", ruleLog.getGroupsName())
				.addValue("ruleId", ruleLog.getRuleId())
				.addValue("ruleActionId", ruleLog.getRuleActionId())
				.addValue("ruleConditions", ruleLog.getRuleConditions())
				.addValue("ruleStatus", ruleLog.getRuleStatus())
				.addValue("feedback", ruleLog.getFeedback())
				.addValue("payloadData", ruleLog.getPayloadData())
				.addValue("triggerTime", ruleLog.getTriggerTime())
				.addValue("id", ruleLog.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public boolean deleteRuleLogById(int id) {
		StringBuffer dsql = new StringBuffer("DELETE FROM rule_log WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

	@Override
	public RuleLog getRuleLogById(int id) {
		StringBuffer sql = new StringBuffer("SELECT * FROM rule_log WHERE id=:id ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		return (RuleLog) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(RuleLog.class));
	}

	@Override
	public List<RuleLog> getRuleLogByDeviceId(int deviceId) {
		StringBuffer sql = new StringBuffer("SELECT * FROM rule_log WHERE device_id=:deviceId ORDER BY id DESC");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceId", deviceId);
		List<RuleLog> ruleLogList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(RuleLog.class));
		return ruleLogList;
	}

}
