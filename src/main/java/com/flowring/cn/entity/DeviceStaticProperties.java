package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public class DeviceStaticProperties {

	@NotNull(message = "id is necessary")
	private int id;

	@NotNull(message = "englishName is necessary")
	private String englishName;

	@NotNull(message = "name is necessary")
	private String name;

	@NotNull(message = "fix is necessary")
	private boolean fix;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
