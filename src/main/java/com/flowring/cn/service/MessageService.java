package com.flowring.cn.service;

public interface MessageService {
	
	public void publish(String topic, String message);
	
	public void subscribe(String topic, MessageCallBack messageCallBack);

}
