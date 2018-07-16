package com.flowring.cn.service;

import com.flowring.cn.entity.SensorData;
import com.flowring.cn.entity.SensorServiceResponse;

public interface SensorService {
	
	public SensorServiceResponse saveData(String from, String ip, SensorData sensorData);
	
	public SensorServiceResponse saveData(String from, String ip, String customerDeviceId, String json);
	
	public void saveDataMQTT(String topic, SensorData sensorData);
	
	public void saveDataMQTT(String topic, String json);

}
