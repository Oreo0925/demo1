package com.flowring.cn.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Role {
	
	private int id;
	
	@NotNull(message = "Name is necessary")
	private String name;
	
	@NotNull(message = "Description is necessary")
	private String description;
	
	@NotNull(message = "Creater is necessary")
	private int creater;
	
	@NotNull(message = "Priority is necessary")
	private int priority;
	
	private List<Group> groupList;
	
	public Role() {
		super();
	}
	
	public Role(int id, String name, String description, int creater, int priority, List<Group> groupList) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.creater = creater;
		this.priority = priority;
		this.groupList = groupList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
