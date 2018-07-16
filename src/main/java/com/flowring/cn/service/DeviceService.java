package com.flowring.cn.service;

import java.util.Date;
import java.util.List;
import java.util.Map;





import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceProperties;
import com.flowring.cn.entity.DeviceStaticProperties;
import com.flowring.cn.entity.DeviceTemp;
import com.flowring.cn.entity.DeviceTempDynamicProp;
import com.flowring.cn.entity.DeviceTempPropData;
import com.flowring.cn.entity.DeviceType;

public interface DeviceService {
	/* Device */
	public List<Device> getAllDevice();

	public List<Device> getDeviceList();

	public Device getDeviceById(int id);
	
	public Device getDeviceByFullId(String fullId);

	public List<Device> getDeviceListByParentId(int parentId);
	
	public List<Device> getDeviceListByGroupsId(int groupId);
	
	public Device getDeviceByIdentifierAndCustomer(String identifier, String customer);
	
	public int updateDeviceLastRecordTimeById(int id, Device device);
	
	// insert 回傳 Device 給前端
	public int insertDevice(Device device);

	public int batchInsertDeviceList(List<Device> devicelist);

	public int updateDevice(Device device);

	// Delete device is change isDeleted to "false" from true.
	public int deleteDeviceById(int id);

	public int batchDeleteDeviceList(List<Device> deviceList);

	/* DeviceStaticProperties */
	public List<DeviceStaticProperties> getAllDevStaticPropList();

	public DeviceStaticProperties getDevStaticPropListById(int id);

	public int batchInsertDevStaticPropList(
			List<DeviceStaticProperties> staticPropList);

	public int batchUpdateDevStaticPropList(
			List<DeviceStaticProperties> staticPropList);

	/* DeviceTempDynamicProperties */
	public List<DeviceTempDynamicProp> getDevTempDynamicPropByTempId(int tempId);

	public DeviceTempDynamicProp getDevTempDynamicPropById(int id);

	// insert 多筆回傳 List
	public int batchInsertDevTempDynamicPropList(
			List<DeviceTempDynamicProp> tempDynamicPropList);

	// properties 的 deleted 欄位改為 true
	public int deleteDevTempDynamicPropByTempId(int tempId);

	public int batchDeletedDeviceTempDynamicPropList(
			List<DeviceTempDynamicProp> tempDynamicPropertiesList);

	public int batchUpdateDeviceTempPropertiesList(
			List<DeviceTempDynamicProp> tempDynamicPropertiesList);

	public boolean isDevDynamicPropExist(int id);

	/* DeviceProperties */
	public List<DeviceProperties> getAllDeviceProperties();

	public List<DeviceProperties> getDevicePropertiesByDeviceId(int deviceId);

	public DeviceProperties getDevicePropertiesById(int id);

	public int insertDeviceProperties(
			List<DeviceProperties> devicePropertiesList);

	public int updateDeviceProperties(
			List<DeviceProperties> devicePropertiesList);

	public boolean deleteDevicePropertiesList(
			List<DeviceProperties> devicePropertiesList);

	// 裝置屬性設定(查詢出靜態及動態，回傳給前端)
	public Map<String, Object> devicePropertiesSetting(Integer tempId);

	/* DeviceTemp */
	public List<DeviceTemp> getAllDeviceTemp();

	public DeviceTemp getDeviceTempByTempId(int tempId);

	public int insertDeviceTemp(DeviceTemp deviceTemp);

	public int updateDeviceTemp(DeviceTemp deviceTemp);

	public int deletedDeviceTempByTempId(int tempId);

	public int insertDeviceTempDynamicTableData(int tempId, int deviceId);

	public List<DeviceTempPropData> getDeviceTempDynamicTableData(int tempId,
			int deviceId, List<DeviceTempDynamicProp> tempDynamicPropList);

	public int batchUpdateDeviceTempDynamicTableData(int tempId, int deviceId,
			List<DeviceTempPropData> tempPropDataList);
	
	public boolean isDeviceInTempDynamicTableExist(int deviceId, int tempId);
	
	/* type */
	public DeviceType getDeviceTypeById(int id);

	public int insertDeviceType(DeviceType deviceType);

	public int updateDeviceType(DeviceType deviceType);

	public int deleteDeviceType(int id);

}
