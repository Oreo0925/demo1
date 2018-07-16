package com.flowring.cn.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.Group;
import com.flowring.cn.service.DeviceMonitorService;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.util.InfluxDBUtils;
import com.flowring.fish.entity.FishRuleLog;
import com.flowring.fish.service.FishService;

@Service
public class DeviceMonitorServiceImpl implements DeviceMonitorService {
	
	private static Logger logger = LoggerFactory.getLogger(DeviceMonitorServiceImpl.class);
	
	private long DEVICE_CHECK_TIME = 360000; 
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	FishService fishService;

	@Scheduled(fixedRate = 360000)
	public void monitorDevice() throws InterruptedException {
		
		logger.info("Starting Monitor Device.....");
		
		// 讀取所有 Device ID 列表放入 HashMap
		List<Device> devices = deviceService.getAllDevice();
		
		for(Device device: devices) {
			
			long lastTime = InfluxDBUtils.getLastDataTime(device.getIdentifier());
			
			long now = System.currentTimeMillis();
			
			if ((now - lastTime) > DEVICE_CHECK_TIME) {
			
				List<Group> groups = device.getParentGroupList();

				int groupId = 0;

				if (groups.size() > 0) {
					Group group = groups.get(0);

					groupId = group.getId();
				}
				
				FishRuleLog fishRuleLog = new FishRuleLog();

				fishRuleLog.setDeviceId(device.getId());
				fishRuleLog.setGroupId(groupId);
				fishRuleLog.setAlarmType(3);
				fishRuleLog.setControlCode("00");
				fishRuleLog.setStatusCode("00");

				fishService.insertOrUpdateFishRuleLogAlarm(fishRuleLog);	
			}
			
		}
		
	

	}

}
