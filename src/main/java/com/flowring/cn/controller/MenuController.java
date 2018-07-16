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

import com.flowring.cn.entity.Functions;
import com.flowring.cn.entity.Menu;
import com.flowring.cn.entity.RoleFunction;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.cn.entity.RoleMenu;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.MenuService;

@RestController
@RequestMapping("/")
public class MenuController {
	@Autowired
	private MenuService menuManagementService;

	/*
	 * 查詢 All Menu
	 */
	@GetMapping("/menus")
	public SingleResponseObject<List<Menu>> getAllGroup() {
		SingleResponseObject<List<Menu>> ero = new SingleResponseObject<List<Menu>>();
		try {
			List<Menu> groupList = menuManagementService.getAllMenu();
			ero.setData(groupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢 Menu By id
	 */
	@GetMapping("/menu/{id}")
	public SingleResponseObject<Menu> getMenuById(@PathVariable("id") Integer id) {
		SingleResponseObject<Menu> ero = new SingleResponseObject<Menu>();
		try {
			Menu menu = menuManagementService.getMenuById(id);
			ero.setData(menu);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢 Menus By parentId
	 */
	@GetMapping("/menus/parentId/{parentId}")
	public SingleResponseObject<List<Menu>> getMenuByParentId(
			@PathVariable("parentId") int parentId) {
		SingleResponseObject<List<Menu>> ero = new SingleResponseObject<List<Menu>>();
		try {
			List<Menu> groupList = menuManagementService
					.getMenuByParentId(parentId);
			ero.setData(groupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增群組
	 */
	@PostMapping("/menu")
	public SingleResponseObject<Menu> insertMenu(@RequestBody Menu menu) {
		SingleResponseObject<Menu> ero = new SingleResponseObject<Menu>();
		try {
			menuManagementService.insertMenu(menu);
			ero.setData(menu);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改群組 ById
	 */
	@PutMapping("/menu")
	public SingleResponseObject<Menu> updateMenu(@RequestBody Menu menu) {
		SingleResponseObject<Menu> ero = new SingleResponseObject<Menu>();
		try {
			menuManagementService.updateMenu(menu);
			ero.setData(menu);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 刪除群組 ById
	 */
	@DeleteMapping("/menu/{id}")
	public SingleResponseObject<Integer> deleteMenuById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int rs = menuManagementService.deleteMenuById(id);
			ero.setTotal(rs);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢 RoleMenuList By roleId
	 */
	@GetMapping("/menus/role/{roleId}")
	public SingleResponseObject<List<RoleMenu>> getRoleMenuByRoleId(
			@PathVariable("roleId") Integer roleId) {
		SingleResponseObject<List<RoleMenu>> ero = new SingleResponseObject<List<RoleMenu>>();
		try {
			List<RoleMenu> roleMenuList = menuManagementService
					.getRoleMenuByRoleId(roleId);
			ero.setData(roleMenuList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢 RoleMenuList ByMenuId
	 */
	@GetMapping("/menu/roles/{menuId}")
	public SingleResponseObject<List<RoleMenu>> getRoleMenuByMenuId(
			@PathVariable("menuId") Integer menuId) {
		SingleResponseObject<List<RoleMenu>> ero = new SingleResponseObject<List<RoleMenu>>();
		try {
			List<RoleMenu> roleMenuList = menuManagementService
					.getRoleMenuByMenuId(menuId);
			ero.setData(roleMenuList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增menu role
	 */
	@PostMapping("/menus/roles")
	public SingleResponseObject<RoleMenu> insertRoleMenu(
			@RequestBody RoleMenu roleMenu) {
		SingleResponseObject<RoleMenu> ero = new SingleResponseObject<RoleMenu>();
		try {
			menuManagementService.insertRoleMenu(roleMenu);
			ero.setData(roleMenu);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改menu role
	 */
	@PostMapping("/menu/role")
	public SingleResponseObject<Integer> insertRoleMenuList(
			@RequestBody List<RoleMenu> roleMenuList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = menuManagementService.batchInsertRoleMenu(roleMenuList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 刪除menu role By MenuId
	 */
	@DeleteMapping("/menu/roles/{menuId}")
	public SingleResponseObject<Integer> deleteRoleMenu(
			@PathVariable("menuId") Integer menuId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = menuManagementService.deleteRoleMenuByMenuId(menuId);
			ero.setData(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除menu role By RoleId
	 */
	@DeleteMapping("/menus/role/{roleId}")
	public SingleResponseObject<Integer> deleteRoleMenuById(
			@PathVariable("roleId") Integer roleId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = menuManagementService.deleteRoleMenuByRoleId(roleId);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	@PostMapping(value = "functions")
	public SingleResponseObject<Functions> insertFunctions(
			@RequestBody Functions functions) throws Exception {
		SingleResponseObject<Functions> ero = new SingleResponseObject<Functions>();
		try {
			menuManagementService.insertFunctions(functions);
			ero.setData(functions);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "functions")
	public SingleResponseObject<Functions> updateFunctions(
			@RequestBody Functions functions) throws Exception {
		SingleResponseObject<Functions> ero = new SingleResponseObject<Functions>();
		try {
			menuManagementService.updateFunctions(functions);
			ero.setData(functions);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "functions/{id}")
	public SingleResponseObject<Functions> deleteFunctionsById(
			@PathVariable("id") int id) {
		SingleResponseObject<Functions> ero = new SingleResponseObject<Functions>();
		try {
			boolean result = menuManagementService.deleteFunctionsById(id);
			ero.setTotal(1);
			ero.setSuccess(result);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "functions/{id}")
	public SingleResponseObject<Functions> getFunctionsById(
			@PathVariable("id") int id) {
		SingleResponseObject<Functions> ero = new SingleResponseObject<Functions>();
		try {
			Functions functions = menuManagementService.getFunctionsById(id);
			ero.setData(functions);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "functions/menu/{menuId}")
	public SingleResponseObject<List<Functions>> getFunctionsByMenuId(
			@PathVariable("menuId") int menuId) {
		SingleResponseObject<List<Functions>> ero = new SingleResponseObject<List<Functions>>();
		try {
			List<Functions> functionsList = menuManagementService
					.getFunctionsByMenuId(menuId);
			ero.setData(functionsList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "role/function")
	public SingleResponseObject<RoleFunction> insertRoleFunction(
			@RequestBody RoleFunction roleFunction) throws Exception {
		SingleResponseObject<RoleFunction> ero = new SingleResponseObject<RoleFunction>();
		try {
			menuManagementService.insertRoleFunction(roleFunction);
			ero.setData(roleFunction);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "role/multiFunction")
	public SingleResponseObject<List<RoleFunction>> batchInsertRoleFunctionList(
			@RequestBody List<RoleFunction> roleFunctionList) throws Exception {
		SingleResponseObject<List<RoleFunction>> ero = new SingleResponseObject<List<RoleFunction>>();
		try {
			menuManagementService.batchInsertRoleFunctionList(roleFunctionList);
			ero.setData(roleFunctionList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "role/multiFunction")
	public SingleResponseObject<List<RoleFunction>> batchDeleteRoleFunctionList(
			@RequestBody List<RoleFunction> roleFunctionList) {
		SingleResponseObject<List<RoleFunction>> ero = new SingleResponseObject<List<RoleFunction>>();
		try {
			int total = menuManagementService
					.batchDeleteRoleFunctionList(roleFunctionList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/{roleId}/function")
	public SingleResponseObject<List<RoleFunction>> getRoleFunctionByRoleId(
			@PathVariable("roleId") int roleId) {
		SingleResponseObject<List<RoleFunction>> ero = new SingleResponseObject<List<RoleFunction>>();
		try {
			List<RoleFunction> roleFunctionsList = menuManagementService
					.getRoleFunctionByRoleId(roleId);
			ero.setData(roleFunctionsList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "role/group")
	public SingleResponseObject<RoleGroup> insertRoleGroup(
			@RequestBody RoleGroup roleGroup) throws Exception {
		SingleResponseObject<RoleGroup> ero = new SingleResponseObject<RoleGroup>();
		try {
			menuManagementService.insertRoleGroup(roleGroup);
			ero.setData(roleGroup);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "role/groups")
	public SingleResponseObject<List<RoleGroup>> batchInsertRoleGroupList(
			@RequestBody List<RoleGroup> roleGroupList) throws Exception {
		SingleResponseObject<List<RoleGroup>> ero = new SingleResponseObject<List<RoleGroup>>();
		try {
			menuManagementService.batchInsertRoleGroupList(roleGroupList);
			ero.setData(roleGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "role/groups")
	public SingleResponseObject<List<RoleGroup>> batchDeleteRoleGroupList(
			@RequestBody List<RoleGroup> roleGroupList) {
		SingleResponseObject<List<RoleGroup>> ero = new SingleResponseObject<List<RoleGroup>>();
		try {
			int total = menuManagementService
					.batchDeleteRoleGroupList(roleGroupList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/{roleId}/group")
	public SingleResponseObject<List<RoleGroup>> getRoleGroupByRoleId(
			@PathVariable("roleId") int roleId) {
		SingleResponseObject<List<RoleGroup>> ero = new SingleResponseObject<List<RoleGroup>>();
		try {
			List<RoleGroup> roleGroupList = menuManagementService
					.getRoleGroupByRoleId(roleId);
			ero.setData(roleGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/group/{groupId}")
	public SingleResponseObject<List<RoleGroup>> getRoleGroupByGroupId(
			@PathVariable("groupId") int groupId) {
		SingleResponseObject<List<RoleGroup>> ero = new SingleResponseObject<List<RoleGroup>>();
		try {
			List<RoleGroup> roleGroupList = menuManagementService
					.getRoleGroupByGroupId(groupId);
			ero.setData(roleGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

}
