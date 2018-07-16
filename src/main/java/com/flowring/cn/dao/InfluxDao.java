package com.flowring.cn.dao;

import com.flowring.cn.entity.SensorData;

public interface InfluxDao {
	
	public boolean addSensorData(SensorData sensorData);

}
