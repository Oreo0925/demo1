package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RuleGroup {

	private int ruleId;
	private int groupId;

	public RuleGroup() {
		super();
	}

	public RuleGroup(int ruleId, int groupId) {
		super();
		this.ruleId = ruleId;
		this.groupId = groupId;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
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
