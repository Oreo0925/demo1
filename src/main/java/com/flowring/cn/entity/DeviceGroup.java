package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeviceGroup {

	private int deviceId = 0;

	private int groupId = 0;

	public DeviceGroup() {
		super();
	}

	public DeviceGroup(int deviceId, int groupId) {
		super();
		this.deviceId = deviceId;
		this.groupId = groupId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
