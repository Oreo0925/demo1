package com.flowring.cn.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceRawData {
	
	@JsonInclude(Include.NON_NULL)
	private Device device;
	
	@JsonProperty("rawData")
	private InfluxQuerySeries influxQuerySeries;
	
	public DeviceRawData(InfluxQuerySeries influxQuerySeries) {
		this(null, influxQuerySeries);
	}

	public DeviceRawData(Device device, InfluxQuerySeries influxQuerySeries) {
		this.device = device;
		this.influxQuerySeries = influxQuerySeries;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public InfluxQuerySeries getInfluxQuerySeries() {
		return influxQuerySeries;
	}

	public void setInfluxQuerySeries(InfluxQuerySeries influxQuerySeries) {
		this.influxQuerySeries = influxQuerySeries;
	}
}
