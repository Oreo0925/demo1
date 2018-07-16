package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Payload {
	@NotNull(message = "id is necessary")
	private int id;
	
	@NotNull(message = "deviceTypeId is necessary")
	private int deviceTypeId;
	
	@NotNull(message = "keyName is necessary")
	private String keyName;
	
	private String definition;
	
	private String unit;
	
	private String deviceTypeName;
	
	public Payload() {
		super();
	}
	
	public Payload(int id, int deviceTypeId, String keyName, String definition, String unit, String deviceTypeName) {
		super();
		this.id = id;
		this.deviceTypeId = deviceTypeId;
		this.keyName = keyName;
		this.definition = definition;
		this.unit = unit;
		this.deviceTypeName = deviceTypeName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
