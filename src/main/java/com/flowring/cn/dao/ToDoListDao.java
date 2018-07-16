package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.ToDoList;

public interface ToDoListDao {
	public int insertToDoList(ToDoList toDoList);

	public ToDoList updateToDoList(ToDoList toDoList);
	
	public boolean deleteToDoList(int id);
	
	public ToDoList getToDoListById(int id);
	
	public List<ToDoList> getToDoListByRuleId(int ruleId);
}
