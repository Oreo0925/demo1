package com.flowring.cn.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.flowring.cn.service.MessageService;

public abstract class BaseCommand implements Command {

	@Autowired
	protected MessageService messageService;
	
	protected int deviceId;
	
	protected String topic;
	
	@Override
	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getTopic() {
		return topic;
	}

	@Override
	public void setTopic(String topic) {
		this.topic = topic;
	}

}
