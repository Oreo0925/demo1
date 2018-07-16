package com.flowring.cn.service;

import java.util.List;

import com.flowring.cn.entity.Payload;
import com.flowring.cn.entity.Role;
import com.flowring.cn.entity.Rule;
import com.flowring.cn.entity.RuleDevice;
import com.flowring.cn.entity.RuleGroup;
import com.flowring.cn.entity.RuleLog;
import com.flowring.cn.entity.ToDoList;

public interface RuleService {
	/* Rule */
	public int insertRule(Rule rule);

	public int updateRule(Rule rule);

	public boolean deleteRuleById(int id);

	public Rule getRuleById(int id);

	public List<Rule> getRuleByDeviceTypeId(int deviceTypeId);
	
	/* RuleLog */
	public int insertRuleLog(RuleLog ruleLog);

	public int updateRuleLog(RuleLog ruleLog);
	
	public boolean deleteRuleLogById(int id);
	
	public RuleLog getRuleLogById(int id);
	
	public List<RuleLog> getRuleLogByDeviceId(int deviceId);
	
	/* ToDoList */
	public int insertToDoList(ToDoList toDoList);
	
	public ToDoList updateToDoList(ToDoList toDoList);
	
	public boolean deleteToDoList(int id);
	
	public ToDoList getToDoListById(int id);
	
	public List<ToDoList> getToDoListByRuleId(int ruleId);
	
	/* Payload */
	public int insertPayload(Payload payload);

	public int updatePayload(Payload payload);
	
	public boolean deletePayloadById(int id);
	
	public Payload getPayloadById(int id);
	
	public List<Payload> getPayloadByDeviceTypeId(int deviceTypeId);

	/* RuleDevice */
	public List<RuleDevice> getRuleDeviceByRuleId(int ruleId);

	public List<RuleDevice> getRuleDeviceByDeviceId(int deviceId);

	public int insertRuleDevice(RuleDevice ruleDevice);

	public int batchInsertRuleDevice(List<RuleDevice> ruleDeviceList);

	public int deleteRuleDeviceByRuleId(int ruleId);

	public int deleteRuleDeviceByDeviceId(int deviceId);

	/* RuleGroup */
	public List<RuleGroup> getRuleGroupByRuleId(int ruleId);

	public List<RuleGroup> getRuleGroupByGroupId(int groupId);

	public int insertRuleGroup(RuleGroup ruleGroup);

	public int batchInsertRuleGroup(List<RuleGroup> ruleGroupList);

	public int deleteRuleGroupByRuleId(int ruleId);

	public int deleteRuleGroupByGroupId(int groupId);
}
