package com.flowring.cn.service;

import java.util.List;
import java.util.Map;

import com.flowring.cn.entity.DeviceGroup;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.GroupProperties;

public interface GroupService {
	public List<Group> getAllGroup();
	
	public Map<Integer, Group> getAllGroupsByKeyIsId();
	
	public Map<Integer, Group> getAllGroupsByKeyParentId();
	
	public Group getGroupById(int id);
	
	public List<Group> getGroupByName(String name);
	
	public List<Group> getGroupByCreator(int creator);

	public List<Group> getGroupByParentId(int parentId);

	// insert 回傳最後一筆 id
	public int insertGroup(Group group);

	public int updateGroup(Group group);

	public int deleteGroupById(int groupId);

	public List<DeviceGroup> getAllDeviceGroup();

	public List<DeviceGroup> getDeviceGroupByDeviceId(int deviceId);

	public List<DeviceGroup> getDeviceGroupByGroupsId(int groupId);

	public int insertDeviceGroup(DeviceGroup deviceGroups);

	public int batchInsertDeviceGroup(List<DeviceGroup> deviceGroupList);

	public int batchDeleteDeviceGroupList(List<DeviceGroup> deviceGroupList);

	/* GroupProperties */
	public List<GroupProperties> getAllGroupPropList();

	public List<GroupProperties> getGroupPropListByGroupId(int groupId);

	public GroupProperties getGroupPropById(int id);

	public int batchInsertGroupPropList(List<GroupProperties> groupPropList);

	public int batchUpdateGroupPropList(List<GroupProperties> groupPropList);

	public int deleteGroupPropById(int id);

	public int batchDeleteGroupPropList(List<GroupProperties> groupPropList);

	public List<Group> getGroupListByDeviceId(int deviceId);
}
