package com.flowring.cn.service.impl;

import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.flowring.cn.entity.Member;
import com.flowring.cn.service.MailSenderService;
import com.flowring.cn.util.Constants;

@Service
public class MailSenderServiceImpl implements MailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${server.port}")
	private String serverPort;
	
	@Value("${server.ip}")
	private String contextPath;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Value("${velocity.path}")
	private String velocityPath;
	
	@Override
	public void sendVerifyRegisterMail(Member member, String token) throws Exception {
		sendMail(member, token, "register.vm", Constants.REGISTER_MAIL, Constants.VERIFY_REGISTER);
	}
	
	@Override
	public void sendVerifyChangePwdMail(Member member, String token) throws Exception {
		sendMail(member, token, "changePwd.vm", Constants.CHANGE_PASSWORD_MAIL, Constants.VERIFY_CHANGE_PASSWD);
	}
	
	@Override
	public void sendInvitedMemberMail(Member member, String token, String loginId) throws Exception {
		member.setEmail(loginId);//將邀請會員的email設給 member，以利寄送邀請註冊信。
		sendMail(member, token, "invitedMember.vm", Constants.INVITED_MEMBER_MAIL, Constants.VERIFY_NON_REGISTER);
	}
	
	private void sendMail(Member member, String token, String templateName, String subject, String url)
			throws MessagingException, UnknownHostException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

		Template template = getVelocityEngine().getTemplate(templateName, "UTF-8");
		StringWriter writer = new StringWriter();
		String link = getCorrectWebUrl(token, url);

		// Set email information.
		messageHelper.setFrom(sender);
		messageHelper.setTo(member.getEmail());
		messageHelper.setSubject(subject);

		// Set email content.
		VelocityContext context = new VelocityContext();
		context.put("username", member.getName());
		context.put("link", link);
		template.merge(context, writer);

		String text = writer.toString();
		messageHelper.setText(text, true);
		mailSender.send(mimeMessage);
	}

	/**
	 * Get Velocity Engine.
	 * 
	 * @return
	 */
	private VelocityEngine getVelocityEngine() {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, velocityPath);
		velocityEngine.init();
		return velocityEngine;
	}

	/**
	 * Generate URL:http://127.0.0.1:8080/
	 * @return
	 * @throws UnknownHostException
	 */
	private String getCorrectWebUrl(String token, String pathUrl) throws UnknownHostException {
		String portNumStr = "";
		int portNum = Integer.valueOf(serverPort);
		if (portNum != 80 && portNum != 443) {
			portNumStr = ":" + portNum;
		}
		String url = "http:/" + contextPath + portNumStr + pathUrl + token;
		return url;
	}

	@Override
	public String getVerificationToken() {
		String token = "";
		for (int i = 0; i <= 5; i++) {
			Random ran = new Random();
			token = token + Integer.toString(ran.nextInt(10));
		}
		token = DigestUtils.md5DigestAsHex(token.getBytes());
		return token;
	}

}
