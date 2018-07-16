package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RoleFunction {
	private int roleId;
	private int functionId;
	
	public RoleFunction() {
		super();
	}
	
	public RoleFunction(int roleId, int functionId) {
		super();
		this.roleId = roleId;
		this.functionId = functionId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
