package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeviceType {

	private int id;

	private String name;

	private String protocal;

	private String frequency;

	public DeviceType() {
		super();
	}

	public DeviceType(int id, String name, String protocal, String frequency) {
		super();
		this.id = id;
		this.name = name;
		this.protocal = protocal;
		this.frequency = frequency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
