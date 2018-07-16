package com.flowring.cn.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
	public final static String EMAILAUTH = "success";
	public final static String NODATA = "查無資料";
	public final static String REGISTER_MAIL = "慶漁堂註冊驗證信";
	public final static String CHANGE_PASSWORD_MAIL = "慶漁堂 您忘記密碼了嗎?";
	public final static String INVITED_MEMBER_MAIL = "慶漁堂 邀請註冊信";
	public static String VERIFY_REGISTER = "/verify/register/";	//點下註冊驗證信的驗證 Url
	public static String VERIFY_CHANGE_PASSWD = "/verify/password/";	//點下修改密碼的驗證 Url
	public static String VERIFY_NON_REGISTER = "/verify/invite/";	//用於 user 尚未註冊而被邀請, 點下註冊驗證信的驗證 Url
	
	public final static String DEVICE_DYNAMIC = "dynamic";
	public final static String DEVICE_STATIC = "static";
	public final static String DEVICE_TEMP_PREFIXE = "device_temp_";
	
	public static String INFLUXDB_API_URL;
	public static String INFLUXDB_URL;
	public static String MQTT_BROKER;
	
	public static String SENSOR_ROUTE_PORT;
	
	@Value("${com.flowring.influxdb.api}") 
	public void setInfluxDbApiURL(String influxdbApiUrl) {  
		INFLUXDB_API_URL = influxdbApiUrl;
	}  
	
	@Value("${com.flowring.influxdb.url}") 
	public void setInfluxDbURL(String influxdbUrl) {  
		INFLUXDB_URL = influxdbUrl;
	}  
	
	@Value("${com.flowring.mqtt.broker}")
	public void setMqttBroker(String mqttBroker) {
		MQTT_BROKER = mqttBroker;
	}

	@Value("${com.flowring.netty.sensor.route.port}")
	public static void setSensorRoutePort(String sensorRoutePort) {
		SENSOR_ROUTE_PORT = sensorRoutePort;
	}
	
}
