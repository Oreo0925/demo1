package com.flowring.cn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.RoleMenuDao;
import com.flowring.cn.dao.FunctionsDao;
import com.flowring.cn.dao.GroupDao;
import com.flowring.cn.dao.MenuDao;
import com.flowring.cn.dao.RoleDao;
import com.flowring.cn.dao.RoleFunctionDao;
import com.flowring.cn.dao.RoleGroupDao;
import com.flowring.cn.entity.RoleMenu;
import com.flowring.cn.entity.Functions;
import com.flowring.cn.entity.Menu;
import com.flowring.cn.entity.RoleFunction;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private MenuDao menuDao;

	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private FunctionsDao functionsDao;
	
	@Autowired
	private RoleFunctionDao roleFunctionDao;
	
	@Autowired
	private RoleGroupDao roleGroupDao;
	
	@Autowired
	private GroupDao groupDao;
	

	@Override
	@Transactional
	public List<Menu> getAllMenu() {
		List<Menu> menuList = menuDao.getAllMenu();
		if (menuList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return menuList;
	}

	@Override
	@Transactional
	public Menu getMenuById(int id) {
		if(id == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Menu menu = new Menu();
		try {
			menu = menuDao.getMenuById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return menu;
	}

	@Override
	@Transactional
	public List<Menu> getMenuByParentId(int parentId) {
		List<Menu> menuList = menuDao.getMenuByParentId(parentId);
		if (menuList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return menuList;
	}

	@Override
	@Transactional
	public int insertMenu(Menu menu) {
		int lastId = menuDao.insertMenu(menu);
		if (lastId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return lastId;
	}

	@Override
	@Transactional
	public int updateMenu(Menu menu) {
		if(menu.getId() == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = menuDao.updateMenu(menu);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int deleteMenuById(int id) {
		if(id == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = menuDao.deleteMenuById(id);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public List<RoleMenu> getRoleMenuByRoleId(int roleId) {
		if(roleId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<RoleMenu> deviceGroupList = roleMenuDao.getRoleMenuByRoleId(roleId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	@Transactional
	public List<RoleMenu> getRoleMenuByMenuId(int menuId) {
		if(menuId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<RoleMenu> deviceGroupList = roleMenuDao.getRoleMenuByMenuId(menuId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	@Transactional
	public int insertRoleMenu(RoleMenu roleMenu) {
		if(roleMenu.getMenuId() == 0 || roleMenu.getRoleId() == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = 0;
		try {
			total = roleMenuDao.insertRoleMenu(roleMenu);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchInsertRoleMenu(List<RoleMenu> roleMenuList) {
		int total = 0;
		try {
			total = roleMenuDao.insertRoleMenu(roleMenuList);
			
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (roleMenuList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int deleteRoleMenuByRoleId(int roleId) {
		if(roleId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = roleMenuDao.deleteRoleMenuByRoleId(roleId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return total;
	}

	@Override
	public int deleteRoleMenuByMenuId(int menuId) {
		if(menuId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = roleMenuDao.deleteRoleMenuByMenuId(menuId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return total;
	}

	@Override
	public int insertFunctions(Functions functions) {
		if (functions == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getMenuById(functions.getMenuId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		int count = functionsDao.insertFunctions(functions);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public int updateFunctions(Functions functions) {
		if (functions == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getFunctionsById(functions.getId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		if (getMenuById(functions.getMenuId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		int count = functionsDao.updateFunctions(functions);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public boolean deleteFunctionsById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		boolean result = functionsDao.deleteFunctionsById(id);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public Functions getFunctionsById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Functions functions = new Functions();
		try {
			functions = functionsDao.getFunctionsById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return functions;
	}

	@Override
	public List<Functions> getFunctionsByMenuId(int menuId) {
		if (menuId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		} 
		List<Functions> functionsList = new ArrayList<Functions>();
		try {
			functionsList = functionsDao.getFunctionsByMenuId(menuId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return functionsList;
	}

	@Override
	public int insertRoleFunction(RoleFunction roleFunction) {
		if (roleFunction.getRoleId() == 0 || roleFunction.getFunctionId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		} 
		if (roleDao.getRoleById((roleFunction.getRoleId())) == null) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		if (getFunctionsById(roleFunction.getFunctionId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.FUNCTIONS_NOT_EXIST);
		}
		boolean isExists = isRoleFunctionExist(roleFunction);
		if (!isExists) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_AUTHORITY_EXIST);
		}
		int count = roleFunctionDao.insertRoleFunction(roleFunction);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	private boolean isRoleFunctionExist(RoleFunction roleFunction) {
		List<RoleFunction> roleFunctionList = getRoleFunctionByRoleId(roleFunction.getRoleId());
		for (int i = 0; i < roleFunctionList.size(); i++) {
			if (roleFunctionList.get(i).getFunctionId() == roleFunction.getFunctionId()) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional
	public int batchInsertRoleFunctionList(List<RoleFunction> roleFunctionList) {
		if (roleFunctionList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		}
		int total = 0;
		try {
			total = roleFunctionDao.insertRoleFunctionList(roleFunctionList);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (roleFunctionList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchDeleteRoleFunctionList(List<RoleFunction> roleFunctionList) {
		if (roleFunctionList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		}
		int total = roleFunctionDao.deleteRoleFunctionList(roleFunctionList);
		if (roleFunctionList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<RoleFunction> getRoleFunctionByRoleId(int roleId) {
		if (roleId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		} 
		List<RoleFunction> roleFunctionsList = new ArrayList<>();
		try {
			roleFunctionsList = roleFunctionDao.getRoleFunctionByRoleId(roleId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		} 
		return roleFunctionsList;
	}

	@Override
	public int insertRoleGroup(RoleGroup roleGroup) {
		if (roleGroup.getRoleId() == 0 || roleGroup.getGroupId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		} 
		if (roleDao.getRoleById((roleGroup.getRoleId())) == null) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		if (groupDao.getGroupById(roleGroup.getGroupId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.GROUP_NOT_EXIST);
		}
		boolean isExists = istRoleGroupExist(roleGroup);
		if (!isExists) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_AUTHORITY_EXIST);
		}
		int count = roleGroupDao.insertRoleGroup(roleGroup);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	private boolean istRoleGroupExist(RoleGroup roleGroup) {
		List<RoleGroup> roleGroupList = getRoleGroupByRoleId(roleGroup.getRoleId());
		for (int i = 0; i < roleGroupList.size(); i++) {
			if (roleGroupList.get(i).getGroupId() == roleGroup.getGroupId()) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional
	public int batchInsertRoleGroupList(List<RoleGroup> roleGroupList) {
		if (roleGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		}
		int total = 0;
		try {
			total = roleGroupDao.insertRoleGroupList(roleGroupList);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (roleGroupList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchDeleteRoleGroupList(List<RoleGroup> roleGroupList) {
		if (roleGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		}
		int total = roleGroupDao.deleteRoleGroupList(roleGroupList);
		if (roleGroupList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<RoleGroup> getRoleGroupByRoleId(int roleId) {
		if (roleId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		} 
		List<RoleGroup> roleGroupList = new ArrayList<>();
		try {
			roleGroupList = roleGroupDao.getRoleGroupByRoleId(roleId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		} 
		return roleGroupList;
	}

	@Override
	public List<RoleGroup> getRoleGroupByGroupId(int groupId) {
		if (groupId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		} 
		List<RoleGroup> roleGroupList = new ArrayList<>();
		try {
			roleGroupList = roleGroupDao.getRoleGroupByGroupId(groupId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		} 
		return roleGroupList;
	}
	
}
