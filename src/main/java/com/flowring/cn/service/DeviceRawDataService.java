package com.flowring.cn.service;

import java.util.List;

import com.flowring.cn.entity.DeviceRawData;
import com.flowring.cn.entity.InfluxOperate;
import com.flowring.cn.entity.InfluxQuerySeries;

public interface DeviceRawDataService {

	public List<DeviceRawData> getDevicesRawDataOfGroup(int groupId, InfluxOperate influxOperate);
	
	public InfluxQuerySeries getDevicesRawData(int deviceId, InfluxOperate influxOperate);
	
}
