package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Rule {
	@NotNull(message = "id is necessary")
	private int id;
	
	@NotNull(message = "typeId is necessary")
	private int deviceTypeId;
	
	@NotNull(message = "title is necessary")
	private String title;
	
	@NotNull(message = "modes is necessary")
	private int modes;
	
	@NotNull(message = "conditions is necessary")
	private String conditions;
	
	@NotNull(message = "actionType is necessary")
	private int actionType;
	
	private String status;
	
	private int actionId;
	
	public Rule() {
		super();
	}
	
	public Rule(int id, int deviceTypeId, String title, int modes, String conditions, int actionType, String status, int actionId) {
		super();
		this.id = id;
		this.deviceTypeId = deviceTypeId;
		this.title = title;
		this.modes = modes;
		this.conditions = conditions;
		this.actionType = actionType;
		this.status = status;
		this.actionId = actionId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getModes() {
		return modes;
	}

	public void setModes(int modes) {
		this.modes = modes;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
