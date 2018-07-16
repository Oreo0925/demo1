package com.flowring.fish.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.flowring.cn.entity.Device;

public class FishRuleLog {

	private int id;

	@NotNull(message = "groupId is necessary")
	private int groupId;

	@NotNull(message = "deviceId is necessary")
	private int deviceId;

	private int checkMemberId;

	private Date checkTime;

	private String statusCode;

	private String controlCode;

	private float dissolvedOxygen;

	private float temperature;
	
	private float ph;

	@NotNull(message = "alarmType is necessary")
	private int alarmType;

	@NotNull(message = "alarmTime is necessary")
	private Date alarmTime;

	private boolean aeratorOpen;

	private boolean aeratorNormal;

	private boolean usedDrug;

	private boolean waterQuality;

	private String memo;

	private boolean informManager;

	private int result;

	private String resultStatus;

	private Date resultTime;

	private String history;

	private int closed;

	private String afProId;

	private Device device;

	private boolean callAuto;

	private int callTimes;

	private String callSid;

	private Date callDate;

	private String callStatus;

	private boolean callResponse;

	private int callDuration;

	private String callNumber;

	public FishRuleLog() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getCheckMemberId() {
		return checkMemberId;
	}

	public void setCheckMemberId(int checkMemberId) {
		this.checkMemberId = checkMemberId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getControlCode() {
		return controlCode;
	}

	public void setControlCode(String controlCode) {
		this.controlCode = controlCode;
	}

	public float getDissolvedOxygen() {
		return dissolvedOxygen;
	}

	public void setDissolvedOxygen(float dissolvedOxygen) {
		this.dissolvedOxygen = dissolvedOxygen;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
	public float getPh() {
		return ph;
	}

	public void setPh(float ph) {
		this.ph = ph;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public boolean isAeratorOpen() {
		return aeratorOpen;
	}

	public void setAeratorOpen(boolean aeratorOpen) {
		this.aeratorOpen = aeratorOpen;
	}

	public boolean isAeratorNormal() {
		return aeratorNormal;
	}

	public void setAeratorNormal(boolean aeratorNormal) {
		this.aeratorNormal = aeratorNormal;
	}

	public boolean isUsedDrug() {
		return usedDrug;
	}

	public void setUsedDrug(boolean usedDrug) {
		this.usedDrug = usedDrug;
	}

	public boolean isWaterQuality() {
		return waterQuality;
	}

	public void setWaterQuality(boolean waterQuality) {
		this.waterQuality = waterQuality;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isInformManager() {
		return informManager;
	}

	public void setInformManager(boolean informManager) {
		this.informManager = informManager;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public Date getResultTime() {
		return resultTime;
	}

	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public String getAfProId() {
		return afProId;
	}

	public void setAfProId(String afProId) {
		this.afProId = afProId;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public boolean isCallAuto() {
		return callAuto;
	}

	public void setCallAuto(boolean callAuto) {
		this.callAuto = callAuto;
	}

	public int getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(int callTimes) {
		this.callTimes = callTimes;
	}

	public String getCallSid() {
		return callSid;
	}

	public void setCallSid(String callSid) {
		this.callSid = callSid;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public boolean isCallResponse() {
		return callResponse;
	}

	public void setCallResponse(boolean callResponse) {
		this.callResponse = callResponse;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
