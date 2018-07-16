package com.flowring.cn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.DeviceGroupDao;
import com.flowring.cn.dao.GroupDao;
import com.flowring.cn.dao.GroupPropertiesDao;
import com.flowring.cn.dao.RoleDao;
import com.flowring.cn.dao.RoleGroupDao;
import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceGroup;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.GroupProperties;
import com.flowring.cn.entity.Role;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.service.GroupService;
import com.flowring.cn.service.MenuService;
import com.flowring.cn.util.Constants;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private GroupPropertiesDao groupPropertiesDao;

	@Autowired
	private DeviceGroupDao deviceGroupDao;

	@Autowired
	private DeviceService deviceManagementService;
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleGroupDao roleGroupDao;
	
	/*
	 * 查詢未被刪除的裝置 (non-Javadoc)
	 * @see com.flowring.cn.service.GroupManagementService#getAllGroup()
	 */
	@Override
	public List<Group> getAllGroup() {
		List<Group> groupList = groupDao.getGroupListByDeleted(false);
		for (Group group : groupList) {
			List<GroupProperties> groupPropList = groupPropertiesDao
					.getGroupPropListByGroupIdAndDeleted(group.getId(), false);
			group.setPropList(groupPropList);
		}
		return groupList;
	}
	
	@Override
	public Map<Integer, Group> getAllGroupsByKeyIsId() {
		Map<Integer, Group> groupMap = new HashMap<Integer, Group>();
		List<Group> groupList = groupDao.getGroupListByDeleted(false);
		for (Group group : groupList) {
			List<GroupProperties> groupPropList = groupPropertiesDao
					.getGroupPropListByGroupIdAndDeleted(group.getId(), false);
			group.setPropList(groupPropList);
			groupMap.put(group.getId(), group);
		}
		return groupMap;
	}
	
	@Override
	public Map<Integer, Group> getAllGroupsByKeyParentId() {
		Map<Integer, Group> groupMap = new HashMap<Integer, Group>();
		List<Group> groupList = groupDao.getAllGroup();
		for (Group group : groupList) {
			List<GroupProperties> groupPropList = groupPropertiesDao
					.getGroupPropListByGroupIdAndDeleted(group.getParentId(), false);
			group.setPropList(groupPropList);
			groupMap.put(group.getId(), group);
		}
		return groupMap;
	}

	@Override
	public Group getGroupById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Group group = new Group();
		try {
			group = groupDao.getGroupById(id);
			List<GroupProperties> groupPropList = groupPropertiesDao
					.getGroupPropListByGroupIdAndDeleted(id, false);
			group.setPropList(groupPropList);
			List<Device> childDevcieList = deviceManagementService
					.getDeviceListByGroupsId(group.getId());
			group.setChildDevcieList(childDevcieList);
			group = setRoleByGroup(group);//區域被哪些角色所管理
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return group;
	}
	
	@Override
	public List<Group> getGroupByName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<Group> groupList = groupDao.getGroupByNameAndDeleted(name, false);
		if (groupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		Map<Integer, Group> groupAllMap = getAllGroupsByKeyIsId();
		groupList.forEach(group -> {
			if (group.getParentId() != 0 ) {
				group.setParentGroupName(groupAllMap.get(group.getParentId()).getName());
 			}
			group.setSubGroupList(groupDao.getGroupByParentIdAndDeleted(group.getId(), false));
			group.setPropList(groupPropertiesDao.getGroupPropListByGroupIdAndDeleted(group.getId(), false));
		});
		return groupList;
	}
	
	private Group setRoleByGroup(Group group) {
		List<Role> roleList = new ArrayList<Role>();
		List<RoleGroup> roleGroupList = menuService.getRoleGroupByGroupId(group.getId());
		roleGroupList.forEach(roleGroup -> {
			Role role = roleDao.getRoleById(roleGroup.getRoleId());
			role = getGroupByRoleId(role);
			roleList.add(role);
		});
		group.setRoleList(roleList);
		return group;
	}
	
	private Role getGroupByRoleId(Role role) {
		List<Group> groupList = new ArrayList<Group>();
		List<RoleGroup> roleGroupList;
		try {
			roleGroupList = roleGroupDao.getRoleGroupByRoleId(role.getId());
			roleGroupList.forEach(roleGroup -> {
				Group group = groupDao.getGroupById(roleGroup.getGroupId());
				//僅抓 properties.
//				List<GroupProperties> groupPropList = groupPropertiesDao
//						.getGroupPropListByGroupIdAndDeleted(group.getId(), false);
//				group.setPropList(groupPropList);
				groupList.add(group);
			});
		} catch (EmptyResultDataAccessException e) {
			roleGroupList = new ArrayList<RoleGroup>();
		} 
		role.setGroupList(groupList);
		return role;
	}

	@Override
	public List<Group> getGroupByCreator(int creator) {
		if (creator == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<Group> groupList = groupDao.getGroupByCreatorAndDeleted(creator, false);
		if (groupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		Map<Integer, Group> groupAllMap = getAllGroupsByKeyParentId();
		groupList.forEach(group -> {
			if (group.getParentId() != 0 ) {
				group.setParentGroupName(groupAllMap.get(group.getParentId()).getName());
 			}
			group.setSubGroupList(groupDao.getGroupByParentIdAndDeleted(group.getId(), false));
			group.setPropList(groupPropertiesDao.getGroupPropListByGroupIdAndDeleted(group.getId(), false));
		});
		return groupList;
	}

	@Override
	public List<Group> getGroupByParentId(int parentId) {
		List<Group> groupList = groupDao.getGroupByParentIdAndDeleted(parentId, false);
		for (Group group : groupList) {
			List<GroupProperties> groupPropList = groupPropertiesDao
					.getGroupPropListByGroupIdAndDeleted(group.getId(), false);
			group.setPropList(groupPropList);
		}
		if (groupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return groupList;
	}

	@Override
	public int insertGroup(Group group) {
		int lastId = groupDao.insertGroup(group);
		if (lastId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (group.getGroupPropertiesList().size() != 0) {
			group.getGroupPropertiesList().forEach(groupProperties -> {
				groupProperties.setGroupId(lastId);
			});
			batchInsertGroupPropList(group.getGroupPropertiesList());
		}
		return lastId;
	}

	@Override
	public int updateGroup(Group group) {
		if (group.getId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = groupDao.updateGroup(group);
		if (group.getGroupPropertiesList().size() != 0) {
			batchUpdateGroupPropList(group.getGroupPropertiesList());
		}
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int deleteGroupById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (menuService.getRoleGroupByGroupId(id).size() != 0) {
			throw new ConnesiaException(ConnesiaEnum.GROUP_CANNOT_DELETE_BY_ROLE);
		}
		if (groupDao.getGroupByParentIdAndDeleted(id, false).size() != 0) {
			throw new ConnesiaException(ConnesiaEnum.GROUP_CANNOT_DELETE_BY_SUB_GROUP);
		}
		int total = groupDao.updateGroupDeletedById(id, true);
		batchDeleteGroupPropList(getGroupPropListByGroupId(id));
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}
	
	/*
	 * 查詢所有群組列表
	 */
	@Override
	public List<DeviceGroup> getAllDeviceGroup() {
		List<DeviceGroup> deviceGroupList = deviceGroupDao.getAllDeviceGroup();
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}
	
	@Override
	public List<DeviceGroup> getDeviceGroupByDeviceId(int deviceId) {
		if (deviceId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<DeviceGroup> deviceGroupList = deviceGroupDao
				.getDeviceGroupByDeviceId(deviceId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	public List<DeviceGroup> getDeviceGroupByGroupsId(int groupId) {
		if (groupId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<DeviceGroup> deviceGroupList = deviceGroupDao
				.getDeviceGroupByGroupId(groupId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	public int insertDeviceGroup(DeviceGroup deviceGroup) {
		if (deviceGroup.getDeviceId() == 0 || deviceGroup.getGroupId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = 0;
		try {
			total = deviceGroupDao.insertDeviceGroup(deviceGroup);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchInsertDeviceGroup(List<DeviceGroup> deviceGroupList) {
		int total = 0;
		try {
			total = deviceGroupDao.insertDeviceGroup(deviceGroupList);
			if (deviceGroupList.size() != total) {
				throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
			}
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchDeleteDeviceGroupList(List<DeviceGroup> deviceGroupList) {
		int total = deviceGroupDao.deleteDeviceGroupList(deviceGroupList);
		if (deviceGroupList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<GroupProperties> getAllGroupPropList() {
		List<GroupProperties> groupPropertiesList = groupPropertiesDao
				.getAllGroupPropList();
		if (groupPropertiesList.size() <= 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return groupPropertiesList;
	}

	@Override
	public List<GroupProperties> getGroupPropListByGroupId(int groupId) {
		if (groupId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<GroupProperties> groupPropertiesList = groupPropertiesDao
				.getGroupPropListByGroupIdAndDeleted(groupId, false);
		if (groupPropertiesList.size() <= 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return groupPropertiesList;
	}

	@Override
	public GroupProperties getGroupPropById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		GroupProperties groupProperties = new GroupProperties();
		try {
			groupProperties = groupPropertiesDao.getGroupPropById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return groupProperties;
	}

	@Override
	@Transactional
	public int batchInsertGroupPropList(
			List<GroupProperties> groupsPropertiesList) {
		int total = groupPropertiesDao
				.insertGroupPropList(groupsPropertiesList);
		if (groupsPropertiesList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchUpdateGroupPropList(
			List<GroupProperties> groupPropertiesList) {
		int total = groupPropertiesDao.updateGroupPropList(groupPropertiesList);
		if (groupPropertiesList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int deleteGroupPropById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int rs = groupPropertiesDao.updateGroupPropDeletedById(id, true);
		if (rs <= 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return rs;
	}

	@Override
	@Transactional
	public int batchDeleteGroupPropList(
			List<GroupProperties> groupsPropertiesList) {
		int total = groupPropertiesDao.updateGroupPropListDeleted(
				groupsPropertiesList, true);
		if (groupsPropertiesList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<Group> getGroupListByDeviceId(int deviceId) {
		List<DeviceGroup> deviceGroupList = deviceGroupDao
				.getDeviceGroupByDeviceId(deviceId);
		int groupId;
		Group group = new Group();
		List<Group> groupList = new ArrayList<Group>();
		for (DeviceGroup deviceGroup : deviceGroupList) {
			groupId = deviceGroup.getGroupId();
			group = getGroupById(groupId);
			groupList.add(group);
		}
		return groupList;
	}

}
