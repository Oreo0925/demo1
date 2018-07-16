package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Menu;

public interface MenuDao {
	public List<Menu> getAllMenu();
    public Menu getMenuById(int id);
    public List<Menu> getMenuByParentId(int parentId);
    public int insertMenu(Menu menu);
    public int updateMenu(Menu menu);
    public int deleteMenuById(int id);
}
