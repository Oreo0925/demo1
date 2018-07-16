package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RuleDevice {

	private int ruleId;

	private int deviceId;

	public RuleDevice() {
		super();
	}

	public RuleDevice(int ruleId, int deviceId) {
		super();
		this.ruleId = ruleId;
		this.deviceId = deviceId;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
