package com.flowring.fish.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.fish.dao.FishRuleLogDao;
import com.flowring.fish.entity.FishRuleLog;

@Repository
public class FishRuleLogDaoImpl extends NamedParameterJdbcDaoSupport implements
		FishRuleLogDao {

	public final static String GET_ALL_FISH_RULE_LOG = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log ";
	public final static String GET_FISH_RULE_LOG_BY_ID = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE id=:id";
	public final static String GET_FISH_RULE_LOG_BY_DEVICE_ID = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE device_id=:device_id ";
	public final static String GET_FISH_RULE_LOG_BY_DEVICE_ID_AND_COUNT = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE (device_id=:device_id AND closed=:closed1) OR (device_id=:device_id AND closed=:closed2) ORDER BY alarm_time desc limit :count";
	public final static String GET_FISH_RULE_LOG_BY_CLOSED = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE closed=:closed";
	public final static String GET_FISH_RULE_LOG_BY_GROUP_ID = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE group_id=:group_id";
	public final static String GET_FISH_RULE_LOG_BY_MEM_ID_AND_CLOSED = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE check_member_id=:check_member_id AND closed = :closed ";
	public final static String GET_FISH_RULE_LOG_BY_ALARM_TYPE_AND_CLOSED = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE device_id=:device_id AND closed = :closed ";
	public final static String GET_FISH_RULE_LOG_BY_MEM_ID_AND_CLOSED1_AND_CLOSED2 = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE (check_member_id=:check_member_id AND closed=:closed1) OR (check_member_id=:check_member_id AND closed=:closed2)";
	public final static String GET_COUNT_FISH_RULE_LOG_BY_CLOSED = "SELECT COUNT(*) FROM fish_rule_log WHERE closed = :closed";
	public final static String GET_COUNT_FISH_RULE_LOG_BY_NOT_CLOSED = "SELECT COUNT(*) FROM fish_rule_log WHERE closed != :closed";
	public final static String GET_FISH_RULE_LOG_FINISH = "SELECT id, group_id, device_id, check_member_id, check_time, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, aerator_open, aerator_normal, used_drug, water_quality, memo, inform_manager, result, result_status, result_time, history, closed FROM fish_rule_log WHERE closed=:closed1 OR closed=:closed2";
	public final static String GET_NO_JOB_MEMBER_LIST_BY_GROUP = "SELECT f.id, f.full_id, f.login_id, f.name, f.email, f.parent_id, f.password, f.description, f.af_member_id, f.address, f.enable, f.last_login_ip, f.last_login_time, f.email_token, f.sso, f.oauth_token, f.event_priority, f.phone, f.deleted FROM ( SELECT mmrr.mem_id id, mmrr.full_id, mmrr.login_id, mmrr.mem_name name, mmrr.email, mmrr.parent_id, mmrr.password, mmrr.description, mmrr.af_member_id, mmrr.address, mmrr.enable, mmrr.last_login_ip, mmrr.last_login_time, mmrr.email_token, mmrr.sso, mmrr.oauth_token, mmrr.event_priority, mmrr.phone, mmrr.deleted FROM (SELECT g.id group_id, rg.role_id  role_id FROM cn_group g LEFT OUTER JOIN role_group rg ON g.id = rg.group_id WHERE g.id = :group_id ) rrgg LEFT JOIN (SELECT m.id mem_id, m.full_id, m.login_id, m.name mem_name, m.email, m.parent_id, m.password, m.description, m.af_member_id, m.address, m.enable, m.last_login_ip, m.last_login_time, m.email_token, m.sso, m.oauth_token, m.event_priority, m.phone, m.deleted, rm.role_id role_id FROM member m LEFT OUTER JOIN member_role rm ON m.id = rm.member_id ) mmrr ON rrgg.role_id = mmrr.role_id LEFT JOIN member_properties prop ON mmrr.mem_id = prop.member_id WHERE english_name = 'suspend' AND value = 'false') f WHERE (id) NOT IN (SELECT distinct check_member_id member_id FROM fish_rule_log WHERE closed = 0 )";
	public final static String GET_FISH_RULE_LOG_BY_GROUP_ID_AND_NO_MEM_ID_AND_NOT_FINISH = "SELECT * FROM fish_rule_log WHERE group_id = :group_id AND check_member_id = 0 AND closed = 0 AND call_times >= 2 AND alarm_time = (SELECT MAX(alarm_time) FROM fish_rule_log WHERE group_id = :group_id)";
	public final static String GET_ALL_FISH_RULE_LOG_BY_GROUP_ID_AND_NO_MEM_ID_AND_NOT_FINISH = "SELECT * FROM fish_rule_log WHERE check_member_id = 0 AND closed = 0";
	public final static String GET_NOT_CLOSED_FISH_RULE_LOG_BY_GROUPID = "SELECT * FROM fish_rule_log WHERE group_id = :group_id AND closed = :closed";
	public final static String INSERT_FISH_RULE_LOG_ALARM = "INSERT INTO fish_rule_log (group_id, device_id, status_code, control_code, dissolved_oxygen, temperature, ph, alarm_type, alarm_time, check_member_id, check_time) VALUES (:group_id, :device_id, :status_code, :control_code, :dissolved_oxygen, :temperature, :ph, :alarm_type, :alarm_time, :check_member_id, :check_time) ";
	public final static String UPDATE_FISH_RULE_LOG_ALARM = "UPDATE fish_rule_log SET status_code=:status_code, control_code=:control_code, dissolved_oxygen=:dissolved_oxygen, temperature=:temperature, ph=:ph, alarm_type=:alarm_type, alarm_time=:alarm_time WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_ALARM_AND_MEM_ID = "UPDATE fish_rule_log SET status_code=:status_code, control_code=:control_code, dissolved_oxygen=:dissolved_oxygen, temperature=:temperature, ph=:ph, alarm_time=:alarm_time, check_member_id=:check_member_id, check_time=:check_time WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_CHECK_MEM_ID_AND_CHECK_TIME = "UPDATE fish_rule_log SET check_member_id=:check_member_id,  check_time=:check_time WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_HISTORY = "UPDATE fish_rule_log SET history=:history WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_AF_PRO_ID_CLOSED = "UPDATE fish_rule_log SET aerator_open=:aerator_open, aerator_normal=:aerator_normal, used_drug=:used_drug, water_quality=:water_quality, memo=:memo, inform_manager=:inform_manager, result=:result, af_pro_id=:af_pro_id, closed=:closed WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_RESULT_STATUS_AND_RESULT_TIME = "UPDATE fish_rule_log SET result_status =:result_status, result_time=:result_time WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_LIST_CLOSED = "UPDATE fish_rule_log SET closed=:closed WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_QUESTIONNAIRE = "UPDATE fish_rule_log SET aerator_open=:aerator_open, aerator_normal=:aerator_normal, used_drug=:used_drug, water_quality=:water_quality, memo=:memo, inform_manager=:inform_manager, result=:result, closed=:closed WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_RESULT_STATUS_AND_HISTORY = "UPDATE fish_rule_log SET result_status =:result_status, result_time=:result_time, history=:history WHERE id=:id";
	public final static String UPDATE_FISH_RULE_LOG_BY_MEMBER_ID = "UPDATE fish_rule_log SET check_member_id=:check_member_id, check_time=:check_time WHERE id=:id";
	private static String FISH_RULE_LOG_LAST_UPDATE_SQL = "SELECT update_time FROM table_last_update WHERE name=:table_name";
	public final static String GET_ALL_FISH_RULE_LOG_NEED_CALL = "SELECT * FROM fish_rule_log WHERE call_auto=true AND call_times < 2 AND call_status <> '1' AND closed = 0";
	public final static String UPDATE_FISH_RULE_LOG_CALL_DATA = "UPDATE fish_rule_log SET call_auto=:callAuto, call_times=:callTimes, call_sid=:callSid, call_date=:callDate, call_status=:callStatus, call_response=:callResponse, call_duration=:callDuration, call_number=:callNumber WHERE id=:id";
	public final static String GET_FISH_RULE_LOG_BY_CALL_SID = "SELECT * FROM fish_rule_log WHERE call_sid=:callSid";
	
	@Autowired
	FishRuleLogDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<FishRuleLog> getAllFishRuleLog() {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ALL_FISH_RULE_LOG, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public FishRuleLog getFishRuleLogById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		FishRuleLog rtnValue = this.getNamedParameterJdbcTemplate()
				.queryForObject(
						GET_FISH_RULE_LOG_BY_ID,
						parameters,
						new BeanPropertyRowMapper<FishRuleLog>(
								FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByDeviceId(int deviceId) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_id", deviceId);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_DEVICE_ID, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByMemberIdAndClosed(int memberId,
			int closed) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"check_member_id", memberId).addValue("closed", closed);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_MEM_ID_AND_CLOSED, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByMemberFinish(int memberId,
			int closed1, int closed2) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("check_member_id", memberId)
				.addValue("closed1", closed1).addValue("closed2", closed2);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_MEM_ID_AND_CLOSED1_AND_CLOSED2,
				parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByGroupId(int groupId) {

		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"group_id", groupId);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_GROUP_ID, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	/*
	 * 查出此裝置 RuleLog 最新 N 筆資料 By deviceId and closed (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.fish.dao.FishRuleLogDao#getFishRuleLogByDeviceIdAndClosed
	 * (int, int, int, int)
	 */
	@Override
	public List<FishRuleLog> getFishRuleLogByDeviceIdAndClosed(int deviceId,
			int count, int closed1, int closed2) {

		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("device_id", deviceId).addValue("count", count)
				.addValue("closed1", closed1).addValue("closed2", closed2);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_DEVICE_ID_AND_COUNT, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	/*
	 * 依 Closed 查詢 Log List
	 */
	@Override
	public List<FishRuleLog> getFishRuleLogByClosed(int closed) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"closed", closed);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_CLOSED, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public int insertFishRuleLogAlarm(FishRuleLog fishRuleLog) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("group_id", fishRuleLog.getGroupId())
				.addValue("device_id", fishRuleLog.getDeviceId())
				.addValue("status_code", fishRuleLog.getStatusCode())
				.addValue("control_code", fishRuleLog.getControlCode())
				.addValue("dissolved_oxygen", fishRuleLog.getDissolvedOxygen())
				.addValue("temperature", fishRuleLog.getTemperature())
				.addValue("ph", fishRuleLog.getPh())
				.addValue("alarm_type", fishRuleLog.getAlarmType())
				.addValue("alarm_time", fishRuleLog.getAlarmTime())
				.addValue("check_member_id", fishRuleLog.getCheckMemberId())
				.addValue("check_time", fishRuleLog.getCheckTime());
		this.getNamedParameterJdbcTemplate().update(INSERT_FISH_RULE_LOG_ALARM,
				parameters);
		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		fishRuleLog.setId(id);
		return id;
	}

	@Override
	public int updateFishRuleLogResultStatus(int id, String resultStatus,
			Date resultTime) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("result_status", resultStatus)
				.addValue("result_time", resultTime).addValue("id", id);
		int rs = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_RESULT_STATUS_AND_RESULT_TIME, parameters);
		return rs;
	}

	@Override
	public int updateFishRuleLogHistory(int id, String history) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"history", history).addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_HISTORY, parameters);
		return result;
	}

	@Override
	public Date checkFishRuleLoglastUpdate() {
		Date result = null;

		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"table_name", "fish_rule_log");

		result = getNamedParameterJdbcTemplate().queryForObject(
				FISH_RULE_LOG_LAST_UPDATE_SQL, parameters, Date.class);

		return result;
	}

	@Override
	public int updateFishRuleLogListClosed(List<FishRuleLog> fishRuleLogList,
			int closed) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (FishRuleLog fishRuleLog : fishRuleLogList) {
			parameters.add(new MapSqlParameterSource().addValue("id",
					fishRuleLog.getId()).addValue("closed", closed));
		}
		int total = 0;
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_FISH_RULE_LOG_LIST_CLOSED,
				parameters.toArray(new SqlParameterSource[0]));
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int updateFishRuleLogCheckMemberIdAndCheckTime(
			List<FishRuleLog> fishRuleLogList, int memberId, Date checkTime) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (FishRuleLog fishRuleLog : fishRuleLogList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("id", fishRuleLog.getId())
					.addValue("check_member_id", memberId)
					.addValue("check_time", checkTime));
		}
		int total = 0;
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_FISH_RULE_LOG_CHECK_MEM_ID_AND_CHECK_TIME,
				parameters.toArray(new SqlParameterSource[0]));
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogListByMemberIdAndGroupList(
			int memberId, int closed, List<Group> groupList, int count) {
		StringBuffer sql = new StringBuffer(
				"SELECT id, group_id, device_id, status_code, control_code, dissolved_oxygen, temperature, ph,  alarm_type, alarm_time, result, result_time, closed, check_member_id, check_time, history ");
		sql.append(" FROM fish_rule_log WHERE check_member_id = :check_member_id AND closed = :closed ");
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		String orderByStr = "alarm_time";
		StringBuffer groups = new StringBuffer();
		for (int i = 0; i < groupList.size(); i++) {
			groups.append(groupList.get(i).getId());
			if (i < (groupList.size() - 1)) {
				groups.append(", ");
			}
		}
		if (groupList.size() > 0) {
			orderByStr += ", " + groups.toString();
			sql.append(" AND group_id in (" + groups.toString() + ") ");
			sql.append(" order by FIELD(alarm_time,  " + groups.toString()
					+ ") ");
		}
		sql.append(" limit :count");
		if (groupList.size() > 0) {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("check_member_id", memberId)
					.addValue("closed", closed).addValue("count", count);
			rtnValue = this.getNamedParameterJdbcTemplate().query(
					sql.toString(), parameters,
					new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		}

		return rtnValue;
	}

	@Override
	public int updateFishRuleLogAlarm(FishRuleLog fishRuleLog) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("status_code", fishRuleLog.getStatusCode())
				.addValue("control_code", fishRuleLog.getControlCode())
				.addValue("dissolved_oxygen", fishRuleLog.getDissolvedOxygen())
				.addValue("temperature", fishRuleLog.getTemperature())
				.addValue("ph", fishRuleLog.getPh())
				.addValue("alarm_type", fishRuleLog.getAlarmType())
				.addValue("alarm_time", fishRuleLog.getAlarmTime())
				.addValue("id", fishRuleLog.getId());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_ALARM, parameters);
		return result;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByDeviceIdAndClosed(
			int deviceId, int closed) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("device_id", deviceId)
				.addValue("closed", closed);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_ALARM_TYPE_AND_CLOSED, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public int updateFishRuleLogResultStatusAndHistory(FishRuleLog fishRuleLog) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("result_status", fishRuleLog.getResultStatus())
				.addValue("result_time", fishRuleLog.getResultTime())
				.addValue("history", fishRuleLog.getHistory())
				.addValue("id", fishRuleLog.getId());
		int rs = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_RESULT_STATUS_AND_HISTORY, parameters);
		return rs;
	}

	/*
	 * 結束 或 結束派工 (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.fish.dao.FishRuleLogDao#updateFishRuleLogAfPropIdAndClosed
	 * (com.flowring.fish.entity.FishRuleLog)
	 */
	@Override
	public int updateFishRuleLogAfPropIdAndClosed(FishRuleLog fishRuleLog) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("aerator_open", fishRuleLog.isAeratorOpen())
				.addValue("aerator_normal", fishRuleLog.isAeratorNormal())
				.addValue("used_drug", fishRuleLog.isUsedDrug())
				.addValue("water_quality", fishRuleLog.isWaterQuality())
				.addValue("memo", fishRuleLog.getMemo())
				.addValue("inform_manager", fishRuleLog.isInformManager())
				.addValue("result", fishRuleLog.getResult())
				.addValue("af_pro_id", fishRuleLog.getAfProId())
				.addValue("closed", fishRuleLog.getClosed())
				.addValue("id", fishRuleLog.getId());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_AF_PRO_ID_CLOSED, parameters);
		return result;
	}

	/*
	 * 修改 FishRuleLog check_member_id 的欄位 (non-Javadoc)
	 * 
	 * @see com.flowring.fish.dao.FishRuleLogDao#updateFishRuleLogMemberId(int,
	 * int)
	 */
	@Override
	public int updateFishRuleLogMemberIdAndCheckTime(int id, int memberId, Date checkTime) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"check_member_id", memberId).addValue(
						"check_time", checkTime).addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_BY_MEMBER_ID, parameters);
		return result;
	}

	/*
	 * 取得數量 By closed (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.fish.dao.FishRuleLogDao#getFishRuleLogCountByNotClosed(int)
	 */
	@Override
	public int getFishRuleLogCountByClosed(int closed) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"closed", closed);
		int result = this.getNamedParameterJdbcTemplate().queryForObject(
				GET_COUNT_FISH_RULE_LOG_BY_CLOSED, parameters, Integer.class);
		return result;
	}

	/*
	 * 取得 closed 欄位"不"等於的數量 (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.fish.dao.FishRuleLogDao#getFishRuleLogCountByNotClosed(int)
	 */
	@Override
	public int getFishRuleLogCountByNotClosed(int closed) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"closed", closed);
		int result = this.getNamedParameterJdbcTemplate().queryForObject(
				GET_COUNT_FISH_RULE_LOG_BY_NOT_CLOSED, parameters,
				Integer.class);
		return result;
	}

	/*
	 * 取得 FishRuleLog List BY closed = 1 或 closed = 2 (non-Javadoc)
	 * 
	 * @see com.flowring.fish.dao.FishRuleLogDao#getFishRuleLogFinish()
	 */
	@Override
	public List<FishRuleLog> getFishRuleLogFinish() {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"closed1", 1).addValue("closed2", 2);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_FINISH, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;

	}

	@Override
	public int updateFishRuleLogAlarmAndMemberId(FishRuleLog fishRuleLog) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("status_code", fishRuleLog.getStatusCode())
				.addValue("control_code", fishRuleLog.getControlCode())
				.addValue("dissolved_oxygen", fishRuleLog.getDissolvedOxygen())
				.addValue("temperature", fishRuleLog.getTemperature())
				.addValue("ph", fishRuleLog.getPh())
				.addValue("alarm_time", fishRuleLog.getAlarmTime())
				.addValue("id", fishRuleLog.getId())
				.addValue("check_member_id", fishRuleLog.getCheckMemberId())
				.addValue("check_time", fishRuleLog.getCheckTime());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_ALARM_AND_MEM_ID, parameters);
		return result;
	}
	
	/*
	 * 依照 groupId 查出有權限的人員，不包含目前身上有負責事件的人  (non-Javadoc)
	 * @see com.flowring.fish.dao.FishRuleLogDao#getNoJobMemberListByPGroupId(int)
	 */
	@Override
	public List<Member> getNoJobMemberListByPGroupId(int groupId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("group_id", groupId);
		List<Member> memberList = this.getNamedParameterJdbcTemplate().query(
				GET_NO_JOB_MEMBER_LIST_BY_GROUP, parameters, new BeanPropertyRowMapper<Member>(Member.class));
		return memberList;
	}
	
	/*
	 * 找到這一個群組中最舊的、memberId = 0、closed != 0 的一筆 FishRuleLog
	 */
	@Override
	public List<FishRuleLog> getOldestFishRuleLogInTheSameGroup(int groupId) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"group_id", groupId);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_FISH_RULE_LOG_BY_GROUP_ID_AND_NO_MEM_ID_AND_NOT_FINISH, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}
	

	@Override
	public List<FishRuleLog> getAllNotAssignedFishRuleLogInTheSameGroup(int groupId) {
		List<FishRuleLog> rtnValue = new ArrayList<FishRuleLog>();
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ALL_FISH_RULE_LOG_BY_GROUP_ID_AND_NO_MEM_ID_AND_NOT_FINISH, new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public int updateFishRuleLogCallData(FishRuleLog fishRuleLog) {
		
		SqlParameterSource parameters =  new BeanPropertySqlParameterSource(fishRuleLog);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_FISH_RULE_LOG_CALL_DATA, parameters);
		
		return result;
	}

	@Override
	public List<FishRuleLog> getAllNeedCall() {
		List<FishRuleLog> rtnValue = Collections.emptyList();
		
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ALL_FISH_RULE_LOG_NEED_CALL, parameters,
				new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	}
	
	@Override
	public FishRuleLog getFishRuleLogByCallSid(String callSid) {
		FishRuleLog rtnValue = null;
		
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"callSid", callSid);
		rtnValue = this.getNamedParameterJdbcTemplate()
				.queryForObject(
						GET_FISH_RULE_LOG_BY_CALL_SID,
						parameters,
						new BeanPropertyRowMapper<FishRuleLog>(
								FishRuleLog.class));
		return rtnValue;
	}

	@Override
	public List<FishRuleLog> getNotClosedFishRuleLogByGroupId(int groupId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("group_id", groupId)
				.addValue("closed", 0);
		List<FishRuleLog> rtnValue = this.getNamedParameterJdbcTemplate()
				.query(GET_NOT_CLOSED_FISH_RULE_LOG_BY_GROUPID, parameters, new BeanPropertyRowMapper<FishRuleLog>(FishRuleLog.class));
		return rtnValue;
	} 
	
	
	

}
