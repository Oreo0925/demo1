package com.flowring.cn.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.flowring.cn.dao.PayloadDao;
import com.flowring.cn.dao.RuleDao;
import com.flowring.cn.dao.RuleDeviceDao;
import com.flowring.cn.dao.RuleGroupDao;
import com.flowring.cn.dao.RuleLogDao;
import com.flowring.cn.dao.ToDoListDao;
import com.flowring.cn.entity.Payload;
import com.flowring.cn.entity.Rule;
import com.flowring.cn.entity.RuleDevice;
import com.flowring.cn.entity.RuleGroup;
import com.flowring.cn.entity.RuleLog;
import com.flowring.cn.entity.ToDoList;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.RuleService;

@Service
public class RuleServiceImpl implements RuleService {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private RuleLogDao ruleLogDao;
	
	@Autowired
	private ToDoListDao toDoListDao;
	
	@Autowired
	private PayloadDao payloadDao;
	
	@Autowired
	private RuleDeviceDao ruleDeviceDao;
	
	@Autowired
	private RuleGroupDao ruleGroupDao;
	
	/**
	 * ToDo:Add clause that judge device type exist or not.
	 */
	@Override
	public int insertRule(Rule rule) {
		if (rule == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (rule.getDeviceTypeId() == 0 || rule.getActionType() == 0) {
			throw new ConnesiaException(ConnesiaEnum.RULE_NOT_EMPTY);
		}
		int count = ruleDao.insertRule(rule);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public int updateRule(Rule rule) {
		if (rule == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (rule.getDeviceTypeId() == 0 || rule.getActionType() == 0) {
			throw new ConnesiaException(ConnesiaEnum.RULE_NOT_EMPTY);
		}
		int count = ruleDao.updateRule(rule);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public boolean deleteRuleById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		boolean result = ruleDao.deleteRuleById(id);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public Rule getRuleById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Rule rule = new Rule();
		try {
			rule = ruleDao.getRuleById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return rule;
	}

	@Override
	public List<Rule> getRuleByDeviceTypeId(int deviceTypeId) {
		if (deviceTypeId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		} 
		List<Rule> ruleList = new ArrayList<Rule>();
		try {
			ruleList = ruleDao.getRuleByDeviceTypeId(deviceTypeId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return ruleList;
	}
	
	@Override
	public int insertRuleLog(RuleLog ruleLog) {
		if (ruleLog == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (ruleLog.getMemberId() == 0 || ruleLog.getDeviceId() == 0 || ruleLog.getGroupsId() == 0
				|| ruleLog.getRuleId() == 0 || ruleLog.getRuleActionId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		ruleLog.setTriggerTime(sdf.format(new Date()));
		int count = ruleLogDao.insertRuleLog(ruleLog);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public int updateRuleLog(RuleLog ruleLog) {
		if (ruleLog == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (ruleLog.getMemberId() == 0 || ruleLog.getDeviceId() == 0 || ruleLog.getGroupsId() == 0
				|| ruleLog.getRuleId() == 0 || ruleLog.getRuleActionId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		ruleLog.setTriggerTime(sdf.format(new Date()));
		int count = ruleLogDao.updateRuleLog(ruleLog);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public boolean deleteRuleLogById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		boolean result = ruleLogDao.deleteRuleLogById(id);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public RuleLog getRuleLogById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		RuleLog ruleLog = new RuleLog();
		try {
			ruleLog = ruleLogDao.getRuleLogById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return ruleLog;
	}

	@Override
	public List<RuleLog> getRuleLogByDeviceId(int deviceId) {
		if (deviceId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		} 
		List<RuleLog> ruleLogList = new ArrayList<RuleLog>();
		try {
			ruleLogList = ruleLogDao.getRuleLogByDeviceId(deviceId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return ruleLogList;
	}
	
	@Override
	public int insertToDoList(ToDoList toDoList) {
		if (toDoList == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (toDoList.getRuleId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.RULE_NOT_EMPTY);
		}
		int count = toDoListDao.insertToDoList(toDoList);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public ToDoList updateToDoList(ToDoList toDoList) {
		if (toDoList == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (toDoList.getRuleId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.RULE_NOT_EMPTY);
		}
		if (getToDoListById(toDoList.getId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		toDoList.setFinishTime(sdf.format(new Date()));
		ToDoList newToDoList = toDoListDao.updateToDoList(toDoList);
		if (newToDoList == null) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return newToDoList;
	}

	@Override
	public boolean deleteToDoList(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		}
		boolean result = toDoListDao.deleteToDoList(id);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public ToDoList getToDoListById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		ToDoList toDoList = new ToDoList();
		try {
			toDoList = toDoListDao.getToDoListById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return toDoList;
	}

	@Override
	public List<ToDoList> getToDoListByRuleId(int ruleId) {
		if (ruleId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<ToDoList> toDoLists = new ArrayList<ToDoList>();
		try {
			toDoLists = toDoListDao.getToDoListByRuleId(ruleId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return toDoLists;
	}

	@Override
	public List<RuleDevice> getRuleDeviceByRuleId(int ruleId) {
		if(ruleId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<RuleDevice> deviceGroupList = ruleDeviceDao.getRuleDeviceByRuleId(ruleId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	public List<RuleDevice> getRuleDeviceByDeviceId(int deviceId) {
		if(deviceId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<RuleDevice> deviceGroupList = ruleDeviceDao.getRuleDeviceByDeviceId(deviceId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	public int insertRuleDevice(RuleDevice ruleDevice) {
		if(ruleDevice.getRuleId() == 0 || ruleDevice.getDeviceId() == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = 0;
		try {
			total = ruleDeviceDao.insertRuleDevice(ruleDevice);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int batchInsertRuleDevice(List<RuleDevice> ruleDeviceList) {
		int total = 0;
		try {
			total = ruleDeviceDao.insertRuleDevice(ruleDeviceList);
			
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (ruleDeviceList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int deleteRuleDeviceByRuleId(int ruleId) {
		if(ruleId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = ruleDeviceDao.deleteRuleDeviceByRuleId(ruleId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return total;
	}

	@Override
	public int deleteRuleDeviceByDeviceId(int deviceId) {
		if(deviceId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = ruleDeviceDao.deleteRuleDeviceByDeviceId(deviceId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return total;
	}

	@Override
	public List<RuleGroup> getRuleGroupByRuleId(int ruleId) {
		if(ruleId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<RuleGroup> deviceGroupList = ruleGroupDao.getRuleGroupByRuleId(ruleId);
		if (deviceGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceGroupList;
	}

	@Override
	public List<RuleGroup> getRuleGroupByGroupId(int groupId) {
		if(groupId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<RuleGroup> ruleGroupList = ruleGroupDao.getRuleGroupByGroupId(groupId);
		if (ruleGroupList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return ruleGroupList;
	}

	@Override
	public int insertRuleGroup(RuleGroup ruleGroup) {
		if(ruleGroup.getRuleId() == 0 || ruleGroup.getGroupId() == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = 0;
		try {
			total = ruleGroupDao.insertRuleGroup(ruleGroup);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int batchInsertRuleGroup(List<RuleGroup> ruleGroupList) {
		int total = 0;
		try {
			total = ruleGroupDao.insertRuleGroup(ruleGroupList);
			
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (ruleGroupList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int deleteRuleGroupByRuleId(int ruleId) {
		if(ruleId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = ruleGroupDao.deleteRuleGroupByRuleId(ruleId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return total;
	}

	@Override
	public int deleteRuleGroupByGroupId(int groupId) {
		if(groupId == 0){
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = ruleGroupDao.deleteRuleGroupByGroupId(groupId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return total;
	}
	
	/**
	 * ToDo:Add clause that judge device type exist or not.
	 */
	@Override
	public int insertPayload(Payload payload) {
		if (payload == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (payload.getId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.PAYLOAD_NOT_EMPTY);
		}
		int count = payloadDao.insertPayload(payload);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public int updatePayload(Payload payload) {
		if (payload == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getPayloadById(payload.getId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		int count = payloadDao.updatePayload(payload);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public boolean deletePayloadById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);	
		}
		boolean result = payloadDao.deletePayloadById(id);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public Payload getPayloadById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Payload payload = new Payload();
		try {
			payload = payloadDao.getPayloadById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return payload;
	}

	@Override
	public List<Payload> getPayloadByDeviceTypeId(int deviceTypeId) {
		if (deviceTypeId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<Payload> payloadList = new ArrayList<Payload>();
		try {
			payloadList = payloadDao.getPayloadByDeviceTypeId(deviceTypeId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return payloadList;
	}

}
