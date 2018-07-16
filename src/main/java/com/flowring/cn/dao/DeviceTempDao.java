package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.DeviceTemp;
import com.flowring.cn.entity.DeviceTempDynamicProp;
import com.flowring.cn.entity.DeviceTempPropData;

public interface DeviceTempDao {
	public List<DeviceTemp> getAllDeviceTemp();

	public DeviceTemp getDeviceTempByTempId(int tempId);

	public int insertDeviceTemp(DeviceTemp deviceTemp);

	public int updateDeviceTemp(DeviceTemp deviceTemp);

	public int updateDeviceTempDeletedById(int tempId, boolean deleted);

	public boolean createDeviceTempDynamicTable(int tempId,
			List<DeviceTempDynamicProp> dynamicPropList);

	public boolean updateDeviceTempDynamicTable(int tempId,
			List<DeviceTempDynamicProp> oldPropList,
			List<DeviceTempDynamicProp> newPropList);

	public int insertDeviceTempDynamicTableData(int tempId, int deviceId);

	public List<DeviceTempPropData> getDeviceTempDynamicTableData(int tempId,
			int deviceId, List<DeviceTempDynamicProp> tempDynamicPropList);

	public int updateDeviceTempDynamicTableData(
			int tempId, int deviceId, List<DeviceTempPropData> tempPropDataList);
	
	public boolean isDeviceInTempDynamicTableExist(int deviceId, int tempId);
}
