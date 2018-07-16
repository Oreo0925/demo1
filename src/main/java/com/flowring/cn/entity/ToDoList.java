package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ToDoList {
	@NotNull(message = "id is necessary")
	private int id;
	
	@NotNull(message = "ruleId is necessary")
	private int ruleId;
	
	private String ruleConditions;
	
	private String ruleStatus;
	
	private String startTime;
	
	private String finishTime = "";
	
	private boolean finished;
	
	private String feedback = "";
	
	public ToDoList() {
		super();
	}
	
	public ToDoList(int id, int ruleId, String ruleConditions, String ruleStatus, String startTime, String finishTime, boolean finished, String feedback) {
		super();
		this.id = id;
		this.ruleId = ruleId;
		this.ruleConditions = ruleConditions;
		this.ruleStatus = ruleStatus;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.finished = finished;
		this.feedback = feedback;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
