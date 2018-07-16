package com.flowring.cn.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowring.cn.enums.ConnectionType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SensorData {

	private String customerId;
	private String key;
	private int deviceId;
	private String customerDeviceId;
	private String identify;
	private String from;
	private String ip;
	private ConnectionType connectionType;
	private Date time;
	private JsonNode values;
	private Map<String, String> data;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getCustomerDeviceId() {
		return customerDeviceId;
	}

	public void setCustomerDeviceId(String customerDeviceId) {
		this.customerDeviceId = customerDeviceId;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public SensorData() {
		time = Calendar.getInstance().getTime();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public JsonNode getValues() {
		return values;
	}

	public void setValues(JsonNode values) {
		try {
			data = new Gson().fromJson(values.toString(), new TypeToken<HashMap<String, String>>() {
			}.getType());
		} catch (Exception e) {

		}

		this.values = values;
	}

	public void setValueString(String values) {
		try {

			data = new Gson().fromJson(values.toString(), new TypeToken<HashMap<String, String>>() {
			}.getType());

		} catch (Exception e) {

		}

	}

}
