package com.flowring.cn.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.entity.Request;
import com.flowring.cn.entity.Response;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.fish.entity.FishRuleLog;
import com.flowring.fish.service.FishService;

@RestController
@RequestMapping("/yuntongxun")
public class YuntongxunController {
	
	private static Logger logger = LoggerFactory.getLogger(YuntongxunController.class);
	
	@Autowired
	private FishService fishService;
	
	@PostMapping("/resp")
	public Response serviceResponse(
			@RequestBody Request request) {
		
		Response response = new Response();
		try {
			logger.info("yuntongxun request:" + request);
			Calendar cal = Calendar.getInstance();
			FishRuleLog fishRuleLog = fishService.getFishRuleLogByCallSid(request.getCallSid());
			fishRuleLog.setCallDuration(request.getDuration());
			fishRuleLog.setCallStatus(request.getState());
			
			if(request.getState().equals("1")) {
				fishRuleLog.setClosed(1);
				fishRuleLog.setResultStatus("call success:" + request.getNumber());
			}
				
			fishService.updateFishRuleLogCallData(fishRuleLog);
			
			response.setStatuscode("000000");
			
			logger.info("Yuntongxun:" + request);
			
			
		} catch (Exception ce) {
			logger.error("serviceResponse error:", ce);
		}
		
		return response;
	}

}
