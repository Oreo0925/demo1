package com.flowring.cn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.flowring.cn.entity.DeviceRawData;
import com.flowring.cn.entity.InfluxOperate;
import com.flowring.cn.entity.InfluxQuerySeries;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.DeviceRawDataService;

@RestController
@Api(tags = {"感測資料"})
public class DeviceRawDataController {
	
	@Autowired
	private DeviceRawDataService deviceRawDataService;
	
	@ApiIgnore
	@ApiOperation(value="獲取某群組下所有設備資料&感測資料", notes="")
	@GetMapping("v1/group/{id}/devices/rawdata/influx")
	public SingleResponseObject<List<DeviceRawData>> getDevicesRawDataOfGroup(@ApiParam(value = "群組id", required = true) @PathVariable(value="id", required = true) Integer groupId, InfluxOperate influxOperate) {
		SingleResponseObject<List<DeviceRawData>> ero = new SingleResponseObject<List<DeviceRawData>>();
		try {
			List<DeviceRawData> deviceRawDataList = deviceRawDataService.getDevicesRawDataOfGroup(groupId, influxOperate);
			ero.setData(deviceRawDataList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
	
	@ApiIgnore
	@ApiOperation(value="獲取某設備的感測資料", notes="")
	@GetMapping("v1/device/{id}/rawdata/influx")
	public SingleResponseObject<InfluxQuerySeries> getDevicesRawDataInInflux(@ApiParam(value = "設備id", required = true) @PathVariable("id") Integer deviceId, InfluxOperate influxOperate) {
		SingleResponseObject<InfluxQuerySeries> ero = new SingleResponseObject<InfluxQuerySeries>();
		try {
			InfluxQuerySeries influxQuerySeries = deviceRawDataService.getDevicesRawData(deviceId, influxOperate);
			ero.setData(influxQuerySeries);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
	
	@ApiOperation(value="獲取某設備的感測資料", notes="")
	@GetMapping("v1/device/{id}/rawdata")
	public SingleResponseObject<InfluxQuerySeries> getDevicesRawData(@ApiParam(value = "設備id", required = true, example= "0") @PathVariable("id") Integer deviceId, @ApiParam(value = "開始時間 (Long)", example = "0") @RequestParam(value="startTime", required = false) long startTime, @ApiParam(value = "結束時間 (Long)", example = "0") @RequestParam(value="endTime", required = false) long endTime) {
		SingleResponseObject<InfluxQuerySeries> ero = new SingleResponseObject<InfluxQuerySeries>();
		try {
			InfluxOperate influxOperate = new InfluxOperate();
			influxOperate.setConditions("time >= " + startTime + "ms and time < " + endTime + "ms");
			InfluxQuerySeries influxQuerySeries = deviceRawDataService.getDevicesRawData(deviceId, influxOperate);
			ero.setData(influxQuerySeries);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
}
