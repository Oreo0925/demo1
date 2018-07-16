package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RuleLog {
	@NotNull(message = "id is necessary")
	private int id;
	
	@NotNull(message = "ip is necessary")
	private String ip;
	
	@NotNull(message = "memberId is necessary")
	private int memberId;
	
	@NotNull(message = "deviceId is necessary")
	private int deviceId;
	
	@NotNull(message = "groupsId is necessary")
	private int groupsId;
	
	private String groupsName;
	
	@NotNull(message = "ruleId is necessary")
	private int ruleId;
	
	@NotNull(message = "ruleActionId is necessary")
	private int ruleActionId;
	
	private String ruleConditions;
	
	private String ruleStatus;
	
	private String feedback;
	
	private String payloadData;
	
	private String triggerTime;
	
	public RuleLog() {
		super();
	}
	
	public RuleLog(int id, String ip, int memberId, int deviceId, int groupsId, String groupsName, int ruleId,
			int ruleActionId, String ruleConditions, String ruleStatus, String feedback, String payloadData, String triggerTime) {
		super();
		this.id = id;
		this.ip = ip;
		this.memberId = memberId;
		this.deviceId = deviceId;
		this.groupsId = groupsId;
		this.groupsName = groupsName;
		this.ruleId = ruleId;
		this.ruleActionId = ruleActionId;
		this.ruleConditions = ruleConditions;
		this.ruleStatus = ruleStatus;
		this.feedback = feedback;
		this.payloadData = payloadData;
		this.triggerTime = triggerTime;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getGroupsId() {
		return groupsId;
	}

	public void setGroupsId(int groupsId) {
		this.groupsId = groupsId;
	}

	public String getGroupsName() {
		return groupsName;
	}

	public void setGroupsName(String groupsName) {
		this.groupsName = groupsName;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getRuleActionId() {
		return ruleActionId;
	}

	public void setRuleActionId(int ruleActionId) {
		this.ruleActionId = ruleActionId;
	}

	public String getRuleConditions() {
		return ruleConditions;
	}

	public void setRuleConditions(String ruleConditions) {
		this.ruleConditions = ruleConditions;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public String getPayloadData() {
		return payloadData;
	}

	public void setPayloadData(String payloadData) {
		this.payloadData = payloadData;
	}

	public String getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
