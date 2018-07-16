package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.RoleFunction;

public interface RoleFunctionDao {
	
	public int insertRoleFunction(RoleFunction roleFunction);
	
	public int insertRoleFunctionList(List<RoleFunction> roleFunctionList);
	
	public int deleteRoleFunctionList(List<RoleFunction> roleFunctionList);
	
	public List<RoleFunction> getRoleFunctionByRoleId(int roleId);
	
}
