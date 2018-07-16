package com.flowring.cn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceGroup;
import com.flowring.cn.entity.DeviceRawData;
import com.flowring.cn.entity.DeviceTempPropData;
import com.flowring.cn.entity.InfluxOperate;
import com.flowring.cn.entity.InfluxQuerySeries;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.DeviceRawDataService;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.service.GroupService;
import com.flowring.cn.util.InfluxDBUtils;

@Service
public class DeviceRawDataServiceImpl implements DeviceRawDataService {
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private GroupService groupService;

	@Override
	public List<DeviceRawData> getDevicesRawDataOfGroup(int groupId, InfluxOperate influxOperate) {
		List<DeviceGroup> deviceGroupList = groupService.getDeviceGroupByGroupsId(groupId);
		List<DeviceRawData> deviceRawDataList = new ArrayList<DeviceRawData>();
		InfluxQuerySeries influxQuerySeries;
		DeviceTempPropData deviceTempPropData;
		InfluxOperate influxOperateClone;
		try {
			for (DeviceGroup deviceGroup : deviceGroupList) {
				influxOperateClone = new InfluxOperate();
				BeanUtils.copyProperties(influxOperateClone, influxOperate);
				
				Device device = deviceService.getDeviceById(deviceGroup.getDeviceId());
				if (StringUtils.isBlank(influxOperateClone.getDb())) {
					deviceTempPropData = device.getDeviceTempPropDataMap().get("influxDB");
					if (deviceTempPropData != null) {
						influxOperateClone.setDb(deviceTempPropData.getValue());
					}
				}
				if (StringUtils.isBlank(influxOperateClone.getTable())) {
					deviceTempPropData = device.getDeviceTempPropDataMap().get("influxTable");
					if (deviceTempPropData != null) {
						influxOperateClone.setTable(deviceTempPropData.getValue());
					}
				}
				
				influxQuerySeries = InfluxDBUtils.getDataOfIdentify(device.getIdentifier(), influxOperateClone);
				deviceRawDataList.add(new DeviceRawData(device, influxQuerySeries));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceRawDataList;
	}

	@Override
	public InfluxQuerySeries getDevicesRawData(int deviceId, InfluxOperate influxOperate) {
		Device device = deviceService.getDeviceById(deviceId);
		DeviceTempPropData deviceTempPropData;
		InfluxQuerySeries influxQuerySeries;
		if (StringUtils.isBlank(influxOperate.getDb())) {
			deviceTempPropData = device.getDeviceTempPropDataMap().get("influxDB");
			if (deviceTempPropData != null) {
				influxOperate.setDb(deviceTempPropData.getValue());
			}
		}
		if (StringUtils.isBlank(influxOperate.getTable())) {
			deviceTempPropData = device.getDeviceTempPropDataMap().get("influxTable");
			if (deviceTempPropData != null) {
				influxOperate.setTable(deviceTempPropData.getValue());
			}
		}
		
		influxQuerySeries = InfluxDBUtils.getDataOfIdentify(device.getIdentifier(), influxOperate);
		if (influxQuerySeries == null) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		
		return influxQuerySeries;
	}
}
