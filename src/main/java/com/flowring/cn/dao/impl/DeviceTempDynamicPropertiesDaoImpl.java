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

import com.flowring.cn.dao.DeviceTempDynamicPropertiesDao;
import com.flowring.cn.entity.DeviceTempDynamicProp;

@Repository
public class DeviceTempDynamicPropertiesDaoImpl extends
		NamedParameterJdbcDaoSupport implements DeviceTempDynamicPropertiesDao {

	public final static String GET_DEV_TEMP_PROP_BY_TEMP_ID = "SELECT id, device_temp_id, english_name, name, deleted, fix FROM device_temp_dynamic_properties WHERE device_temp_id = :device_temp_id";
	public final static String GET_DEV_TEMP_PROP_BY_TEMP_ID_AND_DELETED = "SELECT id, device_temp_id, english_name, name, deleted, fix FROM device_temp_dynamic_properties WHERE device_temp_id = :device_temp_id AND deleted=:deleted";
	public final static String GET_DEV_TEMP_PROP_BY_ID = "SELECT id, device_temp_id, english_name, name, deleted, fix FROM device_temp_dynamic_properties WHERE id=:id";
	public final static String INSERT_DEV_TEMP_PROP_BY_ID = "INSERT INTO device_temp_dynamic_properties( device_temp_id, english_name, name, deleted, fix) VALUES ( :device_temp_id, :english_name, :name, :deleted, :fix) ";
	public final static String INSERT_DEV_TEMP_PROP_LIST = "INSERT INTO device_temp_dynamic_properties( device_temp_id, english_name, name, deleted, fix) VALUES ( :device_temp_id, :english_name, :name, :deleted, :fix) ";
	public final static String UPDATE_DEV_TEMP_PROP_LIST_DELTED = "UPDATE device_temp_dynamic_properties SET deleted=:deleted WHERE id = :id";
	public final static String UPDATE_DEV_TEMP_PROP_BY_TEMP_ID = "UPDATE device_temp_dynamic_properties SET deleted=:deleted WHERE device_temp_id=:device_temp_id ";
	public final static String UPDATE_DEV_TEMP_PROP_LIST = "UPDATE device_temp_dynamic_properties SET device_temp_id=:device_temp_id, english_name=:english_name, name=:name, deleted=:deleted, fix=:fix WHERE id=:id";
	public final static String IS_DEV_TEMP_PROP_EXIST = "SELECT id FROM device_temp_dynamic_properties WHERE id = :id";

	@Autowired
	DeviceTempDynamicPropertiesDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	@Transactional
	public List<DeviceTempDynamicProp> getDevTempDynamicPropListByTempId(
			int tempId) {
		List<DeviceTempDynamicProp> rtnValue = new ArrayList<DeviceTempDynamicProp>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_temp_id", tempId);

		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_DEV_TEMP_PROP_BY_TEMP_ID,
				parameters,
				new BeanPropertyRowMapper<DeviceTempDynamicProp>(
						DeviceTempDynamicProp.class));
		return rtnValue;
	}

	@Override
	public List<DeviceTempDynamicProp> getDevTempDynamicPropListByTempIdAndDeleted(
			int tempId, boolean deleted) {
		List<DeviceTempDynamicProp> rtnValue = new ArrayList<DeviceTempDynamicProp>();
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_temp_id", tempId).addValue("deleted", deleted);

		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_DEV_TEMP_PROP_BY_TEMP_ID_AND_DELETED,
				parameters,
				new BeanPropertyRowMapper<DeviceTempDynamicProp>(
						DeviceTempDynamicProp.class));
		return rtnValue;
	}

	@Override
	public DeviceTempDynamicProp getDevTempDynamicPropById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		DeviceTempDynamicProp rtnValue = (DeviceTempDynamicProp) this
				.getNamedParameterJdbcTemplate().queryForObject(
						GET_DEV_TEMP_PROP_BY_ID, parameters,
						new BeanPropertyRowMapper<DeviceTempDynamicProp>(DeviceTempDynamicProp.class));
		return rtnValue;
	}

	@Override
	public int insertDevTempDynamicPropList(
			List<DeviceTempDynamicProp> deviceTempPropertieList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceTempDynamicProp deviceTempPropertie : deviceTempPropertieList) {
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("device_temp_id",
							deviceTempPropertie.getDeviceTempId())
					.addValue("english_name",
							deviceTempPropertie.getEnglishName())
					.addValue("name", deviceTempPropertie.getName())
					.addValue("deleted", deviceTempPropertie.isDeleted())
					.addValue("fix", deviceTempPropertie.isFix());
			parameters.add(mapSqlParameterSource);
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_DEV_TEMP_PROP_BY_ID,
				parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int insertDevTempDynamicPropList(int tempId,
			List<DeviceTempDynamicProp> deviceTempPropertieList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceTempDynamicProp deviceTempPropertie : deviceTempPropertieList) {
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("device_temp_id", tempId)
					.addValue("english_name",
							deviceTempPropertie.getEnglishName())
					.addValue("name", deviceTempPropertie.getName())
					.addValue("deleted", deviceTempPropertie.isDeleted())
					.addValue("fix", deviceTempPropertie.isFix());
			parameters.add(mapSqlParameterSource);
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_DEV_TEMP_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int updateDevTempDynamicPropDeletedByTempId(int tempId,
			boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_temp_id", tempId).addValue("deleted", deleted);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV_TEMP_PROP_BY_TEMP_ID, parameters);
		return result;
	}

	@Override
	public int updateDevTempDynamicPropListDeleted(
			List<DeviceTempDynamicProp> deviceTempPropertieList, boolean deleted) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceTempDynamicProp deviceTempPropertie : deviceTempPropertieList) {
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("id", deviceTempPropertie.getId()).addValue(
							"deleted", deleted);
			parameters.add(mapSqlParameterSource);
		}
		int total = 0;
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_DEV_TEMP_PROP_LIST_DELTED,
				parameters.toArray(new SqlParameterSource[0]));
		for (int count : rs) {
			total += count;
		}
		return total;
	}

	@Override
	@Transactional
	public int updateDevTempDynamicProperList(
			List<DeviceTempDynamicProp> tempDynamicPropertiesList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceTempDynamicProp deviceTempPropertie : tempDynamicPropertiesList) {
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("id", deviceTempPropertie.getId())
					.addValue("device_temp_id",
							deviceTempPropertie.getDeviceTempId())
					.addValue("english_name",
							deviceTempPropertie.getEnglishName())
					.addValue("name", deviceTempPropertie.getName())
					.addValue("deleted", deviceTempPropertie.isDeleted())
					.addValue("fix", deviceTempPropertie.isFix());
			parameters.add(mapSqlParameterSource);
		}
		int total = 0;
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_DEV_TEMP_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		for (int count : rs) {
			total += count;
		}
		return total;
	}

	@Override
	public int isDevDynamicPropExist(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		List<DeviceTempDynamicProp> rtnValue = new ArrayList<DeviceTempDynamicProp>();
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				IS_DEV_TEMP_PROP_EXIST, parameters,
				new BeanPropertyRowMapper(String.class));
		return rtnValue.size();
	}
}
