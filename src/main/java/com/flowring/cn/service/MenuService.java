package com.flowring.cn.service;

import java.util.List;

import com.flowring.cn.entity.Functions;
import com.flowring.cn.entity.Menu;
import com.flowring.cn.entity.RoleFunction;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.cn.entity.RoleMenu;

public interface MenuService {
	/* Menu */
	public List<Menu> getAllMenu();

	public Menu getMenuById(int id);

	public List<Menu> getMenuByParentId(int parentId);

	public int insertMenu(Menu menu);

	public int updateMenu(Menu menu);

	public int deleteMenuById(int id);

	/* RoleMenu */
	public List<RoleMenu> getRoleMenuByRoleId(int roleId);

	public List<RoleMenu> getRoleMenuByMenuId(int menuId);

	public int insertRoleMenu(RoleMenu roleMenu);

	public int batchInsertRoleMenu(List<RoleMenu> roleMenuList);

	public int deleteRoleMenuByRoleId(int roleId);

	public int deleteRoleMenuByMenuId(int menuId);
	
	/* functions */
	public int insertFunctions(Functions functions);
	
	public int updateFunctions(Functions functions);
	
	public boolean deleteFunctionsById(int id);
	
	public Functions getFunctionsById(int id);
	
	public List<Functions> getFunctionsByMenuId(int menuId);
	
	/* role_functions */
	public int insertRoleFunction(RoleFunction roleFunction);
	
	public int batchInsertRoleFunctionList(List<RoleFunction> roleFunctionList);
	
	public int batchDeleteRoleFunctionList(List<RoleFunction> roleFunctionList);
	
	public List<RoleFunction> getRoleFunctionByRoleId(int roleId);
	
	/* role_group */
	public int insertRoleGroup(RoleGroup roleGroup);
	
	public int batchInsertRoleGroupList(List<RoleGroup> roleGroupList);
	
	public int batchDeleteRoleGroupList(List<RoleGroup> roleGroupList);
	
	public List<RoleGroup> getRoleGroupByRoleId(int roleId);
	
	public List<RoleGroup> getRoleGroupByGroupId(int groupId);
	
}
