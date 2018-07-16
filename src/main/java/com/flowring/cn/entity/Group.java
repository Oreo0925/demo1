package com.flowring.cn.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Group {

	private int id;

	private int parentId;

	private String name = "";

	private String location = "";

	private String description = "";

	private int creater;

	private int mapType;

	private boolean deleted;

	private Map<String, GroupProperties> propMap = new HashMap<String, GroupProperties>();

	private List<Device> childDevcieList = new ArrayList<Device>();
	
	private List<Role> roleList;
	
	private List<GroupProperties> groupPropertiesList;
	
	private String parentGroupName;
	
	private List<Group> subGroupList;
	
	public Group() {
		super();
	}

	public Group(int id, int parentId, String name, int creater, int mapType,
			List<Device> childDevcieList, List<Role> roleList, List<GroupProperties> groupPropertiesList, String parentGroupName,
			List<Group> subGroupList) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.creater = creater;
		this.mapType = mapType;
		this.childDevcieList = childDevcieList;
		this.roleList = roleList;
		this.groupPropertiesList = groupPropertiesList;
		this.parentGroupName = parentGroupName;
		this.subGroupList = subGroupList;
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

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public int getMapType() {
		return mapType;
	}

	public void setMapType(int mapType) {
		this.mapType = mapType;
	}

	public List<Device> getChildDevcieList() {
		return childDevcieList;
	}

	public void setChildDevcieList(List<Device> childDevcieList) {
		this.childDevcieList = childDevcieList;
	}

	public Map<String, GroupProperties> getPropMap() {
		return propMap;
	}

	public void setPropList(List<GroupProperties> groupPropList) {
		for (GroupProperties groupProp : groupPropList) {
			this.propMap.put(groupProp.getEnglishName(), groupProp);
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<GroupProperties> getGroupPropertiesList() {
		return groupPropertiesList;
	}

	public void setGroupPropertiesList(List<GroupProperties> groupPropertiesList) {
		this.groupPropertiesList = groupPropertiesList;
	}
	
	public String getParentGroupName() {
		return parentGroupName;
	}

	public void setParentGroupName(String parentGroupName) {
		this.parentGroupName = parentGroupName;
	}

	public List<Group> getSubGroupList() {
		return subGroupList;
	}

	public void setSubGroupList(List<Group> subGroupList) {
		this.subGroupList = subGroupList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
