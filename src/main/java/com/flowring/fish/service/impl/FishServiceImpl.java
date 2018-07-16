package com.flowring.fish.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.flowring.cn.dao.DeviceDao;
import com.flowring.cn.dao.GroupDao;
import com.flowring.cn.dao.MemberDao;
import com.flowring.cn.dao.MemberPropertiesDao;
import com.flowring.cn.dao.MemberRoleDao;
import com.flowring.cn.dao.RoleGroupDao;
import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.GroupProperties;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.MemberProperties;
import com.flowring.cn.entity.MemberRole;
import com.flowring.cn.entity.Role;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.service.GroupService;
import com.flowring.cn.service.MemberService;
import com.flowring.cn.util.InfluxDBUtils;
import com.flowring.cn.util.YuntongxunUtils;
import com.flowring.fish.dao.FishRuleLogDao;
import com.flowring.fish.entity.FishGroup;
import com.flowring.fish.entity.FishRuleLog;
import com.flowring.fish.entity.FishGroup.FishDevice;
import com.flowring.fish.entity.FishRuleLogResult;
import com.flowring.fish.service.FishService;

@Service
public class FishServiceImpl implements FishService {

	private static Logger logger = LoggerFactory.getLogger(FishServiceImpl.class);

	@Autowired
	private FishRuleLogDao fishRuleLogDao;

	@Autowired
	private MemberRoleDao memberRoleDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberPropertiesDao memPropDao;

	@Autowired
	private RoleGroupDao roleGroupDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private GroupService groupService;

	@Autowired
	private MemberService membService;

	@Autowired
	private DeviceService devService;

	@Scheduled(fixedRate = 300000)
	private void callTTS() throws InterruptedException {

		List<FishRuleLog> fishRulelogs = this.getFishRuleLogNeedCall();

		for (FishRuleLog fishRuleLog : fishRulelogs) {

			callService(fishRuleLog);
		}
	}

	public void callService(FishRuleLog fishRuleLog) {

		try {
			int groupId = fishRuleLog.getGroupId();

			Group group = groupService.getGroupById(groupId);

			if (group != null) {

				Map<String, GroupProperties> groupProperties = group.getPropMap();

				GroupProperties gp = groupProperties.get("owner");
				String owner = gp.getValue();

				String message = owner + "，您好。" + group.getName() + "鱼塘 溶氧值为，" + fishRuleLog.getDissolvedOxygen()
						+ " ，毫克每升 ，请注意。";

				gp = groupProperties.get("ownerPhone");
				String ownerPhone = gp.getValue();

				Calendar cal = Calendar.getInstance();

				String callSid = YuntongxunUtils.sendTTS(ownerPhone, message);

				if (callSid != null && !callSid.equals("")) {
					fishRuleLog.setCallSid(callSid);
					fishRuleLog.setCallDate(cal.getTime());
					fishRuleLog.setCallNumber(ownerPhone);
					fishRuleLog.setCallTimes(fishRuleLog.getCallTimes() + 1);
					this.updateFishRuleLogCallData(fishRuleLog);
				}
			}
		} catch (Exception e) {
			logger.error("error callService:", e);
		}

	}

