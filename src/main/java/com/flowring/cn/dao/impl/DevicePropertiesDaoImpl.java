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

import com.flowring.cn.dao.DevicePropertiesDao;
import com.flowring.cn.entity.DeviceProperties;

@Repository
public class DevicePropertiesDaoImpl extends NamedParameterJdbcDaoSupport
		implements DevicePropertiesDao {

	public final static String GET_ALL_DEV_PROP = "SELECT id, device_id, name, value, fix FROM device_properties";
	public final static String DELETE_DEV_PROP_LIST = "DELETE FROM device_properties WHERE id=:id ";
	public final static String GET_DEV_PROP_BY_DEV_ID = "SELECT id, device_id, name, value, fix FROM device_properties WHERE device_id=:device_id";
	public final static String GET_DEV_PROP_BY_ID = "SELECT id, device_id, name, value, fix FROM device_properties WHERE id=:id";
	public final static String INSERT_DEV_PROP_LIST = "INSERT INTO device_properties(device_id, name, value, fix) VALUES (:device_id, :name, :value, :fix)";
	public final static String UPDATE_DEV_PROP_LIST = "UPDATE device_properties SET device_id=:device_id, name=:name, value=:value, fix=:fix WHERE id=:id";

	@Autowired
	DevicePropertiesDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	/*
	 * 查詢裝置屬性(non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.DevicePropertiesDao#getAllDeviceProperties()
	 */
	public List<DeviceProperties> getAllDeviceProperties() {
		List<DeviceProperties> rtnValue = new ArrayList<DeviceProperties>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ALL_DEV_PROP,
				parameters,
				new BeanPropertyRowMapper<DeviceProperties>(
						DeviceProperties.class));
		return rtnValue;
	}

	@Override
	/*
	 * 查詢裝置屬性 By DeviceId (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.DevicePropertiesDao#getDevicePropertiesByDeviceId
	 * (int)
	 */
	public List<DeviceProperties> getDevicePropertiesByDeviceId(int deviceId) {
		List<DeviceProperties> rtnValue = new ArrayList<DeviceProperties>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_id", deviceId);
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_DEV_PROP_BY_DEV_ID,
				parameters,
				new BeanPropertyRowMapper<DeviceProperties>(
						DeviceProperties.class));
		return rtnValue;
	}

	@Override
	/*
	 * 查詢裝置屬性 By Id(non-Javadoc)
	 * 
	 * @see com.flowring.cn.dao.DevicePropertiesDao#getDevicePropertiesById(int)
	 */
	public DeviceProperties getDevicePropertiesById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		DeviceProperties deviceProperties = this
				.getNamedParameterJdbcTemplate().queryForObject(
						GET_DEV_PROP_BY_ID,
						parameters,
						new BeanPropertyRowMapper<DeviceProperties>(
								DeviceProperties.class));
		return deviceProperties;
	}

	@Override
	@Transactional
	/*
	 * 新增裝置屬性 (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.DevicePropertiesDao#insertDeviceProperties(java.util
	 * .List)
	 */
	public int insertDeviceProperties(
			List<DeviceProperties> devicePropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceProperties deviceProperties : devicePropertiesList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("device_id", deviceProperties.getDeviceId())
					.addValue("name", deviceProperties.getName())
					.addValue("value", deviceProperties.getValue())
					.addValue("fix", deviceProperties.getFix()));
		}

		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_DEV_PROP_LIST, parameters.toArray(new SqlParameterSource[0]));
		return rs.length;
	}

	@Override
	@Transactional
	/*
	 * 修改裝置屬性 (non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.DevicePropertiesDao#updateDeviceProperties(java.util
	 * .List)
	 */
	public int updateDeviceProperties(
			List<DeviceProperties> devicePropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceProperties deviceProperties : devicePropertiesList) {
			parameters.add(new MapSqlParameterSource()
					.addValue("id", deviceProperties.getId())
					.addValue("device_id", deviceProperties.getDeviceId())
					.addValue("name", deviceProperties.getName())
					.addValue("value", deviceProperties.getValue())
					.addValue("fix", deviceProperties.getFix()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_DEV_PROP_LIST, parameters.toArray(new SqlParameterSource[0]));
		return rs.length;
	}

	@Override
	@Transactional
	/*
	 * 刪除裝置屬性(non-Javadoc)
	 * 
	 * @see
	 * com.flowring.cn.dao.DevicePropertiesDao#deleteDevicePropertiesList(java
	 * .util.List)
	 */
	public boolean deleteDevicePropertiesList(
			List<DeviceProperties> devicePropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceProperties deviceProperties : devicePropertiesList) {
			parameters.add(new MapSqlParameterSource().addValue("id",
					deviceProperties.getId()));
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				DELETE_DEV_PROP_LIST, parameters.toArray(new SqlParameterSource[0]));
		return (rs.length == devicePropertiesList.size()) ? true : false;
	}

}
