package com.flowring.cn.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.PayloadDao;
import com.flowring.cn.entity.Payload;

@Repository
public class PayloadDaoImpl extends NamedParameterJdbcDaoSupport implements PayloadDao {
	
	@Autowired
	PayloadDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertPayload(Payload payload) {
		StringBuffer sql = new StringBuffer("INSERT INTO payload (device_type_id, key_name, definition, unit, device_type_name) VALUES (:deviceTypeId, :keyName, :definition, :unit, :deviceTypeName)");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceTypeId", payload.getDeviceTypeId())
				.addValue("keyName", payload.getKeyName())
				.addValue("definition", payload.getDefinition())
				.addValue("unit", payload.getUnit())
				.addValue("deviceTypeName", payload.getDeviceTypeName());
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		int id = this.getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
		payload.setId(id);
		return id;	
	}

	@Override
	public int updatePayload(Payload payload) {
		StringBuffer sql = new StringBuffer("UPDATE payload SET device_type_id=:deviceTypeId, key_name=:keyName, definition=:definition, unit=:unit, device_type_name=:deviceTypeName WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceTypeId", payload.getDeviceTypeId())
				.addValue("keyName", payload.getKeyName())
				.addValue("definition", payload.getDefinition())
				.addValue("unit", payload.getUnit())
				.addValue("deviceTypeName", payload.getDeviceTypeName())
				.addValue("id", payload.getId());
		return this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
	}

	@Override
	public boolean deletePayloadById(int id) {
		StringBuffer dsql = new StringBuffer("DELETE FROM payload WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		int result = this.getNamedParameterJdbcTemplate().update(dsql.toString(), parameters);
		return result > 0 ? true : false;
	}

	@Override
	public Payload getPayloadById(int id) {
//		StringBuffer sql = new StringBuffer("SELECT p.*, dt.name FROM payload p, device_type dt WHERE p.id=:id AND p.device_type_id = dt.id");
		StringBuffer sql = new StringBuffer("SELECT * FROM payload WHERE id=:id");
		SqlParameterSource parameters = new MapSqlParameterSource()
			.addValue("id", id);
		return (Payload) this.getNamedParameterJdbcTemplate().queryForObject(sql.toString(), parameters, new BeanPropertyRowMapper(Payload.class));
	}

	@Override
	public List<Payload> getPayloadByDeviceTypeId(int deviceTypeId) {
//		StringBuffer sql = new StringBuffer("SELECT p.*, dt.name FROM payload p, device_type dt WHERE p.device_type_id=:deviceTypeId AND p.device_type_id = dt.id");
		StringBuffer sql = new StringBuffer("SELECT * FROM payload WHERE device_type_id=:deviceTypeId");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("deviceTypeId", deviceTypeId);
		List<Payload> payloadList = this.getNamedParameterJdbcTemplate().query(sql.toString(), parameters, new BeanPropertyRowMapper(Payload.class));
		return payloadList;
	}

}
