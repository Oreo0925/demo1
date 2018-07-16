package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Functions {
	@NotNull(message = "id is necessary")
	private int id;
	
	private int menuId;
	
	private String name;
	
	private String description;
	
	public Functions() {
		super();
	}
	
	public Functions(int id, int menuId, String title, String name, String description) {
		super();
		this.id = id;
		this.menuId = menuId;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
