package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MemberRole {
	private int memberId;
	private int roleId;
	
	public MemberRole() {
		super();
	}
	
	public MemberRole(int memberId, int roleId) {
		super();
		this.memberId = memberId;
		this.roleId = roleId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
