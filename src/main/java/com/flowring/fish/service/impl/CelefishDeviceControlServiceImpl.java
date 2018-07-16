package com.flowring.fish.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.flowring.cn.command.Command;
import com.flowring.cn.entity.Device;
import com.flowring.cn.service.DeviceControlService;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.util.ApplicationContextUtils;

@Service
public class CelefishDeviceControlServiceImpl implements DeviceControlService {

	@Autowired
	private DeviceService deviceService;

	@Override
	public void sendCommand(Command command) {

		AutowireCapableBeanFactory autowireCapableBeanFactory = ApplicationContextUtils.getApplicationContext()
				.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.autowireBean(command);

		int deviceId = command.getDeviceId();
		Device device = deviceService.getDeviceById(deviceId);

		String topic = "celefish/" + device.getIdentifier() + "/out";

		command.setTopic(topic);
		command.execute();

	}

}
