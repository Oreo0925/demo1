package com.flowring.cn.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Device {

	private int id;

	private int parentId;

	private String fullId;

	private String name;

	private String identifier;

	private String customer;

	private String ruleNote;

	private String ruleCondition;

	private String description;

	private boolean enabled;

	private boolean automatic;

	private int status;

	private int deviceTypeId;

	private int creator;

	private int tempId;

	private Date lastRecordTime;

	private long recordTimeLimit;

	@NotNull(message = "deleted is necessary")
	private boolean deleted;

	private Map<String, DeviceTempPropData> deviceTempPropDataMap = new HashMap<String, DeviceTempPropData>();

	private List<Group> parentGroupList = new ArrayList<Group>();

	private DeviceType deviceType;

	public Device() {
		super();
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getRuleNote() {
		return ruleNote;
	}

	public void setRuleNote(String ruleNote) {
		this.ruleNote = ruleNote;
	}

	public String getRuleCondition() {
		return ruleCondition;
	}

	public void setRuleCondition(String ruleCondition) {
		this.ruleCondition = ruleCondition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

	public void setDeviceTempPropDataList(
			List<DeviceTempPropData> deviceTempPropDataList) {
		for (DeviceTempPropData deviceTempPropData : deviceTempPropDataList) {
			this.deviceTempPropDataMap.put(deviceTempPropData.getEnglishName(),
					deviceTempPropData);
		}
	}

	public Map<String, DeviceTempPropData> getDeviceTempPropDataMap() {
		return deviceTempPropDataMap;
	}

	public List<Group> getParentGroupList() {
		return parentGroupList;
	}

	public void setParentGroupList(List<Group> parentGroupList) {
		this.parentGroupList = parentGroupList;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getLastRecordTime() {
		return lastRecordTime;
	}

	public void setLastRecordTime(Date lastRecordTime) {
		this.lastRecordTime = lastRecordTime;
	}

	public long getRecordTimeLimit() {
		return recordTimeLimit;
	}

	public void setRecordTimeLimit(long recordTimeLimit) {
		this.recordTimeLimit = recordTimeLimit;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
