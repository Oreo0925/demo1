package com.flowring.cn.dao;

import java.util.List;
import java.util.Map;

import com.flowring.cn.entity.MemberRole;
import com.flowring.cn.entity.Role;

public interface RoleDao {
	
	public int insertRole(Role role);
	
	public int updateRole(Role role);
	
	public Role getRoleById(int id);
	
	public List<Role> getRoleByName(String name);
	
	public List<Role> getRoleByCreator(int creator);
	
	public Map<Integer, Role> getRoleByMemberId(int memberId);
	
	public boolean deleteRole(int roleId);
	
}
