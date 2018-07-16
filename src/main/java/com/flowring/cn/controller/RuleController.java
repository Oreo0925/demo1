package com.flowring.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.entity.Payload;
import com.flowring.cn.entity.Rule;
import com.flowring.cn.entity.RuleDevice;
import com.flowring.cn.entity.RuleGroup;
import com.flowring.cn.entity.RuleLog;
import com.flowring.cn.entity.ToDoList;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.RuleService;

@RestController
@RequestMapping("/")
public class RuleController {

	@Autowired
	RuleService ruleManagementService;

	@PostMapping(value = "rule")
	public SingleResponseObject<Rule> insertRule(@RequestBody Rule rule)
			throws Exception {
		SingleResponseObject<Rule> ero = new SingleResponseObject<Rule>();
		try {
			ruleManagementService.insertRule(rule);
			ero.setData(rule);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "rule")
	public SingleResponseObject<Rule> updateRule(@RequestBody Rule rule)
			throws Exception {
		SingleResponseObject<Rule> ero = new SingleResponseObject<Rule>();
		try {
			ruleManagementService.updateRule(rule);
			ero.setData(rule);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "rule/{id}")
	public SingleResponseObject<Rule> deleteRuleById(@PathVariable("id") int id) {
		SingleResponseObject<Rule> ero = new SingleResponseObject<Rule>();
		try {
			boolean result = ruleManagementService.deleteRuleById(id);
			ero.setTotal(1);
			ero.setSuccess(result);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "rule/{id}")
	public SingleResponseObject<Rule> getRuleById(@PathVariable("id") int id) {
		SingleResponseObject<Rule> ero = new SingleResponseObject<Rule>();
		try {
			Rule rule = ruleManagementService.getRuleById(id);
			ero.setData(rule);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "rules/{deviceTypeId}")
	public SingleResponseObject<List<Rule>> getRuleByTypeId(
			@PathVariable("deviceTypeId") int deviceTypeId) {
		SingleResponseObject<List<Rule>> ero = new SingleResponseObject<List<Rule>>();
		try {
			List<Rule> ruleList = ruleManagementService
					.getRuleByDeviceTypeId(deviceTypeId);
			ero.setData(ruleList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "ruleLog")
	public SingleResponseObject<RuleLog> insertRuleLog(
			@RequestBody RuleLog ruleLog) throws Exception {
		SingleResponseObject<RuleLog> ero = new SingleResponseObject<RuleLog>();
		try {
			ruleManagementService.insertRuleLog(ruleLog);
			ero.setData(ruleLog);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "ruleLog")
	public SingleResponseObject<RuleLog> updateRuleLog(
			@RequestBody RuleLog ruleLog) throws Exception {
		SingleResponseObject<RuleLog> ero = new SingleResponseObject<RuleLog>();
		try {
			ruleManagementService.updateRuleLog(ruleLog);
			ero.setData(ruleLog);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "ruleLog/{ruleLogId}")
	public SingleResponseObject<RuleLog> getRuleLogById(
			@PathVariable("ruleLogId") int ruleLogId) {
		SingleResponseObject<RuleLog> ero = new SingleResponseObject<RuleLog>();
		try {
			RuleLog ruleLog = ruleManagementService.getRuleLogById(ruleLogId);
			ero.setData(ruleLog);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "ruleLog/device/{deviceId}")
	public SingleResponseObject<List<RuleLog>> getRuleLogByDeviceId(
			@PathVariable("deviceId") int deviceId) {
		SingleResponseObject<List<RuleLog>> ero = new SingleResponseObject<List<RuleLog>>();
		try {
			List<RuleLog> ruleLogList = ruleManagementService
					.getRuleLogByDeviceId(deviceId);
			ero.setData(ruleLogList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "ruleLog/{ruleLogId}")
	public SingleResponseObject<RuleLog> deleteRuleLogById(
			@PathVariable("ruleLogId") int ruleLogId) {
		SingleResponseObject<RuleLog> ero = new SingleResponseObject<RuleLog>();
		try {
			boolean result = ruleManagementService.deleteRuleLogById(ruleLogId);
			ero.setTotal(1);
			ero.setSuccess(result);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "todo")
	public SingleResponseObject<ToDoList> insertToDoList(
			@RequestBody ToDoList toDoList) throws Exception {
		SingleResponseObject<ToDoList> ero = new SingleResponseObject<ToDoList>();
		try {
			ruleManagementService.insertToDoList(toDoList);
			ero.setData(toDoList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "todo")
	public SingleResponseObject<ToDoList> updateToDoList(
			@RequestBody ToDoList toDoList) throws Exception {
		SingleResponseObject<ToDoList> ero = new SingleResponseObject<ToDoList>();
		try {
			ToDoList newToDoList = ruleManagementService
					.updateToDoList(toDoList);
			ero.setData(newToDoList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "todo/{todoListId}")
	public SingleResponseObject<ToDoList> getToDoListById(
			@PathVariable("todoListId") int todoListId) {
		SingleResponseObject<ToDoList> ero = new SingleResponseObject<ToDoList>();
		try {
			ToDoList toDoList = ruleManagementService
					.getToDoListById(todoListId);
			ero.setData(toDoList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "todo/list/{ruleId}")
	public SingleResponseObject<List<ToDoList>> getToDoListByRuleId(
			@PathVariable("ruleId") int ruleId) {
		SingleResponseObject<List<ToDoList>> ero = new SingleResponseObject<List<ToDoList>>();
		try {
			List<ToDoList> toDoList = ruleManagementService
					.getToDoListByRuleId(ruleId);
			ero.setData(toDoList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "todo/{todoListId}")
	public SingleResponseObject<ToDoList> deleteToDoList(
			@PathVariable("todoListId") int todoListId) {
		SingleResponseObject<ToDoList> ero = new SingleResponseObject<ToDoList>();
		try {
			boolean result = ruleManagementService.deleteToDoList(todoListId);
			ero.setTotal(1);
			ero.setSuccess(result);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "payload")
	public SingleResponseObject<Payload> insertPayload(
			@RequestBody Payload payload) throws Exception {
		SingleResponseObject<Payload> ero = new SingleResponseObject<Payload>();
		try {
			ruleManagementService.insertPayload(payload);
			ero.setData(payload);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "payload")
	public SingleResponseObject<Payload> updatePayload(
			@RequestBody Payload payload) throws Exception {
		SingleResponseObject<Payload> ero = new SingleResponseObject<Payload>();
		try {
			ruleManagementService.updatePayload(payload);
			ero.setData(payload);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "payload/{payloadId}")
	public SingleResponseObject<Payload> getPayloadById(
			@PathVariable("payloadId") int payloadId) {
		SingleResponseObject<Payload> ero = new SingleResponseObject<Payload>();
		try {
			Payload payload = ruleManagementService.getPayloadById(payloadId);
			ero.setData(payload);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "payloads/{deviceTypeId}")
	public SingleResponseObject<List<Payload>> getPayloadByDeviceTypeId(
			@PathVariable("deviceTypeId") int deviceTypeId) {
		SingleResponseObject<List<Payload>> ero = new SingleResponseObject<List<Payload>>();
		try {
			List<Payload> payload = ruleManagementService
					.getPayloadByDeviceTypeId(deviceTypeId);
			ero.setData(payload);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "payload/{id}")
	public SingleResponseObject<Payload> deletePayloadById(
			@PathVariable("id") int id) {
		SingleResponseObject<Payload> ero = new SingleResponseObject<Payload>();
		try {
			boolean result = ruleManagementService.deletePayloadById(id);
			ero.setTotal(1);
			ero.setSuccess(result);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢RuleDeviceList By ruleId
	 */
	@GetMapping("/rule/devices/{ruleId}")
	public SingleResponseObject<List<RuleDevice>> getRuleDeviceByRuleId(
			@PathVariable("ruleId") Integer ruleId) {
		SingleResponseObject<List<RuleDevice>> ero = new SingleResponseObject<List<RuleDevice>>();
		try {
			List<RuleDevice> ruleDeviceList = ruleManagementService
					.getRuleDeviceByRuleId(ruleId);
			ero.setData(ruleDeviceList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢RuleDeviceList ByDeviceId
	 */
	@GetMapping("/rules/device/{deviceId}")
	public SingleResponseObject<List<RuleDevice>> getRuleDeviceByDeviceId(
			@PathVariable("deviceId") Integer deviceId) {
		SingleResponseObject<List<RuleDevice>> ero = new SingleResponseObject<List<RuleDevice>>();
		try {
			List<RuleDevice> ruleDeviceList = ruleManagementService
					.getRuleDeviceByDeviceId(deviceId);
			ero.setData(ruleDeviceList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增RuleDevice
	 */
	@PostMapping("/rule/device")
	@Transactional
	public SingleResponseObject<RuleDevice> insertRuleDevice(
			@RequestBody RuleDevice ruleDevice) {
		SingleResponseObject<RuleDevice> ero = new SingleResponseObject<RuleDevice>();
		try {
			ruleManagementService.insertRuleDevice(ruleDevice);
			ero.setData(ruleDevice);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增RuleDeviceList
	 */
	@PostMapping("/rules/devices")
	@Transactional
	public SingleResponseObject<Integer> batchInsertRuleDevice(
			@RequestBody List<RuleDevice> ruleDevice) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = ruleManagementService.batchInsertRuleDevice(ruleDevice);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 刪除RuleDevice By DeviceId
	 */
	@DeleteMapping("/rules/device/{deviceId}")
	@Transactional
	public SingleResponseObject<Integer> deleteRuleDeviceByDeviceId(
			@PathVariable("deviceId") Integer deviceId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = ruleManagementService
					.deleteRuleDeviceByDeviceId(deviceId);
			ero.setData(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除RuleDevice By RuleId
	 */
	@DeleteMapping("/rule/devices/{ruleId}")
	@Transactional
	public SingleResponseObject<Integer> deleteRuleDeviceByRuleId(
			@PathVariable("ruleId") Integer ruleId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = ruleManagementService.deleteRuleDeviceByRuleId(ruleId);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢RuleGroupList By ruleId
	 */
	@GetMapping("/rule/groups/{ruleId}")
	@Transactional
	public SingleResponseObject<List<RuleGroup>> getRuleGroupByRuleId(
			@PathVariable("ruleId") Integer ruleId) {
		SingleResponseObject<List<RuleGroup>> ero = new SingleResponseObject<List<RuleGroup>>();
		try {
			List<RuleGroup> ruleGroupList = ruleManagementService
					.getRuleGroupByRuleId(ruleId);
			ero.setData(ruleGroupList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢RuleGroupList ByGroupId
	 */
	@GetMapping("/rules/group/{groupId}")
	@Transactional
	public SingleResponseObject<List<RuleGroup>> getRuleGroupByGroupId(
			@PathVariable("groupId") Integer groupId) {
		SingleResponseObject<List<RuleGroup>> ero = new SingleResponseObject<List<RuleGroup>>();
		try {
			List<RuleGroup> roleMenuList = ruleManagementService
					.getRuleGroupByGroupId(groupId);
			ero.setData(roleMenuList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增RuleGroup
	 */
	@PostMapping("/rule/group")
	@Transactional
	public SingleResponseObject<RuleGroup> insertRuleGroup(@RequestBody RuleGroup ruleGroup) {
		SingleResponseObject<RuleGroup> ero = new SingleResponseObject<RuleGroup>();
		try {
			ruleManagementService.insertRuleGroup(ruleGroup);
			ero.setData(ruleGroup);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增RuleGroupList
	 */
	@PostMapping("/rules/groups")
	@Transactional
	public SingleResponseObject<Integer> insertRuleGroup(
			@RequestBody List<RuleGroup> ruleGroupList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = ruleManagementService
					.batchInsertRuleGroup(ruleGroupList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 刪除RuleGroup By GroupId
	 */
	@DeleteMapping("/rules/group/{groupId}")
	@Transactional
	public SingleResponseObject<Integer> deleteRuleGroupByGroupId(
			@PathVariable("groupId") Integer groupId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = ruleManagementService.deleteRuleGroupByGroupId(groupId);
			ero.setData(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除RuleGroup By RuleId
	 */
	@DeleteMapping("/rule/groups/{ruleId}")
	@Transactional
	public SingleResponseObject<Integer> deleteRuleGroupByRuleId(
			@PathVariable("ruleId") Integer ruleId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = ruleManagementService.deleteRuleGroupByRuleId(ruleId);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
}
