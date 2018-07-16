package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ForgetPwd {
	private int id;
	@NotNull(message = "memberId is necessary")
	private int memberId;
	private int validTime;
	private String verificationCode;
	private String status;
	private String changeTime;
	
	public ForgetPwd() {
		super();
	}
	
	public ForgetPwd(int id, int memberId, int validTime, String verificationCode, String status, String changeTime) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.validTime = validTime;
		this.verificationCode = verificationCode;
		this.status = status;
		this.changeTime = changeTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getValidTime() {
		return validTime;
	}

	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}
	@JsonIgnore
	public String getVerificationCode() {
		return verificationCode;
	}
	@JsonProperty
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
