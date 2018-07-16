package com.flowring.cn.dao;

import java.math.BigInteger;
import java.util.List;

import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.RoleGroup;

public interface MemberDao {

	public int insertMember(Member member);

	public int updateMember(Member member);

	public int updateMemberNoPassword(Member member);

	public Member getMemberById(int id);

	public Member getMemberByLoginId(String loginId);

	public Member getMemberByEmailToken(String emailToken);

	public List<Member> getAllMembers();

	public List<Member> getMemberByParentId(int parentId);

	public List<Member> getMemberListByPropValue(String englishName,
			String value);

	public List<Member> getMemberListByPropValueAndGroupId(String englishName,
			String value, int groupId);

	public int updateMemberEventPriority(int id, BigInteger eventPriority);

	public BigInteger getMaxEventPriority();

}
