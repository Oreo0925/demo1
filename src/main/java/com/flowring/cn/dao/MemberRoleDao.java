package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.MemberRole;

public interface MemberRoleDao {
		
	public int insertMemberRole(MemberRole memberRole);
	
	public int insertMemberRoleList(List<MemberRole> memberRoleList);
	
	public int deleteMemberRoleList(List<MemberRole> memberRoleList);
	
	public List<MemberRole> getMemberRoleByMemberId(int memberId);
	
	public List<MemberRole> getMemberRoleByRoleId(int roleId);
}
