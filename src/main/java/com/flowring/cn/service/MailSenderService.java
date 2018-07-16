package com.flowring.cn.service;

import com.flowring.cn.entity.Member;

public interface MailSenderService {
	
	public String getVerificationToken();
	public void sendVerifyRegisterMail(Member member, String verificationCode) throws Exception;
	public void sendVerifyChangePwdMail(Member member, String verificationCode) throws Exception;
	public void sendInvitedMemberMail(Member member, String verificationCode, String loginId) throws Exception;

}
