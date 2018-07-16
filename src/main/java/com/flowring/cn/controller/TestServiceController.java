package com.flowring.cn.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.entity.Member;
import com.flowring.cn.enums.SequenceConstantEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.SequenceService;

@RestController
@RequestMapping("/test")
public class TestServiceController {

	@Autowired
	private SequenceService sequenceService;
	
	@GetMapping(value = "/data")
	public SingleResponseObject<Member> getMemberById1() {
		SingleResponseObject ero = new SingleResponseObject();
		try {
			long id = sequenceService.getNextSequenceWithNoPrefixAndDate(SequenceConstantEnum.MEM.toString());
			ero.setData(id);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		} 
		return ero;
	}
	
	@GetMapping(value = "/data1")
	public SingleResponseObject<Member> getMemberById2() {
		SingleResponseObject ero = new SingleResponseObject();
		try {
			String id = sequenceService.getNextSequenceWithPrefix(SequenceConstantEnum.MEM.toString(), 16);
			ero.setData(id);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		} 
		return ero;
	}
	
	@GetMapping(value = "/data2")
	public SingleResponseObject<Member> getMemberById3() {
		SingleResponseObject ero = new SingleResponseObject();
		try {
			String id = sequenceService.getNextSequenceWithPreFixAndDate(SequenceConstantEnum.MEM.toString(), 16);
			ero.setData(id);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(500, e.getMessage());
		} 
		return ero;
	}
	
}
