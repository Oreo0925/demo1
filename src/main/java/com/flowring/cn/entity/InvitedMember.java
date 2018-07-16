package com.flowring.cn.entity;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvitedMember {
	@NotNull(message = "id is necessary")
	private int id;
	
	@NotNull(message = "memberId is necessary")
	private int memberId;
	
	@NotNull(message = "loginId is necessary")
	private String loginId;
	
	@NotNull(message = "emailToken is necessary")
	private String emailToken;
	
	@NotNull(message = "deleted is necessary")
	boolean deleted;
	
	
	public InvitedMember() {
		super();
	}
	
	public InvitedMember(int id, int memberId, String loginId, String emailToken, boolean deleted) {
		super();
		this.memberId = memberId;
		this.id = id;
		this.loginId = loginId;
		this.emailToken = emailToken;
		this.deleted = deleted;
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@JsonIgnore
	public String getEmailToken() {
		return emailToken;
	}
	@JsonProperty
	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
