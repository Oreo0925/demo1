package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.RuleDevice;

public interface RuleDeviceDao {
	public List<RuleDevice> getRuleDeviceByRuleId(int ruleId);
    public List<RuleDevice> getRuleDeviceByDeviceId(int deviceId);
    public int insertRuleDevice(RuleDevice ruleDevice);
    public int insertRuleDevice(List<RuleDevice> ruleDeviceList);
    public int deleteRuleDeviceByRuleId(int ruleId);
    public int deleteRuleDeviceByDeviceId(int deviceId);
}
