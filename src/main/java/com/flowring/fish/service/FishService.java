package com.flowring.fish.service;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.RuleDevice;
import com.flowring.fish.entity.FishGroup;
import com.flowring.fish.entity.FishRuleLog;
import com.flowring.fish.entity.FishRuleLogResult;

public interface FishService {
	public List<FishRuleLog> getAllFishRuleLog();

	public List<FishRuleLog> getFishRuleLogByDeviceId(int deviceId);
	
	public List<FishRuleLog> getFishRuleLogByDeviceId(int devcieId, int count);

	public List<FishRuleLog> getFishRuleLogByGroupId(int groupId);
	
	public List<FishRuleLog> getFishRuleLogByClosed(int closed);
	
	public FishRuleLog getFishRuleLogById(int id);
	
	public int insertFishRuleLogAlarm(FishRuleLog fishRuleLog);
	
	public int updateFishRuleLogHistory(int id, String history);
	
	public int updateFishRuleLogResultStatus(int id, String resultStatus);

	public int updateFishRuleLogListClosed(List<FishRuleLog> fishRuleLogList, int closed);
	
	public int updateFishRuleLogAlarm(FishRuleLog fishRuleLog);
	
	public List<FishRuleLog> getFishRuleLogByDeviceIdAndClosed(int deviceId, int closed);
	
	public FishRuleLog insertOrUpdateFishRuleLogAlarm(FishRuleLog fishRuleLog);

	public int updateFishRuleLogResultStatusAndHistory(Integer id,
			FishRuleLog fishRuleLog);

	public int updateFishRuleLogAfPropIdAndClosed(FishRuleLog fishRuleLog);
	
	public FishRuleLogResult checkFishRuleLogLastUpdate(int memberId, long lastTime);
	
	public List<FishRuleLog> checkFishRuleLogResolvedLastUpdate(int deviceId, long lastTime);
	
	public int checkFishRuleLogUnResolved(int memberId, long lastTime);
	
	public int checkFishRuleLogTotalResolved(int memberId, long lastTime);
	
	public int updateFishRuleLogMemberId(int id, int memberId);
	
	public Member getNextMemberByGroupId(int groupId);

	public int getFishRuleLogCountFinish();
	
	public int getFishRuleLogCountNotFinish();

	public List<FishRuleLog> getFishRuleLogByMemberIdAndClosed(int memberId, int closed);
	
	public FishRuleLogResult getAllUnResolvedFishRuleLogByMemberId(int memberId, long lastTime);

	public boolean changeMemberForReplayLater(FishRuleLog fishRuleLog);
	
	public FishRuleLog getFishRuleLogByCallSid(String callSid);
	
	public int updateFishRuleLogCallData(FishRuleLog fishRuleLog);
	
	public void callService(FishRuleLog fishRuleLog);
	
	public List<FishRuleLog> getFishRuleLogNeedCall();
	
	public FishGroup getIndexInfo(int deviceId);
	
}
