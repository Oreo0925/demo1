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

import com.flowring.cn.dao.RuleDeviceDao;
import com.flowring.cn.entity.RuleDevice;

@Repository
public class RuleDeviceDaoImpl extends NamedParameterJdbcDaoSupport implements
		RuleDeviceDao {
	
	public final static String GET_RULE_DEV_BY_RULE_ID = "SELECT rule_id, device_id FROM rule_device WHERE rule_id=:rule_id";
	public final static String GET_RULE_DEV_BY_DEV_ID = "SELECT rule_id, device_id FROM rule_device WHERE device_id=:device_id";
	public final static String INSERT_RULE_DEV = "INSERT INTO rule_device(rule_id, device_id) VALUES (:rule_id, :device_id)";
	public final static String INSERT_RULE_DEV_LIST = "INSERT INTO rule_device(rule_id, device_id) VALUES (:rule_id, :device_id) ";
	public final static String DELETE_RULE_DEV_BY_RULE_ID = "DELETE FROM rule_device WHERE rule_id=:rule_id ";
	public final static String DELETE_RULE_DEV_BY_DEV_ID = "DELETE FROM rule_device WHERE device_id=:device_id ";
	
	@Autowired
	RuleDeviceDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<RuleDevice> getRuleDeviceByRuleId(int ruleId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"rule_id", ruleId);
		List<RuleDevice> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_RULE_DEV_BY_RULE_ID, parameters,
				new BeanPropertyRowMapper<RuleDevice>(RuleDevice.class));
		return rtnValue;
	}

	@Override
	public List<RuleDevice> getRuleDeviceByDeviceId(int deviceId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_id", deviceId);
		List<RuleDevice> rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_RULE_DEV_BY_DEV_ID, parameters,
				new BeanPropertyRowMapper<RuleDevice>(RuleDevice.class));
		return rtnValue;
	}

	@Override
	public int insertRuleDevice(RuleDevice ruleDevice) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"rule_id", ruleDevice.getRuleId()).addValue("device_id",
				ruleDevice.getDeviceId());
		int rs = this.getNamedParameterJdbcTemplate().update(INSERT_RULE_DEV,
				parameters);
		return rs;
	}

	@Override
	public int insertRuleDevice(List<RuleDevice> ruleDeviceList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (RuleDevice ruleDevice : ruleDeviceList) {
			parameters.add(new MapSqlParameterSource().addValue("rule_id",
					ruleDevice.getRuleId()).addValue("device_id",
					ruleDevice.getDeviceId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_RULE_DEV_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int deleteRuleDeviceByRuleId(int ruleId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"rule_id", ruleId);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_RULE_DEV_BY_RULE_ID,
				parameters);
		return rs;
	}

	@Override
	public int deleteRuleDeviceByDeviceId(int deviceId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_id", deviceId);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_RULE_DEV_BY_DEV_ID,
				parameters);
		return rs;
	}

}
