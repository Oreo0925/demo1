package com.flowring.cn.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.flowring.cn.dao.ForgetPwdDao;
import com.flowring.cn.dao.InvitedMemberDao;
import com.flowring.cn.dao.MemberDao;
import com.flowring.cn.dao.MemberPropertiesDao;
import com.flowring.cn.dao.MemberRoleDao;
import com.flowring.cn.dao.RoleDao;
import com.flowring.cn.dao.RoleGroupDao;
import com.flowring.cn.entity.ForgetPwd;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.InvitedMember;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.MemberProperties;
import com.flowring.cn.entity.MemberRole;
import com.flowring.cn.entity.Role;
import com.flowring.cn.entity.RoleGroup;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.enums.SequenceConstantEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.GroupService;
import com.flowring.cn.service.MailSenderService;
import com.flowring.cn.service.MemberService;
import com.flowring.cn.service.MenuService;
import com.flowring.cn.service.SequenceService;
import com.flowring.cn.util.Constants;

@Service
public class MemberServiceImpl implements MemberService {
	private static Logger logger = LoggerFactory
			.getLogger(MemberServiceImpl.class);
	private static String regex = "([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\\.[0-9a-zA-Z]{1,4})";
	private static Pattern pattern = Pattern.compile(regex);
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberPropertiesDao memPropDao;

	@Autowired
	private InvitedMemberDao invitedMemberDao;

	@Autowired
	private ForgetPwdDao forgetPwdDao;

	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private MemberRoleDao memberRoleDao;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private RoleGroupDao roleGroupDao;
	

	@Override
	public int insertMember(Member member) throws Exception {
		if (member == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		isEmailRight(member.getEmail());
		if (!isLoginIdExist(member.getLoginId().trim())) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_REPEAT_ERROR);
		}
		if (StringUtils.isBlank(member.getPassword())) {
			throw new ConnesiaException(ConnesiaEnum.PASSWORD_ERROR);
		}
		// Verification by Email.
		String emailToken = mailSenderService.getVerificationToken();
		member.setFullId(sequenceService.getNextSequenceWithPrefix(
				SequenceConstantEnum.MEM.toString(), 16));
		member.setPassword(DigestUtils.md5DigestAsHex(member.getPassword()
				.getBytes()));
		member.setEnable(false);
		member.setEmailToken(emailToken);
		int count = memberDao.insertMember(member);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		mailSenderService.sendVerifyRegisterMail(member, emailToken);

