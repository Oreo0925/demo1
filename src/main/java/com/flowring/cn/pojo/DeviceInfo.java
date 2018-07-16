package com.flowring.cn.pojo;

import java.util.List;

import com.flowring.cn.entity.DeviceProperties;

public class DeviceInfo {
	
	int id;
	
	int parentId;
	
	int groupId;
	
	boolean deleted;
	
	List<DeviceProperties> properties;
	
	String groupName;
	
	
}
