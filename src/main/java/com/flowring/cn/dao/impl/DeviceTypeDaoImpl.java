package com.flowring.cn.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.DeviceTypeDao;
import com.flowring.cn.entity.DeviceType;

@Repository
public class DeviceTypeDaoImpl extends NamedParameterJdbcDaoSupport implements
		DeviceTypeDao {
	
	public final static String GET_DEV_TYPE_BY_ID = "SELECT id, name, protocal, frequency FROM device_type WHERE id=:id";
	public final static String INSERT_DEV_TYPE = "INSERT INTO device_type(name, protocal, frequency) VALUES (:name, :protocal, :frequency) ";
	public final static String UPDATE_DEV_TYPE = "UPDATE device_type SET name=:name, protocal=:protocal, frequency=:frequency WHERE id=:id ";
	public final static String DELETE_DEV_TYPE = "DELETE FROM device_type WHERE (id=:id) ";
	
	@Autowired
	DeviceTypeDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public DeviceType getDeviceTypeById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		DeviceType rtnValue = this.getNamedParameterJdbcTemplate()
				.queryForObject(GET_DEV_TYPE_BY_ID, parameters,
						new BeanPropertyRowMapper<DeviceType>(DeviceType.class));
		return rtnValue;
	}

	@Override
	public int insertDeviceType(DeviceType deviceType) {
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("name", deviceType.getName())
			.addValue("protocal", deviceType.getProtocal())
			.addValue("frequency", deviceType.getFrequency());
			
		this.getNamedParameterJdbcTemplate().update(INSERT_DEV_TYPE, parameters);
		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		deviceType.setId(id);
		return id;
	}

	@Override
	public int updateDeviceType(DeviceType deviceType) {
		SqlParameterSource parameters = new MapSqlParameterSource()
		.addValue("name", deviceType.getName())
		.addValue("protocal", deviceType.getProtocal())
		.addValue("frequency", deviceType.getFrequency())
		.addValue("id", deviceType.getId());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV_TYPE, parameters);
		return result;
	}

	@Override
	public int deleteDeviceType(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		int rs = this.getNamedParameterJdbcTemplate().update(DELETE_DEV_TYPE,
				parameters);
		return rs;
	}
	

}
