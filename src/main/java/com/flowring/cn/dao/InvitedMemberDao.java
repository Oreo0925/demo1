package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.InvitedMember;

public interface InvitedMemberDao {
	
	public int insertInvitedMember(InvitedMember invitedMember);
	
	public int updateInvitedMember(InvitedMember invitedMember);
	
	public InvitedMember getInvitedMemberById(int id);
	
	public InvitedMember getInvitedMemberByLoginId(String loginId);
	
	public InvitedMember getInvitedMemberByEmailToken(String emailToken);
	
	public List<InvitedMember> getInvitedMembersByMemberId(int memberId);
	
	public List<InvitedMember> getAllInvitedMembers();
	
	public boolean deleteInvitedMemberByLoginId(String loginId);
	
}
