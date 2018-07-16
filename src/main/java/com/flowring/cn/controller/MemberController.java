package com.flowring.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.entity.ForgetPwd;
import com.flowring.cn.entity.InvitedMember;
import com.flowring.cn.entity.Member;
import com.flowring.cn.entity.MemberProperties;
import com.flowring.cn.entity.MemberRole;
import com.flowring.cn.entity.Role;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.MemberService;

@RestController
@RequestMapping("/")
public class MemberController {

	@Autowired
	private MemberService memberManagementService;

	@PostMapping(value = "user")
	public SingleResponseObject<Member> insertMember(@RequestBody Member member)
			throws Exception {
		SingleResponseObject<Member> ero = new SingleResponseObject<Member>();
		try {
			memberManagementService.insertMember(member);
			ero.setData(member);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "user/{id}")
	public SingleResponseObject<Member> updateMember(
			@PathVariable("id") int id, @RequestBody Member member) {
		SingleResponseObject<Member> ero = new SingleResponseObject<Member>();
		try {
			memberManagementService.updateMember(member);
			ero.setData(member);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "user/{id}")
	public SingleResponseObject<Member> getMemberById(@PathVariable("id") int id) {
		SingleResponseObject<Member> ero = new SingleResponseObject<Member>();
		try {
			Member member = memberManagementService.getMemberById(id);
			ero.setData(member);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "users")
	public SingleResponseObject<List<Member>> getAllMembers() {
		SingleResponseObject<List<Member>> ero = new SingleResponseObject<List<Member>>();
		try {
			List<Member> memberList = memberManagementService.getAllMembers();
			ero.setData(memberList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "user/loginId/{loginId}")
	public SingleResponseObject<Member> getMemberByLoginId(
			@PathVariable("loginId") String loginId) {
		SingleResponseObject<Member> ero = new SingleResponseObject<Member>();
		try {
			Member member = memberManagementService.getMemberByLoginId(loginId
					.trim());
			ero.setData(member);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "users/parentId/{parentId}")
	public SingleResponseObject<List<Member>> getMemberByParentId(
			@PathVariable("parentId") int parentId) {
		SingleResponseObject<List<Member>> ero = new SingleResponseObject<List<Member>>();
		try {
			List<Member> memberList = memberManagementService
					.getMemberByParentId(parentId);
			ero.setData(memberList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	@GetMapping(value = "users/roleId/{roleId}")
	public SingleResponseObject<List<Member>> getMemberListByRoleId(
			@PathVariable("roleId") int roleId) {
		SingleResponseObject<List<Member>> ero = new SingleResponseObject<List<Member>>();
		try {
			List<Member> memberList = memberManagementService
					.getMemberListByRoleId(roleId);
			ero.setData(memberList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	@GetMapping(value = "users/roleId/{roleId}/parentId/{parentId}")
	public SingleResponseObject<List<Member>> getNoRoleMemberByParentId(
			@PathVariable("roleId") int roleId,
			@PathVariable("parentId") int parentId) {
		SingleResponseObject<List<Member>> ero = new SingleResponseObject<List<Member>>();
		try {
			List<Member> memberList = memberManagementService
					.getNoRoleMemberByParentId(roleId, parentId);
			ero.setData(memberList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/**
	 * Change to Spring Security in the future.
	 * 
	 * @param member
	 * @return
	 */
	@PostMapping(value = "doLogin")
	public SingleResponseObject<Member> doLogin(@RequestBody Member member) {
		SingleResponseObject<Member> ero = new SingleResponseObject<Member>();
		try {
			Member newMember = memberManagementService.checkPassword(member
					.getLoginId().trim(), member.getPassword().trim());
			ero.setData(newMember);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "verify/register/{emailToken}")
	public SingleResponseObject<Member> verifyRegister(
			@PathVariable("emailToken") String emailToken) {
		SingleResponseObject<Member> ero = new SingleResponseObject<Member>();
		try {
			Member member = memberManagementService
					.getMemberByEmailToken(emailToken);
			ero.setData(member);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增會員動態屬性List
	 */
	@PostMapping(value = "/user/properties")
	public SingleResponseObject<List<MemberProperties>> batchInsertMemberProperties(
			@RequestBody List<MemberProperties> memberPropertiesList) {
		SingleResponseObject<List<MemberProperties>> ero = new SingleResponseObject<List<MemberProperties>>();
		try {
			int total = memberManagementService
					.batchInsertMemberProperties(memberPropertiesList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		}
		return ero;
	}

	/*
	 * 修改會員動態屬性List
	 */
	@PutMapping(value = "/user/properties")
	public SingleResponseObject<List<MemberProperties>> batchUpdateMemberProperties(
			@RequestBody List<MemberProperties> memberPropertiesList) {
		SingleResponseObject<List<MemberProperties>> ero = new SingleResponseObject<List<MemberProperties>>();
		try {
			int total = memberManagementService
					.batchUpdateMemberProperties(memberPropertiesList);
			ero.setData(memberPropertiesList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		}
		return ero;
	}

	/*
	 * 修改會員動態屬性的Value欄位 ById
	 */
	@PutMapping(value = "/user/property/{id}/value")
	public SingleResponseObject<MemberProperties> updateMemberPropValueById(
			@PathVariable("id") int id, @RequestBody MemberProperties memberProp) {
		SingleResponseObject<MemberProperties> ero = new SingleResponseObject<MemberProperties>();
		try {
			memberProp = memberManagementService.updateMemberPropValueById(id,
					memberProp);
			ero.setData(memberProp);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		}
		return ero;
	}

	/*
	 * 查詢會員動態屬性
	 */
	@GetMapping(value = "/user/property/{id}")
	public SingleResponseObject<MemberProperties> getMemberPropertiesById(
			@PathVariable("id") int id) {
		SingleResponseObject<MemberProperties> ero = new SingleResponseObject<MemberProperties>();
		try {
			MemberProperties memberProp = memberManagementService
					.getMemberPropertiesById(id);
			ero.setData(memberProp);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		}
		return ero;
	}

	/*
	 * 查詢會員動態屬性 By memberId
	 */
	@GetMapping(value = "/user/properties/memberId/{memberId}")
	public SingleResponseObject<List<MemberProperties>> getMemberPropertiesByMemberId(
			@PathVariable("memberId") int memberId) {
		SingleResponseObject<List<MemberProperties>> ero = new SingleResponseObject<List<MemberProperties>>();
		try {
			List<MemberProperties> memberPropertiesList = memberManagementService
					.getMemberPropertiesByMemberId(memberId);
			ero.setData(memberPropertiesList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		}
		return ero;
	}

	@DeleteMapping(value = "/user/properties")
	public SingleResponseObject<List<MemberProperties>> batchDeleteMemberPropertiesList(
			@RequestBody List<MemberProperties> memberPropertiesList) {
		SingleResponseObject<List<MemberProperties>> ero = new SingleResponseObject<List<MemberProperties>>();
		try {
			int total = memberManagementService
					.batchDeleteMemberPropertiesList(memberPropertiesList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		}
		return ero;
	}

	@PostMapping(value = "user/invite")
	public SingleResponseObject<InvitedMember> insertInvitedMember(
			@RequestBody InvitedMember invitedMember) throws Exception {
		SingleResponseObject<InvitedMember> ero = new SingleResponseObject<InvitedMember>();
		try {
			memberManagementService.insertInvitedMember(invitedMember);
			ero.setData(invitedMember);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "verify/invite/{emailToken}")
	public SingleResponseObject<InvitedMember> verifyInvitedMember(
			@PathVariable("emailToken") String emailToken) {
		SingleResponseObject<InvitedMember> ero = new SingleResponseObject<InvitedMember>();
		try {
			InvitedMember invitedMember = memberManagementService
					.checkInvitedMemberByToken(emailToken);
			ero.setData(invitedMember);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "user/invite")
	public SingleResponseObject<InvitedMember> updateInvitedMember(
			@RequestBody InvitedMember invitedMember) {
		SingleResponseObject<InvitedMember> ero = new SingleResponseObject<InvitedMember>();
		try {
			memberManagementService.updateInvitedMember(invitedMember);
			ero.setData(invitedMember);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "/invite/{memberId}")
	public SingleResponseObject<List<InvitedMember>> getInvitedMembersByMemberId(
			@PathVariable("memberId") int memberId) {
		SingleResponseObject<List<InvitedMember>> ero = new SingleResponseObject<List<InvitedMember>>();
		try {
			List<InvitedMember> invitedMemberList = memberManagementService
					.getInvitedMembersByMemberId(memberId);
			ero.setData(invitedMemberList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "/user/invite/{loginId}")
	public SingleResponseObject<InvitedMember> deleteInvitedMemberByLoginId(
			@PathVariable("loginId") String loginId) {
		SingleResponseObject<InvitedMember> ero = new SingleResponseObject<InvitedMember>();
		try {
			memberManagementService.deleteInvitedMemberByLoginId(loginId);
			ero.setMessage("ok");
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "/forget/password")
	public SingleResponseObject<ForgetPwd> insertForgetPwd(
			@RequestBody ForgetPwd forgetPwd) throws Exception {
		SingleResponseObject<ForgetPwd> ero = new SingleResponseObject<ForgetPwd>();
		try {
			memberManagementService.insertForgetPwd(forgetPwd);
			ero.setData(forgetPwd);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "verify/password/{verificationCode}")
	public SingleResponseObject<ForgetPwd> verifyChangePwd(
			@PathVariable("verificationCode") String verificationCode) {
		SingleResponseObject<ForgetPwd> ero = new SingleResponseObject<ForgetPwd>();
		try {
			ForgetPwd forgetPwd = memberManagementService
					.checkForgetPwdByToken(verificationCode);
			ero.setData(forgetPwd);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "forget/password")
	public SingleResponseObject<ForgetPwd> updateForgetPwd(
			@RequestBody ForgetPwd forgetPwd) {
		SingleResponseObject<ForgetPwd> ero = new SingleResponseObject<ForgetPwd>();
		try {
			memberManagementService.updateForgetPwd(forgetPwd);
			ero.setData(forgetPwd);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "forget/password/{memberId}")
	public SingleResponseObject<List<ForgetPwd>> getForgetPwdByMemId(
			@PathVariable("memberId") int memberId) {
		SingleResponseObject<List<ForgetPwd>> ero = new SingleResponseObject<List<ForgetPwd>>();
		try {
			List<ForgetPwd> forgetPwdList = memberManagementService
					.getForgetPwdByMemId(memberId);
			ero.setData(forgetPwdList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "role")
	public SingleResponseObject<Role> insertRole(@RequestBody Role role)
			throws Exception {
		SingleResponseObject<Role> ero = new SingleResponseObject<Role>();
		try {
			memberManagementService.insertRole(role);
			ero.setData(role);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PutMapping(value = "role")
	public SingleResponseObject<Role> updateRole(@RequestBody Role role)
			throws Exception {
		SingleResponseObject<Role> ero = new SingleResponseObject<Role>();
		try {
			memberManagementService.updateRole(role);
			ero.setData(role);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	@DeleteMapping(value = "role/{id}")
	public SingleResponseObject<Role> deleteRole(@PathVariable("id") int roleId) {
		SingleResponseObject<Role> ero = new SingleResponseObject<Role>();
		try {
			memberManagementService.deleteRole(roleId);
			ero.setMessage("ok");
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/{id}")
	public SingleResponseObject<Role> getRoleById(@PathVariable("id") int id) {
		SingleResponseObject<Role> ero = new SingleResponseObject<Role>();
		try {
			Role role = memberManagementService.getRoleById(id);
			ero.setData(role);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/name/{name}")
	public SingleResponseObject<List<Role>> getRoleByName(
			@PathVariable("name") String name) {
		SingleResponseObject<List<Role>> ero = new SingleResponseObject<List<Role>>();
		try {
			List<Role> role = memberManagementService.getRoleByName(name);
			ero.setData(role);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/creator/{creator}")
	public SingleResponseObject<List<Role>> getRoleByCreator(
			@PathVariable("creator") int creator) {
		SingleResponseObject<List<Role>> ero = new SingleResponseObject<List<Role>>();
		try {
			List<Role> role = memberManagementService.getRoleByCreator(creator);
			ero.setData(role);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	@GetMapping(value = "role/groupId/{groupId}/creator/{creator}")
	public SingleResponseObject<List<Role>> getNoGroupRoleByCreator(
			@PathVariable("groupId") int groupId,
			@PathVariable("creator") int creator) {
		SingleResponseObject<List<Role>> ero = new SingleResponseObject<List<Role>>();
		try {
			List<Role> roleList = memberManagementService
					.getNoGroupRoleByCreator(groupId, creator);
			ero.setData(roleList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "user/role")
	public SingleResponseObject<MemberRole> insertMemberRole(
			@RequestBody MemberRole memberRole) throws Exception {
		SingleResponseObject<MemberRole> ero = new SingleResponseObject<MemberRole>();
		try {
			memberManagementService.insertMemberRole(memberRole);
			ero.setData(memberRole);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@PostMapping(value = "user/roles")
	public SingleResponseObject<List<MemberRole>> batchInsertMemberRoleList(
			@RequestBody List<MemberRole> memberRoleList) throws Exception {
		SingleResponseObject<List<MemberRole>> ero = new SingleResponseObject<List<MemberRole>>();
		try {
			memberManagementService.batchInsertMemberRoleList(memberRoleList);
			ero.setData(memberRoleList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@DeleteMapping(value = "user/roles")
	public SingleResponseObject<List<MemberRole>> batchDeleteMemberRoleList(
			@RequestBody List<MemberRole> memberRoleList) {
		SingleResponseObject<List<MemberRole>> ero = new SingleResponseObject<List<MemberRole>>();
		try {
			int total = memberManagementService
					.batchDeleteMemberRoleList(memberRoleList);
			ero.setTotal(total);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "user/{memberId}/role")
	public SingleResponseObject<List<MemberRole>> getMemberRoleByMemberId(
			@PathVariable("memberId") int memberId) {
		SingleResponseObject<List<MemberRole>> ero = new SingleResponseObject<List<MemberRole>>();
		try {
			List<MemberRole> memberRoleList = memberManagementService
					.getMemberRoleByMemberId(memberId);
			ero.setData(memberRoleList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	@GetMapping(value = "role/{roleId}/user")
	public SingleResponseObject<List<MemberRole>> getMemberRoleByRoleId(
			@PathVariable("roleId") int roleId) {
		SingleResponseObject<List<MemberRole>> ero = new SingleResponseObject<List<MemberRole>>();
		try {
			List<MemberRole> memberRoleList = memberManagementService
					.getMemberRoleByRoleId(roleId);
			ero.setData(memberRoleList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

}
