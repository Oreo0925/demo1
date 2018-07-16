package com.flowring.cn.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Member {
	
	@NotNull(message = "id is necessary")
	private int id;
	
	@NotNull(message = "fullId is necessary")
	private String fullId;
	
	@NotNull(message = "loginId is necessary")
	private String loginId;
	
	private String name;
	
	private String email;
	
	@NotNull(message = "parentId is necessary")
	private int parentId;
	
	@NotNull(message = "password is necessary")
	private String password;
	
	private String description;
	
	private String afMemberId;
	
	private String address;
	
	private boolean enable;
	
	private String lastLoginIp;
	
	private String lastLoginTime;
	
	private String emailToken;
	
	private String sso;
	
	private String oauthToken;
	
	private Role mainRole;
	
	private BigInteger eventPriority;
	
	private String phone;
	
	private boolean deleted;
	
	private List<Role> roleList;
	
	private List<Group> groupList;
	
	private String roleAllName;
	
	private Map<String, MemberProperties> propMap = new HashMap<String, MemberProperties>();
	
	public Member() {
		super();
	}
	
	public Member(int id, String fullId, String loginId, String name, String email, int parentId, String password, String description,
			String afMemberId, String address, boolean enable, String lastLoginIp, String lastLoginTime, String emailToken, String sso,
			String oauthToken, Role mainRole, List<Role> roleList, List<Group> groupList, String roleAllName) {
		super();
		this.id = id;
		this.fullId = fullId;
		this.loginId = loginId;
		this.name = name;
		this.email = email;
		this.parentId = parentId;
		this.password = password;
		this.description = description;
		this.afMemberId = afMemberId;
		this.address = address;
		this.enable = enable;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.emailToken = emailToken;
		this.sso = sso;
		this.oauthToken = oauthToken;
		this.mainRole = mainRole;
		this.roleList = roleList;
		this.groupList = groupList;
		this.roleAllName = roleAllName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAfMemberId() {
		return afMemberId;
	}

	public void setAfMemberId(String afMemberId) {
		this.afMemberId = afMemberId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public BigInteger getEventPriority() {
		return eventPriority;
	}

	public void setEventPriority(BigInteger eventPriority) {
		this.eventPriority = eventPriority;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Role getMainRole() {
		return mainRole;
	}

	public void setMainRole(Role mainRole) {
		this.mainRole = mainRole;
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getRoleAllName() {
		return roleAllName;
	}

	public void setRoleAllName(String roleAllName) {
		this.roleAllName = roleAllName;
	}
	
	public void setPropList(
			List<MemberProperties> memPropList) {
		for (MemberProperties memProp : memPropList) {
			this.propMap.put(memProp.getEnglishName(),
					memProp);
		}
	}

	public Map<String, MemberProperties> getPropMap() {
		return propMap;
	}
	
	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
