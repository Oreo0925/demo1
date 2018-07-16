package com.flowring.fish.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flowring.cn.command.BaseCommand;

public class ControlCommand extends BaseCommand {

	private static Logger logger = LoggerFactory.getLogger(ControlCommand.class);

	private int deviceAddress;

	private int powerControl;

	@Override
	public void execute() {

		String deviceAddressHex = String.format("%02X", deviceAddress & 0xFF);
		String funHex = "11";
		String powerControlHex = String.format("%02X", powerControl & 0xFF);

		int checkSum = deviceAddress + 0x11 + powerControl;

		String checkSumHex = String.format("%02X", checkSum);

		String VOLTAGE_COMMAND_STR = "AAAA" + deviceAddressHex + funHex + powerControlHex + checkSumHex;

		messageService.publish(topic, VOLTAGE_COMMAND_STR);
		
	}

	public int getDeviceAddress() {
		return deviceAddress;
	}

	public void setDeviceAddress(int deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	public int getPowerControl() {
		return powerControl;
	}

	public void setPowerControl(int powerControl) {
		this.powerControl = powerControl;
	}

}
