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

import com.flowring.cn.dao.DeviceStaticPropertiesDao;
import com.flowring.cn.entity.DeviceStaticProperties;

@Repository
public class DeviceStaticPropertiesDaoImpl extends NamedParameterJdbcDaoSupport
		implements DeviceStaticPropertiesDao {

	public final static String GET_ALL_DEV_STATIC_PROP = "SELECT id, english_name, name, fix FROM device_static_properties ";
	public final static String GET_DEV_STATIC_PROP_BY_ID = "SELECT id, english_name, name, fix FROM device_static_properties WHERE id=:id";
	public final static String INSERT_DEV_STATIC_PROP_LIST = "INSERT INTO device_static_properties( english_name, name, fix) VALUES (:english_name, :name, :fix) ";
	public final static String UPDATE_DEV_STATIC_PROP_LIST = "UPDATE device_static_properties SET english_name=:english_name, name=:name, fix=:fix WHERE id = :id";

	@Autowired
	DeviceStaticPropertiesDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<DeviceStaticProperties> getAllDevStaticPropList() {
		List<DeviceStaticProperties> rtnValue = new ArrayList<DeviceStaticProperties>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		rtnValue = this.getNamedParameterJdbcTemplate().query(
				GET_ALL_DEV_STATIC_PROP,
				parameters,
				new BeanPropertyRowMapper<DeviceStaticProperties>(
						DeviceStaticProperties.class));
		return rtnValue;
	}

	@Override
	public DeviceStaticProperties getDevStaticPropListById(int id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", id);
		DeviceStaticProperties rtnValue = this.getNamedParameterJdbcTemplate()
				.queryForObject(
						GET_DEV_STATIC_PROP_BY_ID,
						parameters,
						new BeanPropertyRowMapper<DeviceStaticProperties>(
								DeviceStaticProperties.class));
		return rtnValue;
	}

	@Override
	public int insertDevStaticPropList(
			List<DeviceStaticProperties> staticPropList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceStaticProperties staticProp : staticPropList) {
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("english_name", staticProp.getEnglishName())
					.addValue("name", staticProp.getName())
					.addValue("fix", staticProp.isFix());
			parameters.add(mapSqlParameterSource);
		}
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				INSERT_DEV_STATIC_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		int total = 0;
		for (int i : rs) {
			total += i;
		}
		return total;
	}

	@Override
	public int updateDevStaticPropList(
			List<DeviceStaticProperties> staticPropList) {
		List<MapSqlParameterSource> parameters = new ArrayList<MapSqlParameterSource>();
		for (DeviceStaticProperties staticProp : staticPropList) {
			MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
					.addValue("id", staticProp.getId())
					.addValue("english_name", staticProp.getEnglishName())
					.addValue("name", staticProp.getName())
					.addValue("fix", staticProp.isFix());
			parameters.add(mapSqlParameterSource);
		}
		int total = 0;
		int[] rs = this.getNamedParameterJdbcTemplate().batchUpdate(
				UPDATE_DEV_STATIC_PROP_LIST,
				parameters.toArray(new SqlParameterSource[0]));
		for (int count : rs) {
			total += count;
		}
		return total;
	}

}
