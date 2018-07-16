package com.flowring.fish.entity;

import java.util.List;

import com.flowring.cn.entity.Device;

public class FishGroup {
	
	private int id;

	private String name;
	
	private List<FishDevice> childDeviceList;
	
	public FishGroup() {
		super();
	}
	
	public FishGroup(int id, String name, List<FishDevice> childDeviceList) {
		this.id = id;
		this.name = name;
		this.childDeviceList = childDeviceList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FishDevice> getChildDeviceList() {
		return childDeviceList;
	}

	public void setChildDeviceList(List<FishDevice> childDeviceList) {
		this.childDeviceList = childDeviceList;
	}
	
	public static class FishDevice {
		
		private int id;
		
		private String name;
		
		private int typeId;
		
		private boolean isScheduled = false;
		
		private float dissolvedOxygen;

		private float temperature;
		
		private float ph;

		private int alarmType;
		
		private boolean enabled;

		private boolean automatic;

		private boolean isWorked;
		
		public FishDevice() {
			super();
		}

		public FishDevice(int id, String name, int typeId, boolean isScheduled, float dissolvedOxygen, float temperature, float ph, 
				int alarmType, boolean enabled, boolean automatic, boolean isWorked) {
			this.id = id;
			this.name = name;
			this.typeId = typeId;
			this.isScheduled = isScheduled;
			this.dissolvedOxygen = dissolvedOxygen;
			this.temperature = temperature;
			this.ph = ph;
			this.alarmType = alarmType;
			this.enabled = enabled;
			this.automatic = automatic;
			this.isWorked = isWorked;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getTypeId() {
			return typeId;
		}

		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}

		public boolean isScheduled() {
			return isScheduled;
		}

		public void setScheduled(boolean isScheduled) {
			this.isScheduled = isScheduled;
		}

		public float getDissolvedOxygen() {
			return dissolvedOxygen;
		}

		public void setDissolvedOxygen(float dissolvedOxygen) {
			this.dissolvedOxygen = dissolvedOxygen;
		}

		public float getTemperature() {
			return temperature;
		}

		public void setTemperature(float temperature) {
			this.temperature = temperature;
		}

		public float getPh() {
			return ph;
		}

		public void setPh(float ph) {
			this.ph = ph;
		}

		public int getAlarmType() {
			return alarmType;
		}

		public void setAlarmType(int alarmType) {
			this.alarmType = alarmType;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public boolean isAutomatic() {
			return automatic;
		}

		public void setAutomatic(boolean automatic) {
			this.automatic = automatic;
		}

		public boolean isWorked() {
			return isWorked;
		}

		public void setWorked(boolean isWorked) {
			this.isWorked = isWorked;
		}

	}
	
}
