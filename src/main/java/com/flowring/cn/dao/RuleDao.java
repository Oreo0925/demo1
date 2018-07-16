package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Rule;

public interface RuleDao {
	
	public int insertRule(Rule rule);

	public int updateRule(Rule rule);
	
	public boolean deleteRuleById(int id);
	
	public Rule getRuleById(int id);
	
	public List<Rule> getRuleByDeviceTypeId(int deviceTypeId);
	
}
	
	
