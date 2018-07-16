package com.flowring.fish.controller;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.DeviceControlService;
import com.flowring.cn.service.MemberService;
import com.flowring.fish.command.ControlCommand;
import com.flowring.fish.command.SettingCommand;
import com.flowring.fish.entity.FishGroup;
import com.flowring.fish.entity.FishRuleLog;
import com.flowring.fish.entity.FishRuleLogResult;
import com.flowring.fish.service.FishService;

@RestController
@RequestMapping("/fish")
public class FishController {
	@Autowired
	private FishService fishService;

	@Autowired
	private MemberService membService;

	@Autowired
	DeviceControlService deviceControlService;

	/*
	 * 查詢事件列表(fish_rule_Log) List
	 */
	@GetMapping(value = "/rule/logs")
	public SingleResponseObject<List<FishRuleLog>> getAllFishRuleLog() {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			List<FishRuleLog> ruleLogList = fishService.getAllFishRuleLog();
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢事件列表(fish_rule_Log) By device_id
	 */
	@GetMapping("/rule/logs/deviceId/{deviceId}")
	public SingleResponseObject<List<FishRuleLog>> getFishRuleLogByDeviceId(
			@PathVariable("deviceId") Integer deviceId) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			List<FishRuleLog> ruleLogList = fishService.getFishRuleLogByDeviceId(deviceId);
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢事件列表(fish_rule_Log) By check_member_id
	 */
	@GetMapping("/rule/logs/memberId/{memberId}/closed/{closed}")
	public SingleResponseObject<List<FishRuleLog>> getFishRuleLogByMemberIdAndClosed(
			@PathVariable("memberId") Integer memberId, @PathVariable("closed") Integer closed) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			List<FishRuleLog> ruleLogList = fishService.getFishRuleLogByMemberIdAndClosed(memberId, closed);
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢事件列表(fish_rule_Log) By group_id
	 */
	@GetMapping("/rule/logs/groupId/{groupId}")
	public SingleResponseObject<List<FishRuleLog>> getFishRuleLogByGroupId(@PathVariable("groupId") Integer groupId) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			List<FishRuleLog> ruleLogList = fishService.getFishRuleLogByGroupId(groupId);
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢事件列表(fish_rule_Log) By device_id 最新 N筆資料且closed為true
	 */
	@GetMapping("/rule/logs/deviceId/{deviceId}/count/{count}")
	public SingleResponseObject<List<FishRuleLog>> getFishRuleLogByDeviceId(@PathVariable("deviceId") Integer deviceId,
			@PathVariable("count") Integer count) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			List<FishRuleLog> ruleLogList = fishService.getFishRuleLogByDeviceId(deviceId, count);
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢事件列表(fish_rule_Log) By closed
	 */
	@GetMapping("/rule/logs/closed/{closed}")
	public SingleResponseObject<List<FishRuleLog>> getFishRuleLogByClosed(@PathVariable("closed") int closed) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			List<FishRuleLog> ruleLogList = fishService.getFishRuleLogByClosed(closed);
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢事件列表(fish_rule_Log) By id
	 */
	@GetMapping("/rule/log/{id}")
	public SingleResponseObject<FishRuleLog> getFishRuleLogById(@PathVariable("id") Integer id) {
		SingleResponseObject<FishRuleLog> ero = new SingleResponseObject<FishRuleLog>();
		try {
			FishRuleLog ruleLogList = fishService.getFishRuleLogById(id);
			ero.setData(ruleLogList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 新增事件(fish_rule_Log) group_id、device_id、data、alarm_type、alarm_time
	 */
	@PostMapping("/rule/log")
	public SingleResponseObject<FishRuleLog> insertDevice(@RequestBody FishRuleLog fishRuleLog) {
		SingleResponseObject<FishRuleLog> ero = new SingleResponseObject<FishRuleLog>();
		try {
			fishService.insertFishRuleLogAlarm(fishRuleLog);
			fishRuleLog = fishService.getFishRuleLogById(fishRuleLog.getId());
			ero.setData(fishRuleLog);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 事件存在(相同deviceId，closed為false)則Update，不存在則新增
	 */
	@PostMapping("/rule/log/insert/update")
	public SingleResponseObject<FishRuleLog> insertOrUpdateFishRuleLogAlert(@RequestBody FishRuleLog fishRuleLog) {
		SingleResponseObject<FishRuleLog> ero = new SingleResponseObject<FishRuleLog>();
		try {
			fishRuleLog = fishService.insertOrUpdateFishRuleLogAlarm(fishRuleLog);
			ero.setData(fishRuleLog);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 事件已處理 (result_status、result_time)
	 */
	@PutMapping("/rule/log/{id}/resultStatus")
	public SingleResponseObject<FishRuleLog> updateFishRuleLogResultStatus(@PathVariable("id") Integer id,
			@RequestBody FishRuleLog fishRuleLog) {
		SingleResponseObject<FishRuleLog> ero = new SingleResponseObject<FishRuleLog>();
		try {
			int count = fishService.updateFishRuleLogResultStatus(id, fishRuleLog.getResultStatus());
			FishRuleLog ruleLog = fishService.getFishRuleLogById(id);
			ero.setTotal(count);
			ero.setData(ruleLog);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 事件處理狀態改變，若原本 result_status != "", 則將上次的狀態存到 History，並更新
	 * result_status、result_time
	 */
	@PutMapping("/rule/log/{id}/resultStatus/history")
	public SingleResponseObject<FishRuleLog> updateFishRuleLogResultStatusAndHistory(@PathVariable("id") Integer id,
			@RequestBody FishRuleLog fishRuleLog) {
		SingleResponseObject<FishRuleLog> ero = new SingleResponseObject<FishRuleLog>();
		try {
			fishService.updateFishRuleLogResultStatusAndHistory(id, fishRuleLog);
			FishRuleLog ruleLog = fishService.getFishRuleLogById(id);
			ero.setData(ruleLog);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改事件列表(fish_rule_Log List) 的 closed 欄位
	 */
	@PutMapping("/rule/logs/closed/{closed}")
	public SingleResponseObject<List<FishRuleLog>> updateFishRuleLogListClosed(@PathVariable("closed") int closed,
			@RequestBody List<FishRuleLog> fishRuleLogList) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			int total = fishService.updateFishRuleLogListClosed(fishRuleLogList, closed);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping("/rule/logs/user/{memberId}")
	public SingleResponseObject<List<FishRuleLog>> getRuleDeviceByMemberId(@PathVariable("memberId") Integer memberId,
			@RequestParam("lastUpdate") long lastUpdate) {

		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			FishRuleLogResult result = fishService.checkFishRuleLogLastUpdate(memberId, lastUpdate);
			ero.setData(result.getFishRuleLogResult());
			if (result.isEmpty()) {
				ero.setMessage("0");
			}
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改事件列表(fish_rule_Log List) 的 afProId and closed 欄位
	 */
	@PutMapping("/rule/log/{id}/afProId/closed")
	public SingleResponseObject<List<FishRuleLog>> updateFishRuleLogAfPropIdAndClosed(@PathVariable("id") Integer id,
			@RequestBody FishRuleLog fishRuleLog) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			fishRuleLog.setId(id);
			int total = fishService.updateFishRuleLogAfPropIdAndClosed(fishRuleLog);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改事件列表(fish_rule_Log List) 的 check_member_id, checkTime 欄位
	 */
	@PutMapping("/rule/log/{id}/memberId/{memberId}")
	public SingleResponseObject<List<FishRuleLog>> updateFishRuleLogMemberId(@PathVariable("id") Integer id,
			@PathVariable("memberId") Integer memberId) {
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		try {
			int total = fishService.updateFishRuleLogMemberId(id, memberId);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping("/rule/logs/user/{memberId}/unresolved")
	public SingleResponseObject<Integer> getFieshRuleLogUnResolved(@PathVariable("memberId") Integer memberId,
			@RequestParam("lastUpdate") long lastUpdate) {

		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int count = fishService.checkFishRuleLogUnResolved(memberId, lastUpdate);
			ero.setData(count);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}

		return ero;
	}

	@GetMapping("/rule/logs/user/{memberId}/resolved")
	public SingleResponseObject<Integer> getFieshRuleLogResolved(@PathVariable("memberId") Integer memberId,
			@RequestParam("lastUpdate") long lastUpdate) {

		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int count = fishService.checkFishRuleLogTotalResolved(memberId, lastUpdate);
			ero.setData(count);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}

		return ero;
	}

	@CrossOrigin
	@GetMapping("/rule/logs/device/{deviceId}/resolvedlist")
	public SingleResponseObject<List<FishRuleLog>> getFieshRuleLogResolvedList(@PathVariable("deviceId") int deviceId,
			@RequestParam("lastUpdate") long lastUpdate) {

		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();

		try {
			List<FishRuleLog> rishRuleLogList = fishService.checkFishRuleLogResolvedLastUpdate(deviceId, lastUpdate);
			ero.setData(rishRuleLogList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}

		return ero;
	}
	

	@PutMapping("/device/{deviceId}/control")
	public SingleResponseObject<Boolean> deviceControl(@PathVariable("deviceId") int deviceId,
			@RequestBody ControlCommand controlCommand) {

		SingleResponseObject<Boolean> ero = new SingleResponseObject<Boolean>();

		try {
			controlCommand.setDeviceId(deviceId);
			deviceControlService.sendCommand(controlCommand);
			ero.setData(true);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}

		return ero;
	}

	@PutMapping("/device/{deviceId}/setting")
	public SingleResponseObject<Boolean> deviceSetting(@PathVariable("deviceId") int deviceId,
			@RequestBody SettingCommand settingCommand) {

		SingleResponseObject<Boolean> ero = new SingleResponseObject<Boolean>();

		try {
			settingCommand.setDeviceId(deviceId);
			deviceControlService.sendCommand(settingCommand);
			ero.setData(true);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}

		return ero;
	}

	@PutMapping("/rule/log/{id}/replayLater")
	public SingleResponseObject<Boolean> changeMemberForReplayLater(
			@PathVariable("id") int id,
			@RequestBody FishRuleLog fishRuleLog) {

		SingleResponseObject<Boolean> ero = new SingleResponseObject<Boolean>();

		try {
			fishRuleLog.setId(id);
			boolean rs = fishService.changeMemberForReplayLater(fishRuleLog);
			ero.setData(rs);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}

		return ero;
	}
	
	@CrossOrigin
	@GetMapping("/rule/logs/user/{memberId}/unassigned")
	public SingleResponseObject<List<FishRuleLog>> getFieshRuleLogUnAssigned(@PathVariable("memberId") Integer memberId,
			@RequestParam("lastUpdate") long lastUpdate) {
		
		SingleResponseObject<List<FishRuleLog>> ero = new SingleResponseObject<List<FishRuleLog>>();
		
		try {
			FishRuleLogResult result = fishService.getAllUnResolvedFishRuleLogByMemberId(memberId, lastUpdate);
			ero.setData(result.getFishRuleLogResult());
			if (result.isEmpty()) {
				ero.setMessage("0");
			}
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		
		return ero;

	}
	
	@GetMapping("/index/groupId/{groupId}")
	public SingleResponseObject<FishGroup> getIndexInfo(@PathVariable("groupId") Integer groupId) {
		SingleResponseObject<FishGroup> ero = new SingleResponseObject<FishGroup>();
		try {
			FishGroup fishGroup = fishService.getIndexInfo(groupId);
			ero.setData(fishGroup);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
}