		return count;
	}

	@Override
	public int updateMember(Member member) {
		if (member == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		isEmailRight(member.getEmail());
		Member memberFromDB = getMemberById(member.getId());// 先確認資料是否存在
		if (!loginIdExist(member.getLoginId().trim(), memberFromDB)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_REPEAT_ERROR);
		}
		if (member.getEmailToken() == null) {
			member.setEmailToken(memberFromDB.getEmailToken());
		}
		if (StringUtils.isBlank(member.getPassword())) {
			throw new ConnesiaException(ConnesiaEnum.PASSWORD_ERROR);
		}
		member.setPassword(DigestUtils.md5DigestAsHex(member.getPassword()
				.getBytes()));
		int count = memberDao.updateMember(member);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public Member getMemberById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		Member member = new Member();
		try {
			member = memberDao.getMemberById(id);
			member = getMainRoleByMemberId(member);
			member = getGroupByMemberId(member);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		return member;
	}

	private Member getGroupByMemberId(Member member) {
		Map<Integer, Role> roleMap =  getRoleByMemberId(member.getId());
		List<Group> groupList = new ArrayList<Group>();
		Map<Integer, Group> groupMap = groupService.getAllGroupsByKeyIsId();
		roleMap.forEach((key, value) -> {
			 List<RoleGroup> roleGroupList = menuService.getRoleGroupByRoleId(value.getId());
			 roleGroupList.forEach(roleGroup -> {
				 Group group = groupMap.get(roleGroup.getGroupId());
				 groupList.add(group);
			 });
		});
		member.setGroupList(groupList);
		return member;
	}

	@Override
	public Member getMemberByLoginId(String loginId) {
		if (StringUtils.isBlank(loginId)) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		Member member = new Member();
		try {
			member = memberDao.getMemberByLoginId(loginId);
			member = getMainRoleByMemberId(member);
			member = getGroupByMemberId(member);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		return member;
	}

	@Override
	public Member getMemberByEmailToken(String emailToken) {
		if (StringUtils.isBlank(emailToken)) {
			throw new ConnesiaException(ConnesiaEnum.TOKEN_ERROR);
		}
		Member member = new Member();
		try {
			member = memberDao.getMemberByEmailToken(emailToken);
			member.setEnable(true);
			member.setEmailToken("success");
			memberDao.updateMemberNoPassword(member);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		return member;
	}

	@Override
	public List<Member> getAllMembers() {
		List<Member> memberList = new ArrayList<Member>();
		memberList = memberDao.getAllMembers();
		return memberList;
	}

	@Override
	public List<Member> getMemberByParentId(int parentId) {
		List<Member> memberList = new ArrayList<Member>();
		try {
			memberList = memberDao.getMemberByParentId(parentId);
			memberList = setMemberRoles(memberList);
			memberList = setGroupByMemberList(memberList);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memberList;
	}
	
	private List<Member> setGroupByMemberList(List<Member> memberList) {
		List<Member> newMemberList = new ArrayList<Member>();
		memberList.forEach(m -> {
			Member member = getGroupByMemberId(m);
			newMemberList.add(member);
		});
		return newMemberList;
	}
	
	private List<Member> setMemberRoles(List<Member> memberList) {
		List<Member> newMemberList = new ArrayList<Member>();
		for (int i = 0; i < memberList.size(); i++) {
			Member member = getMainRoleByMemberId(memberList.get(i));
			newMemberList.add(member);
		}
		return newMemberList;
	}

	@Override
	public List<Member> getMemberListByRoleId(int roleId) {
		List<Member> memberList = new ArrayList<Member>();
		List<MemberRole> memberRoleList = getMemberRoleByRoleId(roleId);
		for (int i = 0; i < memberRoleList.size(); i++) {
			memberList.add(getMemberById(memberRoleList.get(i).getMemberId())); 
		}
		return memberList;
	}
	
	@Override
	public List<Member> getNoRoleMemberByParentId(int roleId, int parentId) {
		List<Member> newMemberList = new ArrayList<Member>();
		List<Member> memberList = getMemberByParentId(parentId);
		for (int i = 0; i < memberList.size(); i++) {
			List<MemberRole> memberRoleList = getMemberRoleByMemberId(memberList.get(i).getId());
			Map<Integer, MemberRole> memberRoleMap = new HashMap<Integer, MemberRole>();
			for (int j = 0; j < memberRoleList.size(); j++) {
				memberRoleMap.put(memberRoleList.get(j).getRoleId(), memberRoleList.get(j));
			}
			if (!memberRoleMap.containsKey(roleId)) {
				newMemberList.add(memberList.get(i));
			}
		}
		return newMemberList;
	}

	@Override
	public List<Member> getMemberListByPropValueAndGroupId(String englishName,
			String value, int groupId) {
		if (StringUtils.isBlank(englishName) || groupId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<Member> memberList = new ArrayList<Member>();
		try {
			memberList = memberDao.getMemberListByPropValueAndGroupId(
					englishName, value, groupId);
			for (Member member : memberList) {
				List<MemberProperties> memPropList = memPropDao
						.getMemberPropertiesByMemberId(member.getId());
				member.setPropList(memPropList);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memberList;
	}

	@Override
	public List<Member> getMemberListByPropValue(String englishName,
			String value) {
		if (StringUtils.isBlank(englishName)) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<Member> memberList = new ArrayList<Member>();
		try {
			memberList = memberDao.getMemberListByPropValue(englishName, value);
			for (Member member : memberList) {
				List<MemberProperties> memPropList = memPropDao
						.getMemberPropertiesByMemberId(member.getId());
				member.setPropList(memPropList);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memberList;
	}

	@Override
	public Member checkPassword(String loginId, String password) {
		if (StringUtils.isBlank(loginId) || StringUtils.isBlank(password)) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Member member = this.getMemberByLoginId(loginId);
		if (!member.getEmailToken().equals(Constants.EMAILAUTH)) {
			throw new ConnesiaException(ConnesiaEnum.EMAIL_AUTH_ERROR);
		}
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!md5Password.equals(member.getPassword())) {
			throw new ConnesiaException(ConnesiaEnum.LOGINIDPASSWORD_ERROR);
		}
		memberDao.updateMemberNoPassword(member);// 更新最後上線時間。

		member = getMainRoleByMemberId(member);
		member = getGroupByMemberId(member);
		return member;
	}

	private Member getMainRoleByMemberId(Member member) {
		Map<Integer, Role> roleMap = getRoleByMemberId(member.getId());
		member.setRoleAllName(getRoleNames(roleMap));
		member.setMainRole(roleMap.get(0));
		roleMap.remove(0);
		
		Iterator<Entry<Integer, Role>> iter = roleMap.entrySet().iterator();
		List<Role> roleList = new ArrayList<Role>();
		
		while (iter.hasNext()) {
			roleList.add(iter.next().getValue());
		}
		member.setRoleList(roleList);
		return member;
	}
	
    private String getRoleNames(Map<Integer, Role> roleMap) {
    	StringBuffer roleAllNames = new StringBuffer();
    	int count = 0;
		Iterator<Entry<Integer, Role>> iterator = roleMap.entrySet().iterator();
		while (iterator.hasNext()) {
			count ++;
			roleAllNames.append(iterator.next().getValue().getName());
			if (count < roleMap.size()) {
				roleAllNames.append(", ");
			} else {
				roleAllNames.append(".");
			}
		}
		return roleAllNames.toString();
	}

	private Map<Integer, Role> getRoleByMemberId(int id) {
    	if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);	
		}
		Map<Integer, Role> roleMap = new HashMap<Integer, Role>();
		try {
			roleMap = roleDao.getRoleByMemberId(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		return roleMap;
	}

	@Override
	public int insertInvitedMember(InvitedMember invitedMember)
			throws Exception {
		if (invitedMember == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (!isInviteLoginIdExist(invitedMember.getLoginId())) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_REPEAT_ERROR);
		}
		isEmailRight(invitedMember.getLoginId());

		Member member = getMemberById(invitedMember.getMemberId());
		String emailToken = mailSenderService.getVerificationToken();
		invitedMember.setEmailToken(emailToken);
		invitedMember.setDeleted(false);
		int count = invitedMemberDao.insertInvitedMember(invitedMember);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		mailSenderService.sendInvitedMemberMail(member, emailToken,
				invitedMember.getLoginId());
		return count;
	}

	private boolean isEmailRight(String loginId) {
		Matcher matcher = pattern.matcher(loginId);
		if (!matcher.find()) {
			throw new ConnesiaException(ConnesiaEnum.EMAIL_ERROR);
		}
		return true;
	}

	@Override
	public InvitedMember checkInvitedMemberByToken(String token) {
		if (StringUtils.isBlank(token)) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		InvitedMember invitedMember = new InvitedMember();
		try {
			invitedMember = invitedMemberDao
					.getInvitedMemberByEmailToken(token);
			invitedMember.setEmailToken("success");
			updateInvitedMember(invitedMember);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.INVITED_MEMBER_NOT_EXIST);
		}
		return invitedMember;
	}

	private boolean isInviteLoginIdExist(String loginId) {
		if (StringUtils.isBlank(loginId)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_ERROR);
		}
		List<InvitedMember> invitedMemberList = getAllInvitedMembers();
		for (int i = 0; i < invitedMemberList.size(); i++) {
			String invitedMemberLoginId = invitedMemberList.get(i).getLoginId();
			boolean isDeleted = invitedMemberList.get(i).isDeleted();
			if (loginId.equals(invitedMemberLoginId) && isDeleted == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int updateInvitedMember(InvitedMember invitedMember) {
		if (getMemberById(invitedMember.getMemberId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		InvitedMember invitedMemberFromDB = getInvitedMemberById(invitedMember
				.getId());// 先確認資料是否存在
		if (!invitedLoginIdExist(invitedMember.getLoginId(),
				invitedMemberFromDB)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_REPEAT_ERROR);
		}
		if (invitedMember.getEmailToken() == null) {
			invitedMember.setEmailToken(invitedMemberFromDB.getEmailToken());
		}
		int count = invitedMemberDao.updateInvitedMember(invitedMember);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	private boolean invitedLoginIdExist(String loginId,
			InvitedMember invitedMember) {
		boolean flag = true;
		String loginIdOfDB = invitedMember.getLoginId();
		if (!loginId.equals(loginIdOfDB)) {// 若欲修改的 longId 與資料庫的 loginId
											// 不符，則須先判斷新的 loginId 是否已存在。
			InvitedMember checkInvitedMemberIsExist = null;
			try {
				checkInvitedMemberIsExist = invitedMemberDao
						.getInvitedMemberByLoginId(loginId);
				if (checkInvitedMemberIsExist != null
						&& checkInvitedMemberIsExist.isDeleted() == false) {
					flag = false;
				}
			} catch (Exception e) {
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public InvitedMember getInvitedMemberById(int id) {
		InvitedMember invitedMember = new InvitedMember();
		try {
			invitedMember = invitedMemberDao.getInvitedMemberById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return invitedMember;
	}

	@Override
	public InvitedMember getInvitedMemberByLoginId(String loginId) {
		if (StringUtils.isBlank(loginId)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_ERROR);
		}
		InvitedMember invitedMember = new InvitedMember();
		try {
			invitedMember = invitedMemberDao.getInvitedMemberByLoginId(loginId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return invitedMember;
	}

	@Override
	public List<InvitedMember> getInvitedMembersByMemberId(int memberId) {
		if (memberId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<InvitedMember> invitedMemberList = new ArrayList<InvitedMember>();
		try {
			invitedMemberList = invitedMemberDao
					.getInvitedMembersByMemberId(memberId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return invitedMemberList;
	}

	@Override
	public List<InvitedMember> getAllInvitedMembers() {
		List<InvitedMember> invitedMemberList = new ArrayList<InvitedMember>();
		invitedMemberList = invitedMemberDao.getAllInvitedMembers();
		return invitedMemberList;
	}

	@Override
	public boolean deleteInvitedMemberByLoginId(String loginId) {
		if (StringUtils.isBlank(loginId)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_ERROR);
		}
		boolean result = invitedMemberDao.deleteInvitedMemberByLoginId(loginId);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public int insertForgetPwd(ForgetPwd forgetPwd) throws Exception {
		if (forgetPwd == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Member member = getMemberById(forgetPwd.getMemberId());
		String verificationCode = mailSenderService.getVerificationToken();
		forgetPwd.setVerificationCode(verificationCode);
		forgetPwd.setStatus("wait");
		forgetPwd.setValidTime(30 * 60 * 1000); // 30 minutes
		int count = forgetPwdDao.insertForgetPwd(forgetPwd);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		mailSenderService.sendVerifyChangePwdMail(member, verificationCode);

		return count;
	}

	@Override
	public int updateForgetPwd(ForgetPwd forgetPwd) {
		if (forgetPwd == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int count = forgetPwdDao.updateForgetPwd(forgetPwd);
		return count;
	}

	@Override
	public ForgetPwd getForgetPwdById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		ForgetPwd forgetPwd = new ForgetPwd();
		try {
			forgetPwd = forgetPwdDao.getForgetPwdById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return forgetPwd;
	}

	@Override
	public ForgetPwd checkForgetPwdByToken(String token) {
		if (StringUtils.isBlank(token)) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		ForgetPwd forgetPwd = new ForgetPwd();
		try {
			forgetPwd = forgetPwdDao.getForgetPwdByVerificationCode(token);
			boolean isCodeValid = isCodeValid(forgetPwd);
			if (isCodeValid) {
				forgetPwd.setVerificationCode("success");
				forgetPwd.setStatus("success");
				updateForgetPwd(forgetPwd);
			} else {
				throw new ConnesiaException(ConnesiaEnum.TOKEN_EXPIRED_ERROR);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return forgetPwd;
	}

	private boolean isCodeValid(ForgetPwd forgetPwd) {
		boolean isValid = true;
		Date date = null;
		long limitTime = forgetPwd.getValidTime();
		try {
			date = sdf.parse(forgetPwd.getChangeTime());
		} catch (ParseException e) {
			throw new ConnesiaException(ConnesiaEnum.DATE_FORMAT_ERROR);
		}
		limitTime = date.getTime() + limitTime;
		if (limitTime < System.currentTimeMillis()) {
			logger.debug("email 驗證碼已失效");
			isValid = false;
		}
		return isValid;
	}

	@Override
	public List<ForgetPwd> getForgetPwdByMemId(int memberId) {
		if (memberId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<ForgetPwd> forgetPwdList = new ArrayList<ForgetPwd>();
		try {
			forgetPwdList = forgetPwdDao.getForgetPwdByMemId(memberId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return forgetPwdList;
	}

	private boolean loginIdExist(String loginId, Member member) {
		if (StringUtils.isBlank(loginId)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_ERROR);
		}
		boolean flag = true;
		String loginIdFromDB = member.getLoginId();
		if (!loginId.equals(loginIdFromDB)) {
			Member checkMemberIsExist = null;
			try {
				checkMemberIsExist = memberDao.getMemberByLoginId(loginId);
				if (checkMemberIsExist != null) {
					flag = false;
				}
			} catch (Exception e) {
				flag = true;
			}
		}
		return flag;
	}

	private boolean isLoginIdExist(String loginId) {
		if (StringUtils.isBlank(loginId)) {
			throw new ConnesiaException(ConnesiaEnum.LOGINID_ERROR);
		}
		List<Member> memberList = getAllMembers();
		for (int i = 0; i < memberList.size(); i++) {
			String memberLoginId = memberList.get(i).getLoginId();
			if (loginId.equals(memberLoginId)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int insertRole(Role role) throws Exception {
		if (role == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getMemberById(role.getCreater()) == null) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		if (StringUtils.isBlank(role.getName())) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EMPTY);
		}
		int count = roleDao.insertRole(role);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public int updateRole(Role role) {
		if (role == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getMemberById(role.getCreater()) == null) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		if (role.getId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		if (StringUtils.isBlank(role.getName())) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EMPTY);
		}
		int count = roleDao.updateRole(role);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	public Role getRoleById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Role role = new Role();
		try {
			role = roleDao.getRoleById(id);
//			role = getGroupByRoleId(role);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		return role;
	}

	private Role getGroupByRoleId(Role role) {
		List<Group> groupList = new ArrayList<Group>();
		List<RoleGroup> roleGroupList;
		try {
			roleGroupList = roleGroupDao.getRoleGroupByRoleId(role.getId());
			for (int i = 0; i < roleGroupList.size(); i++) {
				Group group = groupService.getGroupById(roleGroupList.get(i).getGroupId());
				groupList.add(group);
			}
		} catch (EmptyResultDataAccessException e) {
			roleGroupList = new ArrayList<RoleGroup>();
		} 
		role.setGroupList(groupList);
		return role;
	}

	@Override
	public List<Role> getRoleByName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EMPTY);
		}
		List<Role> roleList = new ArrayList<Role>();
		try {
			roleList = roleDao.getRoleByName(name);
//			roleList = setGroupByRoleList(roleList);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		return roleList;
	}

	private List<Role> setGroupByRoleList(List<Role> roleList) {
		List<Role> newRoleList = new ArrayList<Role>();
		for (int i = 0; i < roleList.size(); i++) {
			Role Role = getGroupByRoleId(roleList.get(i));
			newRoleList.add(Role);
		}
		return newRoleList;
	}

	@Override
	public List<Role> getRoleByCreator(int creator) {
		if (creator == 0) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		List<Role> roleList = new ArrayList<Role>();
		try {
			roleList = roleDao.getRoleByCreator(creator);
//			roleList = setGroupByRoleList(roleList);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		return roleList;
	}
	
	@Override
	public List<Role> getNoGroupRoleByCreator(int groupId, int creator) {
		List<Role> newRoleList = new ArrayList<Role>();
		List<Role> roleList = getRoleByCreator(creator);
		roleList.forEach(role -> {
			List<RoleGroup> roleGroupList = roleGroupDao.getRoleGroupByRoleId(role.getId());
			Map<Integer, RoleGroup> roleGroupMap = new HashMap<Integer, RoleGroup>();
			roleGroupList.forEach(roleGroup -> {
				roleGroupMap.put(roleGroup.getGroupId(), roleGroup);
			});
			if (!roleGroupMap.containsKey(groupId)) {
				newRoleList.add(role);
			}
		});
//		for (int i = 0; i < roleList.size(); i++) {
//			List<RoleGroup> roleGroupList = roleGroupDao.getRoleGroupByRoleId(roleList.get(i).getId());
//			Map<Integer, RoleGroup> roleGroupMap = new HashMap<Integer, RoleGroup>();
//			for (int j = 0; j < roleGroupList.size(); j++) {
//				roleGroupMap.put(roleGroupList.get(j).getGroupId(), roleGroupList.get(j));
//			}
//			if (!roleGroupMap.containsKey(groupId)) {
//				newRoleList.add(roleList.get(i));
//			}
//		}
		return newRoleList;
	}
	
	@Override
	public boolean deleteRole(int roleId) {
		if (roleId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getMemberRoleByRoleId(roleId).size() != 0) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_CANNOT_DELETE);
		}
		if (roleGroupDao.getRoleGroupByRoleId(roleId).size() != 0) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_CANNOT_DELETE);
		}
		boolean result = roleDao.deleteRole(roleId);
		if (!result) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return result;
	}

	@Override
	public int insertMemberRole(MemberRole memberRole) {
		if (memberRole.getMemberId() == 0 || memberRole.getRoleId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		if (getMemberById(memberRole.getMemberId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.MEMBER_NOT_EXIST);
		}
		if (getRoleById(memberRole.getRoleId()) == null) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_NOT_EXIST);
		}
		boolean isRoleExist = isRoleExist(memberRole);
		if (!isRoleExist) {
			throw new ConnesiaException(ConnesiaEnum.ROLE_AUTHORITY_EXIST);
		}
		//Check the High priority exists.
		Map<Integer, Role> roleMap = getRoleByMemberId(memberRole.getMemberId());
		if(roleMap.size() != 0) {
			Role theHighpriorityRole = roleMap.get(0);
			if (theHighpriorityRole.getPriority() == getRoleById(memberRole.getRoleId()).getPriority()) {
				throw new ConnesiaException(ConnesiaEnum.HIGH_AUTHORITY_REPEAT);
			}
		}
		int count = memberRoleDao.insertMemberRole(memberRole);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return count;
	}

	private boolean isRoleExist(MemberRole memberRole) {
		List<MemberRole> memberRoleList = getMemberRoleByMemberId(memberRole
				.getMemberId());
		for (int i = 0; i < memberRoleList.size(); i++) {
			if (memberRoleList.get(i).getRoleId() == memberRole.getRoleId()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<MemberRole> getMemberRoleByMemberId(int memberId) {
		if (memberId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<MemberRole> memberRoleList = new ArrayList<MemberRole>();
		try {
			memberRoleList = memberRoleDao.getMemberRoleByMemberId(memberId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memberRoleList;
	}

	@Override
	public List<MemberRole> getMemberRoleByRoleId(int roleId) {
		if (roleId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<MemberRole> memberRoleList = new ArrayList<MemberRole>();
		try {
			memberRoleList = memberRoleDao.getMemberRoleByRoleId(roleId);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memberRoleList;
	}

	@Override
	@Transactional
	public int batchInsertMemberRoleList(List<MemberRole> memberRoleList) {
		if (memberRoleList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = 0;
		try {
			total = memberRoleDao.insertMemberRoleList(memberRoleList);
		} catch (DuplicateKeyException e) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		if (memberRoleList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchDeleteMemberRoleList(List<MemberRole> memberRoleList) {
		if (memberRoleList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = memberRoleDao.deleteMemberRoleList(memberRoleList);
		if (memberRoleList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int batchInsertMemberProperties(
			List<MemberProperties> memberPropertiesList) {
		if (memberPropertiesList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = memPropDao.insertMemberProperties(memberPropertiesList);
		if (total != memberPropertiesList.size()) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int batchUpdateMemberProperties(
			List<MemberProperties> memberPropertiesList) {
		if (memberPropertiesList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = memPropDao.updateMemberProperties(memberPropertiesList);
		if (total != memberPropertiesList.size()) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public MemberProperties updateMemberPropValueById(int id,
			MemberProperties memProp) {
		if (id == 0 || memProp == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		memPropDao.updateMemberPropValueById(id, memProp.getValue());
		try {
			memProp = memPropDao.getMemberPropertiesById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memProp;
	}

	@Override
	public MemberProperties getMemberPropertiesById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		MemberProperties memberProp = new MemberProperties();
		try {
			memberProp = memPropDao.getMemberPropertiesById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return memberProp;
	}

	@Override
	public List<MemberProperties> getMemberPropertiesByMemberId(int memberId) {
		if (memberId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<MemberProperties> memberPropList = memPropDao
				.getMemberPropertiesByMemberId(memberId);
		return memberPropList;
	}

	@Override
	public int batchDeleteMemberPropertiesList(
			List<MemberProperties> memberPropertiesList) {
		int total = memPropDao.deleteMemberPropertiesList(memberPropertiesList);
		if (total != memberPropertiesList.size()) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}
	
	/*
	 * 修改 member eventPriority 欄位(慶魚塘客製欄位)
	 */
	@Override
	public int updateMemberEventPriority(int id, BigInteger eventPriority) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = memberDao.updateMemberEventPriority(id, eventPriority);
		return total;
	}
	
	/*
	 * 取得 member 中 eventPriority 中最大的值
	 */
	@Override
	public BigInteger getMaxEventPriority() {
		return memberDao.getMaxEventPriority();
	}
	
}
