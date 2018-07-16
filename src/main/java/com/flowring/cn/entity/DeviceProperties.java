package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeviceProperties {

	private int id = 0;

	private int deviceId = 0;

	private String name = "";

	private String value = "";

	private int fix = 0;

	public DeviceProperties() {
		super();
	}

	public DeviceProperties(int id, int deviceId, String name, String value,
			int fix) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.name = name;
		this.value = value;
		this.fix = fix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getFix() {
		return fix;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
