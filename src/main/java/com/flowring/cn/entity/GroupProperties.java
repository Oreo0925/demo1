package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GroupProperties {

	private int id;

	@NotNull(message = "groupId is necessary")
	private int groupId;

	@NotNull(message = "englishName is necessary")
	private String englishName;

	@NotNull(message = "name is necessary")
	private String name;

	@NotNull(message = "value is necessary")
	private String value;

	@NotNull(message = "fix is necessary")
	private boolean fix;

	@NotNull(message = "deleted is necessary")
	private boolean deleted;

	public GroupProperties() {
		super();
	}

	public GroupProperties(int id, int groupId, String englishName,
			String name, String value, boolean fix, boolean deleted) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.englishName = englishName;
		this.name = name;
		this.value = value;
		this.fix = fix;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
