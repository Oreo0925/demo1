package com.flowring.cn.command;

public interface Command {
	
	public void execute();
	public void setTopic(String topic);
	public int getDeviceId();
	
}
