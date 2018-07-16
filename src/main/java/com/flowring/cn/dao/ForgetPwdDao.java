package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.ForgetPwd;

public interface ForgetPwdDao {
	
	public int insertForgetPwd(ForgetPwd forgetPwd);
	
	public int updateForgetPwd(ForgetPwd forgetPwd);
	
	public ForgetPwd getForgetPwdById(int id);
	
	public ForgetPwd getForgetPwdByVerificationCode(String verificationCode);
	
	public List<ForgetPwd> getForgetPwdByMemId(int memberId);
}
