package com.flowring.cn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.DeviceDao;
import com.flowring.cn.entity.Device;

@Repository
public class DeviceDaoImpl extends NamedParameterJdbcDaoSupport implements
		DeviceDao {
	
	@Autowired
	DeviceDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public final static String SELECT_ALL_DEV = "SELECT id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit FROM device";
	public final static String SELECT_DEV_BY_DELETED = "SELECT id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit FROM device WHERE deleted = :deleted";
	public final static String SELECT_DEV_BY_ID = "SELECT id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit FROM device WHERE id=:id";
	public final static String SELECT_DEV_BY_FULL_ID = "SELECT id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit FROM device WHERE full_id=:full_id";
	public final static String SELECT_DEV_BY_PARENT_ID_AND_DELETED = "SELECT id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit FROM device WHERE parent_id=:parent_id AND deleted = :deleted";
	public final static String SELECT_DEV_BY_IDENTIFIER_CUSTOMER = "SELECT id, parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit FROM device WHERE identifier=:identifier AND customer=:customer";
	public final static String INSERT_DEV = "INSERT INTO device(parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (:parent_id, :full_id, :name, :identifier, :customer, :rule_note, :rule_condition, :description, :enabled, :automatic, :status, :device_type_id, :creator, :deleted, :temp_id, :last_record_time, :record_time_limit)";
	public final static String UPDATE_DEV = "UPDATE device SET parent_id=:parent_id, full_id=:full_id, name=:name, customer=:customer, identifier=:identifier, rule_note=:rule_note, rule_condition=:rule_condition, description=:description, enabled=:enabled, automatic=:automatic, status=:status, device_type_id=:device_type_id, creator=:creator, deleted=:deleted, temp_id=:temp_id, last_record_time=:last_record_time, record_time_limit=:record_time_limit WHERE id=:id ";
	public final static String UPDATE_DEV_DELETED_BY_ID = "UPDATE device SET deleted=:deleted WHERE id=:id ";
	public final static String INSERT_DEV_LIST = "INSERT INTO device(parent_id, full_id, name, identifier, customer, rule_note, rule_condition, description, enabled, automatic, status, device_type_id, creator, deleted, temp_id, last_record_time, record_time_limit) VALUES (:parent_id, :full_id, :name, :identifier, :customer, :rule_note, :rule_condition, :description, :enabled, :automatic, :status, :device_type_id, :creator, :deleted, :temp_id, :last_record_time, :record_time_limit)";
	public final static String UPDATE_DEV_LIST_DELETED = "UPDATE device SET deleted = :deleted WHERE id=:id ";
	public final static String UPDATE_DEV_LAST_RECORD_TIME_BY_ID = "UPDATE device SET last_record_time=:last_record_time WHERE id=:id ";
		
	@Override
	public List<Device> getAllDeviceList() {
		List<Device> rtnValue = new ArrayList<Device>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(SELECT_ALL_DEV,
				parameters, new BeanPropertyRowMapper<Device>(Device.class));
		return rtnValue;
	}

	@Override
	public List<Device> getDeviceListByDeleted(boolean deleted) {
		List<Device> rtnValue = new ArrayList<Device>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"deleted", deleted);
		rtnValue = this.getNamedParameterJdbcTemplate().query(SELECT_DEV_BY_DELETED,
				parameters, new BeanPropertyRowMapper<Device>(Device.class));
		return rtnValue;
	}

	@Override
	public Device getDeviceById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		Device rtnValue = this.getNamedParameterJdbcTemplate().queryForObject(
				SELECT_DEV_BY_ID, parameters,
				new BeanPropertyRowMapper<Device>(Device.class));
		return rtnValue;
	}

	@Override
	public Device getDeviceByFullId(String fullId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"full_id", fullId);
		Device rtnValue = this.getNamedParameterJdbcTemplate().queryForObject(
				SELECT_DEV_BY_FULL_ID, parameters,
				new BeanPropertyRowMapper<Device>(Device.class));
		return rtnValue;
	}

	@Override
	public List<Device> getDeviceListByParentIdAndDeleted(int parentId,
			boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"parent_id", parentId).addValue("deleted", deleted);
		List<Device> rtnValue = this.getNamedParameterJdbcTemplate().query(
				SELECT_DEV_BY_PARENT_ID_AND_DELETED, parameters,
				new BeanPropertyRowMapper<Device>(Device.class));
		return rtnValue;
	}

	@Override
	public Device getDeviceByIdentifierAndCustomer(String identifier,
			String customer) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"identifier", identifier).addValue("customer", customer);
		Device rtnValue = this.getNamedParameterJdbcTemplate().queryForObject(
				SELECT_DEV_BY_IDENTIFIER_CUSTOMER, parameters,
				new BeanPropertyRowMapper<Device>(Device.class));
		return rtnValue;
	}

	@Override
	public int insertDevice(Device device) {
		SqlParameterSource parameters = getSqlParameterSourceByDevice(device);
		this.getNamedParameterJdbcTemplate().update(INSERT_DEV, parameters);
		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		device.setId(id);
		return id;
	}

	@Override
	public int updateDevice(Device device) {
		SqlParameterSource parameters = getSqlParameterSourceByDevice(device);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV, parameters);
		return result;
	}

	@Override
	public int updateDeviceDeletedById(int id, boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id).addValue("deleted", deleted);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV_DELETED_BY_ID, parameters);
		return result;
	}

	@Override
	public boolean deleteDeviceById(int id) {
		return false;
	}

	@Override
	@Transactional
	public int insertDeviceList(List<Device> deviceList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (Device device : deviceList) {
			MapSqlParameterSource mapSqlParameterSource = (MapSqlParameterSource) getSqlParameterSourceByDevice(device);
			parameters.add(mapSqlParameterSource);
		}

		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_DEV_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int updateDeviceListDeleted(List<Device> deviceList, boolean deleted) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (Device device : deviceList) {
			parameters.add(new MapSqlParameterSource().addValue("id",
					device.getId()).addValue("deleted", deleted));
		}
		int total = 0;
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_DEV_LIST_DELETED, parameters.toArray(new SqlParameterSource[0]));
		for (int i : rs) {
			total += i;
		}
		return total;
	}
	
	@Override
	public int updateDeviceLastRecordTimeById(int id, Date lastRecordTime) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id).addValue("last_record_time", lastRecordTime);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV_LAST_RECORD_TIME_BY_ID, parameters);
		return result;
	}
	
	private SqlParameterSource getSqlParameterSourceByDevice(Device device) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", device.getId())
				.addValue("parent_id", device.getParentId())
				.addValue("full_id", device.getFullId())
				.addValue("name", device.getName())
				.addValue("customer", device.getCustomer())
				.addValue("identifier", device.getIdentifier())
				.addValue("rule_note", device.getRuleNote())
				.addValue("rule_condition", device.getRuleCondition())
				.addValue("description", device.getDescription())
				.addValue("enabled", device.isEnabled())
				.addValue("automatic", device.isAutomatic())
				.addValue("status", device.getStatus())
				.addValue("device_type_id", device.getDeviceTypeId())
				.addValue("creator", device.getCreator())
				.addValue("deleted", device.isDeleted())
				.addValue("temp_id", device.getTempId())
				.addValue("last_record_time", device.getLastRecordTime())
				.addValue("record_time_limit", device.getRecordTimeLimit());
		return parameters;
	}



}
