package com.flowring.cn.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeviceTemp {

	@NotNull(message = "id is necessary")
	private int id;

	@NotNull(message = "tempName is necessary")
	private String name;

	@NotNull(message = "deleted is necessary")
	private boolean deleted;

	private List<DeviceTempDynamicProp> dynamicPropList = new ArrayList<DeviceTempDynamicProp>();

	public DeviceTemp() {
		super();
	}

	public DeviceTemp(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<DeviceTempDynamicProp> getDynamicPropList() {
		return dynamicPropList;
	}

	public void setDynamicPropList(List<DeviceTempDynamicProp> dynamicPropList) {
		this.dynamicPropList = dynamicPropList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
