package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.RuleGroup;

public interface RuleGroupDao {
	public List<RuleGroup> getRuleGroupByRuleId(int ruleId);
    public List<RuleGroup> getRuleGroupByGroupId(int groupId);
    public int insertRuleGroup(RuleGroup ruleGroup);
    public int insertRuleGroup(List<RuleGroup> ruleGroupList);
    public int deleteRuleGroupByRuleId(int ruleId);
    public int deleteRuleGroupByGroupId(int groupId);
}
