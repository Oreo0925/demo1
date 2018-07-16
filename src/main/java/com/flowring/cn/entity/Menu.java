package com.flowring.cn.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Menu {

	private int id;

	private int parentId;

	private String name;

	private String description;

	private int sequence;

	public Menu() {
		super();
	}

	public Menu(int id, int parentId, String name, String description,
			int sequence) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.description = description;
		this.sequence = sequence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