	@Override
	public List<FishRuleLog> getAllFishRuleLog() {
		List<FishRuleLog> logList = fishRuleLogDao.getAllFishRuleLog();
		addDevice(logList);
		if (logList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return logList;
	}

	private void addDevice(List<FishRuleLog> logList) {
		Device device = new Device();
		for (FishRuleLog log : logList) {
			try {
				device = devService.getDeviceById(log.getDeviceId());
				log.setDevice(device);
			} catch (Exception e) {
				// 查無裝置、類型、屬性等...
			}
		}
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByDeviceId(int deviceId) {
		if (deviceId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<FishRuleLog> logList = fishRuleLogDao.getFishRuleLogByDeviceId(deviceId);
		addDevice(logList);
		if (logList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return logList;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByMemberIdAndClosed(int memberId, int closed) {
		if (closed >= 4) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_ERROR_CLOSED);
		}
		List<FishRuleLog> logList = new ArrayList<FishRuleLog>();
		if (closed <= 2) {
			logList = fishRuleLogDao.getFishRuleLogByMemberIdAndClosed(memberId, closed);
		} else if (closed == 3) {
			logList = fishRuleLogDao.getFishRuleLogByMemberFinish(memberId, 1, 2);
		}
		addDevice(logList);
		if (logList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return logList;
	}

	@Override
	public FishRuleLogResult getAllUnResolvedFishRuleLogByMemberId(int memberId, long lastTime) {

		FishRuleLogResult fishResult = new FishRuleLogResult();
		List<FishRuleLog> result = new ArrayList<FishRuleLog>();

		long start = System.currentTimeMillis();

		logger.info("Current Time Start:" + start);

		try {
			while (true) {

				Date last = fishRuleLogDao.checkFishRuleLoglastUpdate();
				long newestTime = last.getTime();

				if (newestTime > lastTime) {

					result = fishRuleLogDao.getAllNotAssignedFishRuleLogInTheSameGroup(-1);

					break;
				}

				long now = System.currentTimeMillis();

				if ((now - start) > 30000) {
					fishResult.setEmpty(true);
					break;
				}

				Thread.currentThread().sleep(2000);
			}
		} catch (Exception e) {
			logger.error("Error checkFishRuleLogLastUpdate:", e);
		}

		addDevice(result);

		fishResult.setFishRuleLogResult(result);
		return fishResult;

	}

	@Override
	public List<FishRuleLog> getFishRuleLogByDeviceId(int devcieId, int count) {
		if (devcieId == 0 || count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<FishRuleLog> logList = fishRuleLogDao.getFishRuleLogByDeviceIdAndClosed(devcieId, count, 1, 2);
		addDevice(logList);
		if (logList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return logList;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByGroupId(int groupId) {
		if (groupId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<FishRuleLog> logList = fishRuleLogDao.getFishRuleLogByGroupId(groupId);
		addDevice(logList);
		if (logList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return logList;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByClosed(int closed) {
		if (closed >= 4) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_ERROR_CLOSED);
		}
		List<FishRuleLog> logList = new ArrayList<FishRuleLog>();
		if (closed <= 2) {
			logList = fishRuleLogDao.getFishRuleLogByClosed(closed);
		} else if (closed == 3) {
			logList = fishRuleLogDao.getFishRuleLogFinish();
		}
		addDevice(logList);
		if (logList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return logList;
	}

	@Override
	public FishRuleLog getFishRuleLogById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		FishRuleLog ruleLog = new FishRuleLog();
		try {
			ruleLog = fishRuleLogDao.getFishRuleLogById(id);
			ruleLog.setDevice(devService.getDeviceById(ruleLog.getDeviceId()));
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return ruleLog;
	}

	@Override
	public int insertFishRuleLogAlarm(FishRuleLog fishRuleLog) {
		if (fishRuleLog == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int id = fishRuleLogDao.insertFishRuleLogAlarm(fishRuleLog);
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return id;
	}

	@Override
	public int updateFishRuleLogHistory(int id, String history) {
		if (id == 0 || history == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = fishRuleLogDao.updateFishRuleLogHistory(id, history);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int updateFishRuleLogResultStatus(int id, String resultStatus) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Date resultTime = new Date();
		int total = fishRuleLogDao.updateFishRuleLogResultStatus(id, resultStatus, resultTime);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int updateFishRuleLogListClosed(List<FishRuleLog> fishRuleLogList, int closed) {
		int rs = fishRuleLogDao.updateFishRuleLogListClosed(fishRuleLogList, closed);
		return rs;
	}

	/*
	 * 取得 memberId 擁有權限的GroupList
	 */
	private List<Group> getAllAuthGroupListByMemberId(Integer memberId) {
		List<MemberRole> memberRoleList = memberRoleDao.getMemberRoleByMemberId(memberId);
		List<Group> groupList = new ArrayList<Group>();
		for (MemberRole memberRole : memberRoleList) {
			List<RoleGroup> roleGroupList = roleGroupDao.getRoleGroupByRoleId(memberRole.getRoleId());
			for (RoleGroup roleGroup : roleGroupList) {
				Group group = groupDao.getGroupById(roleGroup.getGroupId());
				groupList.add(group);
				getChileGroupFishRulLog(group, groupList);
			}
		}
		return groupList;
	}

	/*
	 * 取得 Group 下的 Groups
	 */
	private List<Group> getChileGroupFishRulLog(Group group, List<Group> groupList) {
		List<Group> childGroupList = groupDao.getGroupByParentId(group.getId());
		groupList.addAll(childGroupList);
		for (Group childGroup : childGroupList) {
			getChileGroupFishRulLog(childGroup, groupList);
		}
		return groupList;
	}

	@Override
	public int updateFishRuleLogAlarm(FishRuleLog fishRuleLog) {
		if (fishRuleLog == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = fishRuleLogDao.updateFishRuleLogAlarm(fishRuleLog);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<FishRuleLog> getFishRuleLogByDeviceIdAndClosed(int deviceId, int closed) {
		List<FishRuleLog> logList = fishRuleLogDao.getFishRuleLogByDeviceIdAndClosed(deviceId, closed);
		return logList;
	}

	@Override
	public FishRuleLog insertOrUpdateFishRuleLogAlarm(FishRuleLog fishRuleLog) {
		// 取得相同deviceId，相同alarmType，closed 為 0 的 List<FishRuleLog>
		List<FishRuleLog> logList = getFishRuleLogByDeviceIdAndClosed(fishRuleLog.getDeviceId(), 0);
		FishRuleLog ruleLog = new FishRuleLog();
		fishRuleLog.setAlarmTime(new Date());
		if (logList.size() == 0) {
			// 如果一筆都沒有則新增裝置
			int lastId = insertFishRuleLogAlarm(fishRuleLog);
			fishRuleLog = fishRuleLogDao.getFishRuleLogById(lastId);

			if (fishRuleLog.getAlarmType() == 1) {
				fishRuleLog.setCallAuto(true);
				this.callService(fishRuleLog);
			}

		} else {
			for (int i = 0; i < logList.size(); i++) {
				if (i == 0) {
					ruleLog = logList.get(i);
					fishRuleLog.setId(ruleLog.getId());
					updateFishRuleLogAlarm(fishRuleLog);
					fishRuleLog = ruleLog;
				} else {
					// 如果超過一筆則將其他筆 closed 改為 1(結束)
					logList.remove(0);
					int total = updateFishRuleLogListClosed(logList, 1);
					logList = null;
					break;
				}
			}
		}

		assignmentFishRuleLog(fishRuleLog.getGroupId());
		return fishRuleLog;
	}

	// 如果有空閒的值班人員
	private boolean assignmentFishRuleLog(int groupId) {
		Member nextMember = getNextMemberByGroupId(groupId);
		boolean assignemt = false;
		if (nextMember.getId() != 0) {
			// 如果還有舊的FishRuleLog未分發
			FishRuleLog oldLog = getOldestFishRuleLogInTheSameGroup(groupId);
			if (oldLog.getId() != 0) {
				fishRuleLogDao.updateFishRuleLogMemberIdAndCheckTime(oldLog.getId(), nextMember.getId(), new Date());
				oldLog.setResultStatus(resultStatus(nextMember));
				updateFishRuleLogResultStatusAndHistory(oldLog.getId(), oldLog);
				assignemt = true;
			}
		}
		return assignemt;
	}

	/*
	 * 找到這一個群組中最舊的、memberId = 0、closed != 0 的一筆 FishRuleLog
	 */
	private FishRuleLog getOldestFishRuleLogInTheSameGroup(int groupId) {
		FishRuleLog fishRuleLog = new FishRuleLog();
		List<FishRuleLog> logList = fishRuleLogDao.getOldestFishRuleLogInTheSameGroup(groupId);
		if (logList.size() > 0) {
			fishRuleLog = logList.get(0);
		}
		return fishRuleLog;
	}

	private String resultStatus(Member member) {
		return "将任务分派给人员编号: " + member.getId() + ", 人员名称: " + member.getName();
	}

	private void updateFishRuleLogAlarmAndMemberId(FishRuleLog fishRuleLog) {
		if (fishRuleLog == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = fishRuleLogDao.updateFishRuleLogAlarmAndMemberId(fishRuleLog);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return;
	}

	@Override
	public int updateFishRuleLogResultStatusAndHistory(Integer id, FishRuleLog fishRuleLog) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Date resultTime = new Date();
		// 取出 FishRuleLog By Id
		FishRuleLog log = fishRuleLogDao.getFishRuleLogById(id);
		// 如果原本的狀態為空
		if (StringUtils.isBlank(log.getResultStatus())) {
			int total = fishRuleLogDao.updateFishRuleLogResultStatus(id, fishRuleLog.getResultStatus(), resultTime);
		} else {
			// 如果原本的狀態不為 0 則將前一次的狀態加入History，再更新狀態
			JSONObject jsonObj = new JSONObject();
			try {
				String history = log.getHistory();
				if (history == null) {
					history = "[]";
				}
				JSONArray jsonArrHistory = new JSONArray(history);
				jsonObj.put("resultStatus", log.getResultStatus());
				jsonObj.put("resultTime", log.getResultTime());
				jsonArrHistory.put(jsonObj);
				fishRuleLog.setId(id);
				fishRuleLog.setHistory(jsonArrHistory.toString());
				fishRuleLog.setResultTime(resultTime);
				fishRuleLogDao.updateFishRuleLogResultStatusAndHistory(fishRuleLog);
			} catch (JSONException e) {
				logger.debug("FishRuleLog 的 History 欄位轉型錯誤，FishRuleLogId: " + id + ", " + e);
			}
		}
		return 0;
	}

	@Override
	public FishRuleLogResult checkFishRuleLogLastUpdate(int memberId, long lastTime) {

		FishRuleLogResult fishResult = new FishRuleLogResult();
		List<FishRuleLog> result = new ArrayList<FishRuleLog>();

		long start = System.currentTimeMillis();

		logger.info("Current Time Start:" + start);

		try {
			while (true) {

				Date last = fishRuleLogDao.checkFishRuleLoglastUpdate();
				long newestTime = last.getTime();

				if (newestTime > lastTime) {

					result = fishRuleLogDao.getFishRuleLogByMemberIdAndClosed(memberId, 0);
					break;
				}

				long now = System.currentTimeMillis();

				if ((now - start) > 30000) {
					fishResult.setEmpty(true);
					break;
				}

				Thread.currentThread().sleep(2000);
			}
		} catch (Exception e) {
			logger.error("Error checkFishRuleLogLastUpdate:", e);
		}

		addDevice(result);

		fishResult.setFishRuleLogResult(result);
		return fishResult;
	}

	@Override
	public int updateFishRuleLogAfPropIdAndClosed(FishRuleLog fishRuleLog) {
		if (fishRuleLog == null || fishRuleLog.getId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = 0;
		boolean pass = verificationAfPropIdAndClosed(fishRuleLog);
		if (pass) {
			total = fishRuleLogDao.updateFishRuleLogAfPropIdAndClosed(fishRuleLog);
			if (total == 0) {
				throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
			}
		} else {
			throw new ConnesiaException(ConnesiaEnum.NOT_MATCH_AF_PRO_ID_AND_CLOSED);
		}

		assignmentFishRuleLog(fishRuleLog.getGroupId());
		return total;
	}

	private boolean verificationAfPropIdAndClosed(FishRuleLog fishRuleLog) {
		boolean pass = false;
		int closed = fishRuleLog.getClosed();
		boolean afProIsEmpty = StringUtils.isEmpty(fishRuleLog.getAfProId());
		if (closed == 0 && afProIsEmpty == true) {
			pass = true;
			fishRuleLog.setAfProId("");
		} else if (closed == 1 && afProIsEmpty == true) {
			pass = true;
			fishRuleLog.setAfProId("");
		} else if (closed == 2 && afProIsEmpty == false) {
			pass = true;
		}
		return pass;
	}

	@Override
	public int updateFishRuleLogMemberId(int id, int memberId) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = fishRuleLogDao.updateFishRuleLogMemberIdAndCheckTime(id, memberId, new Date());
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	/*
	 * 取得下一個值班人員
	 */
	@Override
	public Member getNextMemberByGroupId(int groupId) {
		// 取得此區域非暫停的值班人員
		List<Member> dutyMemList = getNoJobMemberListByPGroupId(groupId);
		// 取得最大的 priority
		BigInteger maxPriority = membService.getMaxEventPriority();
		// 取得下一個被抽到的值班人員(group 中 人員 event_priority 最小)
		Member nextDutyMem = new Member();
		BigInteger thisPriority = null;
		BigInteger minPriority = new BigInteger("0");
		for (int i = 0; i < dutyMemList.size(); i++) {
			Member member = dutyMemList.get(i);
			thisPriority = member.getEventPriority();
			if (i == 0) {
				nextDutyMem = member;
				minPriority = thisPriority;
			}
			// BigInteger 比較後， 0 兩者相等、1 前者較大、-1前者較小
			if (thisPriority.compareTo(minPriority) == -1) {
				minPriority = thisPriority;
				nextDutyMem = member;
			}
		}
		// 修改 priority + 1
		if (nextDutyMem.getId() != 0) {
			membService.updateMemberEventPriority(nextDutyMem.getId(), maxPriority.add(new BigInteger("1")));
		}
		return nextDutyMem;
	}

	private List<Member> getNoJobMemberListByPGroupId(int groupId) {
		return fishRuleLogDao.getNoJobMemberListByPGroupId(groupId);
	}

	@Override
	public int getFishRuleLogCountFinish() {
		int count = fishRuleLogDao.getFishRuleLogCountByNotClosed(0);
		return count;
	}

	@Override
	public int getFishRuleLogCountNotFinish() {
		int count = fishRuleLogDao.getFishRuleLogCountByClosed(0);
		return count;
	}

	@Override
	public int checkFishRuleLogUnResolved(int memberId, long lastTime) {
		int result = -1;

		long start = System.currentTimeMillis();

		try {
			while (true) {

				Date last = fishRuleLogDao.checkFishRuleLoglastUpdate();
				long newestTime = last.getTime();

				if (newestTime > lastTime) {

					result = this.getFishRuleLogCountNotFinish();
					break;
				}

				long now = System.currentTimeMillis();

				if ((now - start) > 30000) {
					break;
				}

				Thread.currentThread().sleep(2000);
			}

			if (result == -1) {
				result = this.getFishRuleLogCountNotFinish();
			}
		} catch (Exception e) {
			logger.error("Error checkFishRuleLogUnResolved:", e);
		}

		return result;
	}

	@Override
	public int checkFishRuleLogTotalResolved(int memberId, long lastTime) {
		int result = -1;

		long start = System.currentTimeMillis();

		try {
			while (true) {

				Date last = fishRuleLogDao.checkFishRuleLoglastUpdate();
				long newestTime = last.getTime();

				if (newestTime > lastTime) {

					result = this.getFishRuleLogCountFinish();
					break;
				}

				long now = System.currentTimeMillis();

				if ((now - start) > 30000) {
					break;
				}

				Thread.currentThread().sleep(2000);
			}

			if (result == -1) {
				result = this.getFishRuleLogCountFinish();
			}
		} catch (Exception e) {
			logger.error("Error checkFishRuleLogTotalResolved:", e);
		}

		return result;
	}

	@Override
	public List<FishRuleLog> checkFishRuleLogResolvedLastUpdate(int deviceId, long lastTime) {
		List<FishRuleLog> result = new ArrayList<FishRuleLog>();

		long start = System.currentTimeMillis();

		logger.info("Current Time Start:" + start);

		try {
			while (true) {

				Date last = fishRuleLogDao.checkFishRuleLoglastUpdate();
				long newestTime = last.getTime();

				if (newestTime > lastTime) {

					result = this.getFishRuleLogByDeviceId(deviceId, 10);
					break;
				}

				long now = System.currentTimeMillis();

				if ((now - start) > 30000) {
					break;
				}
				Thread.currentThread().sleep(2000);
			}

			if (result.size() == 0) {
				result = this.getFishRuleLogByDeviceId(deviceId, 10);
			}
		} catch (Exception e) {
			logger.error("Error checkFishRuleLogLastUpdate:", e);
		}
		addDevice(result);
		return result;
	}

	public boolean changeMemberForReplayLater(FishRuleLog fishRuleLog) {
		Member member = memberDao.getMemberById(fishRuleLog.getCheckMemberId());
		// 將 check_member_id 改為 0
		fishRuleLogDao.updateFishRuleLogMemberIdAndCheckTime(fishRuleLog.getId(), 0, new Date());
		// 更新 result_status 及 result_time 狀態
		fishRuleLog.setResultStatus(resultStatusForReplayLater(member));
		updateFishRuleLogResultStatusAndHistory(fishRuleLog.getId(), fishRuleLog);
		assignmentFishRuleLog(fishRuleLog.getGroupId());
		return true;
	}

	@Override
	public FishRuleLog getFishRuleLogByCallSid(String callSid) {
		FishRuleLog fishRuleLog = null;
		
		fishRuleLog = fishRuleLogDao.getFishRuleLogByCallSid(callSid);

		return fishRuleLog;

	}

	@Override
	public int updateFishRuleLogCallData(FishRuleLog fishRuleLog) {
		int result = 0;

		result = fishRuleLogDao.updateFishRuleLogCallData(fishRuleLog);

		return result;
	}

	public List<FishRuleLog> getFishRuleLogNeedCall() {
		List<FishRuleLog> fishRuleLogs = Collections.emptyList();

		fishRuleLogs = fishRuleLogDao.getAllNeedCall();

		return fishRuleLogs;
	}

	private String resultStatusForReplayLater(Member member) {
		return "稍后再拨，人员编号: " + member.getId() + ", 人员名称: " + member.getName();
	}

	@Override
	public FishGroup getIndexInfo(int groupId) {
		List<FishRuleLog> fishRuleLogList = fishRuleLogDao.getNotClosedFishRuleLogByGroupId(groupId);
		List<Device> childDevcieList = groupService.getGroupById(groupId).getChildDevcieList();
		FishGroup fishGroup = new FishGroup();
		List<FishDevice> fishDeviceList = new ArrayList<FishDevice>();
		fishGroup.setName(groupService.getGroupById(groupId).getName());
		fishRuleLogList.forEach(fishRuleLog -> {
			childDevcieList.forEach(device -> {
				if(fishRuleLog.getDeviceId() == device.getId()) {
					FishDevice fishDevice = this.setCeleFishDevice(fishRuleLog, device);
					fishDeviceList.add(fishDevice);
				}
			});
		});
		fishGroup.setChildDeviceList(fishDeviceList);
		return fishGroup;
	}
	
	private FishDevice setCeleFishDevice(FishRuleLog fishRuleLog, Device device) {
		FishDevice fishDevice = new FishDevice();
		fishDevice.setId(device.getId());
		fishDevice.setName(device.getName());
		fishDevice.setTypeId(device.getDeviceTypeId());
		fishDevice.setDissolvedOxygen(fishRuleLog.getDissolvedOxygen());
		fishDevice.setTemperature(fishRuleLog.getTemperature());
		fishDevice.setPh(fishRuleLog.getPh());
		fishDevice.setEnabled(device.isEnabled());
		fishDevice.setAutomatic(device.isAutomatic());
		return fishDevice;
	}

}
