package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Functions;

public interface FunctionsDao {
	
	public int insertFunctions(Functions functions);
	
	public int updateFunctions(Functions functions);
	
	public boolean deleteFunctionsById(int id);
	
	public Functions getFunctionsById(int id);
	
	public List<Functions> getFunctionsByMenuId(int menuId);
}
