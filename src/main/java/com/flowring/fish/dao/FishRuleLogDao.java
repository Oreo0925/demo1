package com.flowring.fish.dao;

import java.util.Date;
import java.util.List;

import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.fish.entity.FishRuleLog;

public interface FishRuleLogDao {
	public List<FishRuleLog> getAllFishRuleLog();

	public List<FishRuleLog> getFishRuleLogByDeviceId(int deviceId);

	public List<FishRuleLog> getFishRuleLogByGroupId(int groupId);

	public List<FishRuleLog> getFishRuleLogByDeviceIdAndClosed(int devcieId, int count, int closed1, int closed2);

	public List<FishRuleLog> getFishRuleLogByClosed(int closed);

	public List<FishRuleLog> getNotClosedFishRuleLogByGroupId(int groupId);
	
	public FishRuleLog getFishRuleLogById(int id);

	public int insertFishRuleLogAlarm(FishRuleLog fishRuleLog);

	public int updateFishRuleLogHistory(int id, String history);

	public int updateFishRuleLogListClosed(List<FishRuleLog> fishRuleLogList, int closed);

	public int updateFishRuleLogResultStatus(int id, String resultStatus, Date resultTime);

	public int updateFishRuleLogCheckMemberIdAndCheckTime(List<FishRuleLog> fishRuleLogList, int memberId,
			Date checkTime);

	public List<FishRuleLog> getFishRuleLogListByMemberIdAndGroupList(int memberId, int closed, List<Group> groupList,
			int count);

	public int updateFishRuleLogAlarm(FishRuleLog fishRuleLog);

	public List<FishRuleLog> getFishRuleLogByDeviceIdAndClosed(int deviceId, int closed);

	public Date checkFishRuleLoglastUpdate();

	public int updateFishRuleLogResultStatusAndHistory(FishRuleLog fishRuleLog);

	public int updateFishRuleLogAfPropIdAndClosed(FishRuleLog fishRuleLog);

	public int updateFishRuleLogMemberIdAndCheckTime(int id, int memberId, Date checkTime);

	public int getFishRuleLogCountByClosed(int closed);

	public int getFishRuleLogCountByNotClosed(int closed);

	public List<FishRuleLog> getFishRuleLogFinish();

	public List<FishRuleLog> getFishRuleLogByMemberIdAndClosed(int memberId, int closed);

	public List<FishRuleLog> getFishRuleLogByMemberFinish(int memberId, int closed1, int closed2);

	public int updateFishRuleLogAlarmAndMemberId(FishRuleLog fishRuleLog);

	public List<Member> getNoJobMemberListByPGroupId(int groupId);

	public List<FishRuleLog> getOldestFishRuleLogInTheSameGroup(int groupId);
	
	public List<FishRuleLog> getAllNotAssignedFishRuleLogInTheSameGroup(int groupId);
	
	public int updateFishRuleLogCallData(FishRuleLog fishRuleLog);
	
	public List<FishRuleLog> getAllNeedCall();
	
	public FishRuleLog getFishRuleLogByCallSid(String callSid);
}
