package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.RoleGroup;

public interface RoleGroupDao {
	
	public int insertRoleGroup(RoleGroup roleGroup);
	
	public int insertRoleGroupList(List<RoleGroup> roleGroupList);
	
	public int deleteRoleGroupList(List<RoleGroup> roleGroupList);
	
	public List<RoleGroup> getRoleGroupByRoleId(int roleId);
	
	public List<RoleGroup> getRoleGroupByGroupId(int groupId);
}
