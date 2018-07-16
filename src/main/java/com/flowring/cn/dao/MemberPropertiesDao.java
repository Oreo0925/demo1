package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.MemberProperties;

public interface MemberPropertiesDao {
	
	public int insertMemberProperties (List<MemberProperties> memberPropertiesList);
	
	public int updateMemberProperties(List<MemberProperties> memberPropertiesList);
	
	public int updateMemberPropValueById(int id, String value);
	
	public MemberProperties getMemberPropertiesById(int id);
	
	public List<MemberProperties> getMemberPropertiesByMemberId(int memberId);
	
	public int deleteMemberPropertiesList(List<MemberProperties> memberPropertiesList);

}
