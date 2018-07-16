package com.flowring.cn.service;

import java.math.BigInteger;
import java.util.List;

import com.flowring.cn.entity.ForgetPwd;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.InvitedMember;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.MemberProperties;
import com.flowring.cn.entity.MemberRole;
import com.flowring.cn.entity.Role;

public interface MemberService {

	/* Member */
	public int insertMember(Member member) throws Exception;

	public int updateMember(Member member);

	public Member getMemberById(int id);

	public Member getMemberByLoginId(String loginId);

	public Member getMemberByEmailToken(String emailToken);

	public List<Member> getAllMembers();

	public List<Member> getMemberByParentId(int parentId);
	
	public List<Member> getMemberListByRoleId(int roleId);
	
	public List<Member> getNoRoleMemberByParentId(int roleId, int parentId);

	public Member checkPassword(String loginId, String password);
	
	public List<Member> getMemberListByPropValue(String englishName, String value);
	
	public List<Member> getMemberListByPropValueAndGroupId(
			String englishName, String value, int groupId);
	
	/* MemberProperties */
	public int batchInsertMemberProperties(
			List<MemberProperties> memberPropertiesList);

	public int batchUpdateMemberProperties(
			List<MemberProperties> memberPropertiesList);
	
	public MemberProperties updateMemberPropValueById(int id, MemberProperties memProp);

	public MemberProperties getMemberPropertiesById(int id);

	public List<MemberProperties> getMemberPropertiesByMemberId(int memberId);

	public int batchDeleteMemberPropertiesList(
			List<MemberProperties> memberPropertiesList);

	/* InvitedMember */
	public int insertInvitedMember(InvitedMember invitedMember)
			throws Exception;

	public int updateInvitedMember(InvitedMember invitedMember);

	public InvitedMember getInvitedMemberById(int id);

	public InvitedMember getInvitedMemberByLoginId(String loginId);

	public List<InvitedMember> getInvitedMembersByMemberId(int memberId);

	public List<InvitedMember> getAllInvitedMembers();

	public InvitedMember checkInvitedMemberByToken(String token);

	public boolean deleteInvitedMemberByLoginId(String loginId);

	/* ForgetPwd */
	public int insertForgetPwd(ForgetPwd forgetPwd) throws Exception;

	public int updateForgetPwd(ForgetPwd forgetPwd);

	public ForgetPwd getForgetPwdById(int id);

	public ForgetPwd checkForgetPwdByToken(String token);

	public List<ForgetPwd> getForgetPwdByMemId(int memberId);

	/* Role */
	public int insertRole(Role role) throws Exception;

	public int updateRole(Role role);

	public Role getRoleById(int id);

	public List<Role> getRoleByName(String name);

	public List<Role> getRoleByCreator(int creator);
	
	public List<Role> getNoGroupRoleByCreator(int groupId, int creator);
	
	public boolean deleteRole(int roleId);
	

	/* MemberRole */
	public int insertMemberRole(MemberRole memberRole);

	public int batchInsertMemberRoleList(List<MemberRole> memberRoleList);

	public int batchDeleteMemberRoleList(List<MemberRole> memberRoleList);

	public List<MemberRole> getMemberRoleByMemberId(int memberId);

	public List<MemberRole> getMemberRoleByRoleId(int roleId);

	public int updateMemberEventPriority(int id, BigInteger eventPriority);

	public BigInteger getMaxEventPriority();
	
}
