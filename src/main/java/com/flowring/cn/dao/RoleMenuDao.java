package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.RoleMenu;

public interface RoleMenuDao {
    public List<RoleMenu> getRoleMenuByRoleId(int roleId);
    public List<RoleMenu> getRoleMenuByMenuId(int menuId);
    public int insertRoleMenu(RoleMenu roleMenu);
    public int insertRoleMenu(List<RoleMenu> roleMenuList);
    public int deleteRoleMenuByRoleId(int roleId);
    public int deleteRoleMenuByMenuId(int menuId);
}
