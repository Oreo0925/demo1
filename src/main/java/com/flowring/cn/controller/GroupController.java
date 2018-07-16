package com.flowring.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.entity.DeviceGroup;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.GroupProperties;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.GroupService;

@RestController
@RequestMapping("/")
public class GroupController {

	@Autowired
	private GroupService groupManagementService;

	/*
	 * 查詢群組
	 */
	@GetMapping("/groups")
	public SingleResponseObject<List<Group>> getAllGroup() {
		SingleResponseObject<List<Group>> ero = new SingleResponseObject<List<Group>>();
		try {
			List<Group> groupList = groupManagementService.getAllGroup();
			ero.setData(groupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢群組 ById
	 */
	@GetMapping("/group/{id}")
	public SingleResponseObject<Group> getGroupById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Group> ero = new SingleResponseObject<Group>();
		try {
			Group group = groupManagementService.getGroupById(id);
			ero.setData(group);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	/*
	 * 查詢群組 By Name
	 */
	@GetMapping("/group/name/{name}")
	public SingleResponseObject<List<Group>> getGroupByName(
			@PathVariable("name") String name) {
		SingleResponseObject<List<Group>> ero = new SingleResponseObject<List<Group>>();
		try {
			List<Group> groupList = groupManagementService.getGroupByName(name);
			ero.setData(groupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢群組 ByParentId
	 */
	@GetMapping("/groups/parentId/{parentId}")
	public SingleResponseObject<List<Group>> getGroupByParentId(
			@PathVariable("parentId") int parentId) {
		SingleResponseObject<List<Group>> ero = new SingleResponseObject<List<Group>>();
		try {
			List<Group> groupList = groupManagementService
					.getGroupByParentId(parentId);
			ero.setData(groupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	/*
	 * 查詢群組 ByCreator
	 */
	@GetMapping("/groups/creator/{creator}")
	public SingleResponseObject<List<Group>> getGroupByCreator(
			@PathVariable("creator") int creator) {
		SingleResponseObject<List<Group>> ero = new SingleResponseObject<List<Group>>();
		try {
			List<Group> groupList = groupManagementService
					.getGroupByCreator(creator);
			ero.setData(groupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增群組
	 */
	@PostMapping("/group")
	public SingleResponseObject<Group> insertGroup(@RequestBody Group group) {
		SingleResponseObject<Group> ero = new SingleResponseObject<Group>();
		try {
			int lastId = groupManagementService.insertGroup(group);
			group.setId(lastId);
			ero.setData(group);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改群組 ById
	 */
	@PutMapping("/group")
	public SingleResponseObject<Object> updateGroups(@RequestBody Group group) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			int count = groupManagementService.updateGroup(group);
			ero.setTotal(count);
			ero.setData(group);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 刪除群組 ById
	 */
	@DeleteMapping("/group/{id}")
	public SingleResponseObject<Integer> deleteGroupById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int rs = groupManagementService.deleteGroupById(id);
			ero.setTotal(rs);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢全部裝置與群組的對應
	 */
	@GetMapping("/groups/devices")
	public SingleResponseObject<List<DeviceGroup>> getAllDeviceGroup() {
		SingleResponseObject<List<DeviceGroup>> ero = new SingleResponseObject<List<DeviceGroup>>();
		try {
			List<DeviceGroup> deviceGroupList = groupManagementService
					.getAllDeviceGroup();
			ero.setData(deviceGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢某裝置的群組 By deviceId
	 */
	@GetMapping("/groups/device/{deviceId}")
	public SingleResponseObject<List<DeviceGroup>> getDeviceGroupByDeviceId(
			@PathVariable("deviceId") Integer deviceId) {
		SingleResponseObject<List<DeviceGroup>> ero = new SingleResponseObject<List<DeviceGroup>>();
		try {
			List<DeviceGroup> deviceGroupList = groupManagementService
					.getDeviceGroupByDeviceId(deviceId);
			ero.setData(deviceGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢某群組的裝置 By GroupId
	 */
	@GetMapping("/group/devices/{groupId}")
	public SingleResponseObject<List<DeviceGroup>> getDeviceGroupByGroupsId(
			@PathVariable("groupId") Integer groupId) {
		SingleResponseObject<List<DeviceGroup>> ero = new SingleResponseObject<List<DeviceGroup>>();
		try {
			List<DeviceGroup> deviceGroupList = groupManagementService
					.getDeviceGroupByGroupsId(groupId);
			ero.setData(deviceGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 裝置入群
	 */
	@PostMapping("/group/device")
	public SingleResponseObject<Object> insertDeviceGroup(
			@RequestBody DeviceGroup deviceGroup) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			int total = groupManagementService.insertDeviceGroup(deviceGroup);
			ero.setTotal(total);
			ero.setData(deviceGroup);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 裝置入群List 如果deviceGroupList中deviceId或groupId為0，無處理。
	 */
	@PostMapping("/groups/devices")
	public SingleResponseObject<Integer> insertDeviceGroupList(
			@RequestBody List<DeviceGroup> deviceGroupList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = groupManagementService
					.batchInsertDeviceGroup(deviceGroupList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 裝置移出群組List
	 */
	@DeleteMapping("/groups/devices")
	public SingleResponseObject<Integer> deleteDeviceGroupByDeviceIdAndGroupId(
			@RequestBody List<DeviceGroup> deviceGroup) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = groupManagementService
					.batchDeleteDeviceGroupList(deviceGroup);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查群群組動態屬性
	 */
	@GetMapping("/groups/properties")
	public SingleResponseObject<List<GroupProperties>> getAllGroupProperties() {
		SingleResponseObject<List<GroupProperties>> ero = new SingleResponseObject<List<GroupProperties>>();
		try {
			List<GroupProperties> groupPropertiesList = groupManagementService
					.getAllGroupPropList();
			ero.setData(groupPropertiesList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查群群組動態屬性 ByGroupId
	 */
	@GetMapping("/group/properties/{groupId}")
	public SingleResponseObject<List<GroupProperties>> getGroupPropertiesByGroupId(
			@PathVariable("groupId") Integer groupId) {
		SingleResponseObject<List<GroupProperties>> ero = new SingleResponseObject<List<GroupProperties>>();
		try {
			List<GroupProperties> groupsPropertiesList = groupManagementService
					.getGroupPropListByGroupId(groupId);
			ero.setData(groupsPropertiesList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查群群組動態屬性 ById
	 */
	@GetMapping("/group/property/{id}")
	public SingleResponseObject<GroupProperties> getGroupPropertiesById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<GroupProperties> ero = new SingleResponseObject<GroupProperties>();
		try {
			GroupProperties groupProperties = groupManagementService
					.getGroupPropById(id);
			ero.setData(groupProperties);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 新增群組動態屬性 List
	 */
	@PostMapping("/groups/properties")
	public SingleResponseObject<Integer> insertGroupProperties(
			@RequestBody List<GroupProperties> groupPropertiesList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = groupManagementService
					.batchInsertGroupPropList(groupPropertiesList);
			ero.setData(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 修改群組動態屬性 List
	 */
	@PutMapping("/groups/properties")
	public SingleResponseObject<Object> updateGroupProperties(
			@RequestBody List<GroupProperties> groupPropertiesList) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			int total = groupManagementService
					.batchUpdateGroupPropList(groupPropertiesList);
			ero.setTotal(total);
			ero.setData(groupPropertiesList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除新增群組動態屬性 ById
	 */
	@DeleteMapping("/group/property/{id}")
	public SingleResponseObject<Integer> deleteGroupPropertiesById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = groupManagementService.deleteGroupPropById(id);
			ero.setData(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除新增群組動態屬性 List
	 */
	@DeleteMapping("/groups/properties")
	public SingleResponseObject<Integer> deleteGroupPropertiesList(
			@RequestBody List<GroupProperties> devicePropertiesList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = groupManagementService
					.batchDeleteGroupPropList(devicePropertiesList);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
}
