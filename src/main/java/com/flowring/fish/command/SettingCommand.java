package com.flowring.fish.command;

import com.flowring.cn.command.BaseCommand;

public class SettingCommand extends BaseCommand {

	private int oxyLimitUp;

	private int oxyLimitDown1;

	private int oxyLimitDown2;
	
	private int salinity;

	@Override
	public void execute() {
		
		String oxyLimitUpHex = String.format("%02X", oxyLimitUp & 0xFF);
		String oxyLimitDown1Hex = String.format("%02X", oxyLimitDown1 & 0xFF);
		String oxyLimitDown2Hex = String.format("%02X", oxyLimitDown2 & 0xFF);
		String otherProperties = "0000000000000000";
		String salinityHex = String.format("%02X", salinity & 0xFF);
		int checkSum = 0 + 0x15 + oxyLimitUp + oxyLimitDown1 + oxyLimitDown2 + salinity;
		String checkSumHex = String.format("%02X", checkSum);
		
		String VOLTAGE_COMMAND_STR = "AAAA0015" +  oxyLimitUpHex + oxyLimitDown1Hex + oxyLimitDown2Hex + otherProperties + salinityHex + checkSumHex;

		messageService.publish(topic, VOLTAGE_COMMAND_STR);

	}

	public float getOxyLimitUp() {
		return oxyLimitUp;
	}

	public void setOxyLimitUp(int oxyLimitUp) {
		this.oxyLimitUp = oxyLimitUp;
	}

	public float getOxyLimitDown1() {
		return oxyLimitDown1;
	}

	public void setOxyLimitDown1(int oxyLimitDown1) {
		this.oxyLimitDown1 = oxyLimitDown1;
	}

	public float getOxyLimitDown2() {
		return oxyLimitDown2;
	}

	public void setOxyLimitDown2(int oxyLimitDown2) {
		this.oxyLimitDown2 = oxyLimitDown2;
	}

	public int getSalinity() {
		return salinity;
	}

	public void setSalinity(int salinity) {
		this.salinity = salinity;
	}
	

}
