package com.flowring.cn.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.DeviceTempDao;
import com.flowring.cn.entity.DeviceTemp;
import com.flowring.cn.entity.DeviceTempDynamicProp;
import com.flowring.cn.entity.DeviceTempPropData;
import com.flowring.cn.util.Constants;

@Repository
public class DeviceTempDaoImpl extends NamedParameterJdbcDaoSupport implements
		DeviceTempDao {

	public final static String GET_ALL_DEV_TEMP = "SELECT id, name, deleted FROM device_temp";
	public final static String GET_DEV_TEMP_BY_TEMP_ID = "SELECT id, name, deleted FROM device_temp WHERE id=:id";
	public final static String UPDATE_DEV_TEMP_BY_TEMP_ID = "UPDATE device_temp SET name=:name, deleted=:deleted WHERE id=:id ";
	public final static String UPDATE_DEV_TEMP_BY_ID = "UPDATE device_temp SET deleted=:deleted WHERE id=:id ";

	@Autowired
	DeviceTempDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<DeviceTemp> getAllDeviceTemp() {
		List<DeviceTemp> rtnValue = new ArrayList<DeviceTemp>();
		SqlParameterSource parameters = new MapSqlParameterSource();

		rtnValue = this.getNamedParameterJdbcTemplate().query(GET_ALL_DEV_TEMP,
				parameters,
				new BeanPropertyRowMapper<DeviceTemp>(DeviceTemp.class));
		return rtnValue;
	}

	@Override
	public DeviceTemp getDeviceTempByTempId(int tempId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", tempId);
		DeviceTemp rtnValue = this
				.getNamedParameterJdbcTemplate()
				.queryForObject(GET_DEV_TEMP_BY_TEMP_ID, parameters,
						new BeanPropertyRowMapper<DeviceTemp>(DeviceTemp.class));
		return rtnValue;
	}

	@Override
	public int insertDeviceTemp(DeviceTemp deviceTemp) {
		StringBuffer sql = new StringBuffer(
				"INSERT INTO device_temp(name, deleted) ");
		sql.append("VALUES (:name, :deleted)");
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"name", deviceTemp.getName()).addValue("deleted",
				deviceTemp.isDeleted());
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		int id = this.getJdbcTemplate().queryForObject(
				"select last_insert_id()", Integer.class);
		deviceTemp.setId(id);
		return id;
	}

	@Override
	@Transactional
	public int updateDeviceTemp(DeviceTemp deviceTemp) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", deviceTemp.getId())
				.addValue("name", deviceTemp.getName())
				.addValue("deleted", deviceTemp.isDeleted());
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV_TEMP_BY_TEMP_ID, parameters);
		return result;
	}

	@Override
	public int updateDeviceTempDeletedById(int tempId, boolean deleted) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"id", tempId).addValue("deleted", deleted);
		int result = this.getNamedParameterJdbcTemplate().update(
				UPDATE_DEV_TEMP_BY_ID, parameters);
		return result;
	}

	@Override
	public boolean createDeviceTempDynamicTable(int tempId,
			List<DeviceTempDynamicProp> dynamicPropList) {
		DeviceTempDynamicProp dynamicProp;
		StringBuffer sql = new StringBuffer("CREATE TABLE "
				+ Constants.DEVICE_TEMP_PREFIXE + tempId + "(");
		sql.append(" device_id bigint(10) NOT NULL ");
		for (int i = 0; i < dynamicPropList.size(); i++) {
			dynamicProp = dynamicPropList.get(i);
			sql.append(", " + dynamicProp.getEnglishName().trim() + "_"
					+ " nvarchar(100) DEFAULT NULL ");

		}
		sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		SqlParameterSource parameters = new MapSqlParameterSource();
		this.getNamedParameterJdbcTemplate().update(sql.toString(), parameters);
		return true;
	}

	@Override
	@Transactional
	public boolean updateDeviceTempDynamicTable(int tempId,
			List<DeviceTempDynamicProp> oldPropList,
			List<DeviceTempDynamicProp> newPropList) {
		SqlParameterSource parameters = new MapSqlParameterSource();
		StringBuffer sql = new StringBuffer();
		for (DeviceTempDynamicProp newProp : newPropList) {
			if (newProp.getId() != 0) {
				for (DeviceTempDynamicProp oldProp : oldPropList) {
					if (newProp.getId() == oldProp.getId()) {
						// 修改欄位
						sql = new StringBuffer("ALTER TABLE device_temp_"
								+ tempId + " CHANGE ");
						sql.append(oldProp.getEnglishName() + "_ "
								+ newProp.getEnglishName() + "_"
								+ " nvarchar(100) DEFAULT NULL;");
						this.getNamedParameterJdbcTemplate().update(
								sql.toString(), parameters);
						sql = new StringBuffer(
								"UPDATE device_temp_dynamic_properties SET ");
						sql.append("english_name='" + newProp.getEnglishName()
								+ "', ");
						sql.append("name='" + newProp.getName() + "', ");
						sql.append("deleted=" + newProp.isDeleted() + ", ");
						sql.append("fix=" + newProp.isFix() + " ");
						sql.append(" WHERE id=" + newProp.getId());
						this.getNamedParameterJdbcTemplate().update(
								sql.toString(), parameters);
					}
				}
			} else {
				// 新增欄位
				sql = new StringBuffer("ALTER TABLE device_temp_" + tempId
						+ " ADD COLUMN ");
				sql.append(newProp.getEnglishName() + "_"
						+ " nvarchar(100) DEFAULT NULL; ");
				this.getNamedParameterJdbcTemplate().update(sql.toString(),
						parameters);
				sql = new StringBuffer(
						"INSERT INTO device_temp_dynamic_properties( ");
				sql.append(" device_temp_id, english_name, name, deleted, fix)");
				sql.append("VALUES (" + newProp.getDeviceTempId() + ", ");
				sql.append("'" + newProp.getEnglishName() + "', ");
				sql.append("'" + newProp.getName() + "', ");
				sql.append(newProp.isDeleted() + ", ");
				sql.append(newProp.isFix() + ")");
				this.getNamedParameterJdbcTemplate().update(sql.toString(),
						parameters);
			}
		}
		return true;
	}

	@Override
	public int insertDeviceTempDynamicTableData(int tempId, int deviceId) {
		StringBuffer sql = new StringBuffer("INSERT INTO "
				+ Constants.DEVICE_TEMP_PREFIXE + tempId);
		sql.append("(device_id) VALUES (:device_id)");
		System.out.println(sql.toString());
		SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"device_id", deviceId);
		int result = this.getNamedParameterJdbcTemplate().update(
				sql.toString(), parameters);
		return result;
	}

	@Override
	public List<DeviceTempPropData> getDeviceTempDynamicTableData(int tempId,
			int deviceId, List<DeviceTempDynamicProp> tempDynamicPropList) {
		StringBuffer sql = new StringBuffer("SELECT * ");
		for (int i = 0; i < tempDynamicPropList.size(); i++) {
			if (i == 0) {
				sql.append(", ");
			}
			sql.append(tempDynamicPropList.get(i).getEnglishName() + "_");
			if (i != tempDynamicPropList.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(" FROM " + Constants.DEVICE_TEMP_PREFIXE + tempId);
		sql.append(" WHERE device_id = " + deviceId);

		List<DeviceTempPropData> tempPropDataList = new ArrayList<DeviceTempPropData>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		Map paramMap = getNamedParameterJdbcTemplate().queryForMap(
				sql.toString(), new HashMap());
		String englishName = "";
		for (DeviceTempDynamicProp tempDynamicProp : tempDynamicPropList) {
			DeviceTempPropData tempPropData = new DeviceTempPropData();
			englishName = tempDynamicProp.getEnglishName();
			tempPropData.setDeviceId(deviceId);
			tempPropData.setTempId(tempId);
			tempPropData.setEnglishName(englishName);
			tempPropData.setName(tempDynamicProp.getName());
			tempPropData
					.setValue((String) paramMap.get(englishName + "_") == null ? ""
							: (String) paramMap.get(englishName + "_"));
			tempPropData.setFix(tempDynamicProp.isFix());
			tempPropData.setDeleted(tempDynamicProp.isDeleted());
			tempPropDataList.add(tempPropData);
		}
		return tempPropDataList;
	}

	@Override
	public int updateDeviceTempDynamicTableData(int tempId, int deviceId,
			List<DeviceTempPropData> tempPropDataList) {
		StringBuffer sql = new StringBuffer("UPDATE "
				+ Constants.DEVICE_TEMP_PREFIXE + tempId + " SET ");
		for (int i = 0; i < tempPropDataList.size(); i++) {
			DeviceTempPropData tempPropData = tempPropDataList.get(i);
			sql.append(tempPropData.getEnglishName() + "_");
			sql.append(" = ' " + tempPropData.getValue() + " ' ");
			if (i != tempPropDataList.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(" WHERE device_id = " + deviceId);
		SqlParameterSource parameters = new MapSqlParameterSource();
		int result = this.getNamedParameterJdbcTemplate().update(
				sql.toString(), parameters);
		return result;
	}

	@Override
	public boolean isDeviceInTempDynamicTableExist(int deviceId, int tempId) {
		StringBuffer sql = new StringBuffer("SELECT device_id ");
		sql.append("FROM device_temp_" + tempId + " ");
		sql.append("WHERE device_id = " + deviceId);
		Map<String, String> paramMap = new HashMap<String, String>();
		List<Map<String, Object>> resultList = this
				.getNamedParameterJdbcTemplate().queryForList(sql.toString(),
						paramMap);
		if (resultList.size() > 0) {
			return true;
		}
		return false;
	}

}
