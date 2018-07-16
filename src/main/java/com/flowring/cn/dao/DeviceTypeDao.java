package com.flowring.cn.dao;

import com.flowring.cn.entity.DeviceType;

public interface DeviceTypeDao {
	public DeviceType getDeviceTypeById(int id);
	public int insertDeviceType(DeviceType deviceType);
	public int updateDeviceType(DeviceType deviceType);
	public int deleteDeviceType(int id);
}
