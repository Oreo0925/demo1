package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.RuleLog;

public interface RuleLogDao {
	
	public int insertRuleLog(RuleLog ruleLog);

	public int updateRuleLog(RuleLog ruleLog);
	
	public boolean deleteRuleLogById(int id);
	
	public RuleLog getRuleLogById(int id);
	
	public List<RuleLog> getRuleLogByDeviceId(int deviceId);
}	
