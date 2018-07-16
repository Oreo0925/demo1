package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.DeviceTempDynamicProp;

public interface DeviceTempDynamicPropertiesDao {

	public List<DeviceTempDynamicProp> getDevTempDynamicPropListByTempId(
			int tempId);

	public List<DeviceTempDynamicProp> getDevTempDynamicPropListByTempIdAndDeleted(
			int tempId, boolean deleted);

	public DeviceTempDynamicProp getDevTempDynamicPropById(
			int ud);

	// insert one return last_id
	public int insertDevTempDynamicPropList(
			List<DeviceTempDynamicProp> dynamicPropList);
	
	public int insertDevTempDynamicPropList(int tempId,
			List<DeviceTempDynamicProp> dynamicPropList);
			
	// 修改 DeviceTempDynamicProperties 的 deleted 欄位
	public int updateDevTempDynamicPropDeletedByTempId(int tempId,
			boolean deleted);

	// 修改 List<DeviceTempDynamicProperties> 的 deleted 欄位
	public int updateDevTempDynamicPropListDeleted(
			List<DeviceTempDynamicProp> tempDynamicPropertiesList,
			boolean deleted);

	public int updateDevTempDynamicProperList(
			List<DeviceTempDynamicProp> tempDynamicPropertiesList);

	public int isDevDynamicPropExist(int id);
	
}
