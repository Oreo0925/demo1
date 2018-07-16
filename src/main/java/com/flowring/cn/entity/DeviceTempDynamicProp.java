package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeviceTempDynamicProp {

	@NotNull(message = "id is necessary")
	private int id;

	@NotNull(message = "deviceTempId is necessary")
	private int deviceTempId;

	@NotNull(message = "englishName is necessary")
	private String englishName;
	@NotNull(message = "name is necessary")
	private String name;
	@NotNull(message = "deleted is necessary")
	private boolean deleted;

	@NotNull(message = "fix is necessary")
	private boolean fix;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeviceTempId() {
		return deviceTempId;
	}

	public void setDeviceTempId(int deviceTempId) {
		this.deviceTempId = deviceTempId;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
