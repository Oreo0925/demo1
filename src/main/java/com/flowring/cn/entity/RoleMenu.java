package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RoleMenu {

	private int roleId;

	private int menuId;

	public RoleMenu() {
		super();
	}

	public RoleMenu(int roleId, int menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
