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
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.DeviceGroupDao;
import com.flowring.cn.entity.DeviceGroup;

@Repository
public class DeviceGroupDaoImpl extends NamedParameterJdbcDaoSupport implements
		DeviceGroupDao {
	
	public final static String GET_ALL_DEV_GRO = "SELECT device_id, group_id FROM device_group";
	public final static String GET_DEV_GRO_BY_ID = "SELECT device_id, group_id FROM device_group WHERE device_id=:device_id";
	public final static String GET_DEV_GRO_BY_GROUP_ID = "SELECT device_id, group_id FROM device_group WHERE group_id=:group_id";
	public final static String INSERT_DEV_GRO = "INSERT INTO device_group(device_id, group_id) VALUES (:device_id, :group_id)";
	public final static String INSERT_DEV_GRO_LIST = "INSERT INTO device_group(device_id, group_id) VALUES (:device_id, :group_id) ";
	public final static String DELETE_DEV_GRO_LIST = "DELETE FROM device_group WHERE device_id=:device_id AND group_id=:group_id ";
	
	@Autowired
	DeviceGroupDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<DeviceGroup> getAllDeviceGroup() {
		List<DeviceGroup> rtnValue = new ArrayList<DeviceGroup>();
		SqlParameterSource parameters = new MapSqlParameterSource();

		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_ALL_DEV_GRO,
				parameters, new BeanPropertyRowMapper<DeviceGroup>(DeviceGroup.class));
		return rtnValue;
	}

	@Override
	public List<DeviceGroup> getDeviceGroupByDeviceId(int deviceId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_id", deviceId);
		List<DeviceGroup> rtnValue = this.getNamedParameterJdbcTemplate()
				.query(GET_DEV_GRO_BY_ID, parameters,
						new BeanPropertyRowMapper<DeviceGroup>(DeviceGroup.class));
		return rtnValue;
	}

	@Override
	public List<DeviceGroup> getDeviceGroupByGroupId(int groupId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"group_id", groupId);
		List<DeviceGroup> rtnValue = this.getNamedParameterJdbcTemplate()
				.query(GET_DEV_GRO_BY_GROUP_ID, parameters,
						new BeanPropertyRowMapper<DeviceGroup>(DeviceGroup.class));
		return rtnValue;
	}

	@Override
	public int insertDeviceGroup(DeviceGroup deviceGroup) {
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("device_id", deviceGroup.getDeviceId())
			.addValue("group_id", deviceGroup.getGroupId());
		int rs = this.getNamedParameterJdbcTemplate().update(INSERT_DEV_GRO,
				parameters);
		return rs;
	}

	@Override
	@Transactional
	public int insertDeviceGroup(List<DeviceGroup> deviceGroupList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceGroup deviceGroup : deviceGroupList) {
			parameters.add(new MapSqlParameterSource().addValue("device_id",
					deviceGroup.getDeviceId()).addValue("group_id",
					deviceGroup.getGroupId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_DEV_GRO_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	@Transactional
	public int deleteDeviceGroupList(List<DeviceGroup> deviceGroupList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceGroup deviceGroup : deviceGroupList) {
			parameters.add(new MapSqlParameterSource().addValue("device_id",
					deviceGroup.getDeviceId()).addValue("group_id",
					deviceGroup.getGroupId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				DELETE_DEV_GRO_LIST, parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

}
