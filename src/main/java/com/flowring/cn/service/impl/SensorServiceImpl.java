package com.flowring.cn.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowring.cn.dao.InfluxDao;
import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceTempPropData;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.SensorData;
import com.flowring.cn.entity.SensorServiceResponse;
import com.flowring.cn.enums.ConnectionType;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.service.GroupService;
import com.flowring.cn.service.SensorService;
import com.flowring.cn.util.JsonUtils;
import com.flowring.cn.util.ScriptUtils;
import com.flowring.fish.dao.FishRuleLogDao;
import com.flowring.fish.entity.FishRuleLog;
import com.flowring.fish.service.FishService;

@Service("SensorService")
public class SensorServiceImpl implements SensorService {

	private static Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);

	private static ScriptEngine engine;

	@Autowired
	private InfluxDao influxDao;

	@Autowired
	private FishService fishService;

	@Autowired
	private GroupService groupManagementService;

	@Autowired
	private DeviceService deviceManagementService;

	public SensorServiceImpl() {

		ScriptEngineManager engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("nashorn");

	}

	@Override
	public SensorServiceResponse saveData(String from, String ip, SensorData sensorData){

		sensorData.setIp(ip);
		sensorData.setConnectionType(ConnectionType.RESTFUL);

		if (sensorData.getFrom() == null || sensorData.getFrom().isEmpty()) {
			sensorData.setFrom(from);
		}

		logger.info(JsonUtils.toJson(sensorData));

		addData(sensorData);
		return new SensorServiceResponse();
	}

	public SensorServiceResponse saveData(String from, String ip, String customerDeviceId, String json){

		System.out.println("hello.....");

		SensorData sensorData = new SensorData();
		sensorData.setIp(ip);
		sensorData.setConnectionType(ConnectionType.RESTFUL);

		sensorData.setFrom(from);
		sensorData.setCustomerDeviceId(customerDeviceId);
		sensorData.setValueString(json);

		addData(sensorData);
		logger.info(JsonUtils.toJson(sensorData));

		return new SensorServiceResponse();
	}

	@Override
	public void saveDataMQTT(String topic, SensorData sensorData) {
		// TODO Auto-generated method stub

		try {

			sensorData.setConnectionType(ConnectionType.MQTT);
			String[] topics = topic.split("/");

			String from = topics[0];
			String deviceId = topics[1];

			if (sensorData.getCustomerDeviceId() == null || sensorData.getCustomerDeviceId().isEmpty()) {
				sensorData.setCustomerDeviceId(deviceId);
			}

			if (sensorData.getFrom() == null || sensorData.getFrom().isEmpty()) {
				sensorData.setFrom(from);
			}

			addData(sensorData);
			logger.info("topic==>" + topic + " " + JsonUtils.toJson(sensorData));
		} catch (Exception e) {
			logger.error("Error saveDataMQTT", e);
		}

	}

	@Override
	public void saveDataMQTT(String topic, String json) {
		try {
			String[] topics = topic.split("/");

			String from = topics[0];
			String deviceId = topics[1];

			SensorData sensorData = new SensorData();
			sensorData.setConnectionType(ConnectionType.MQTT);

			sensorData.setCustomerDeviceId(deviceId);
			sensorData.setFrom(from);
			sensorData.setValueString(json);

			addData(sensorData);
			logger.info("topic==>" + topic + " " + JsonUtils.toJson(sensorData));

		} catch (Exception e) {
			logger.error("Error saveDataMQTT", e);
		}

	}

	private void addData(SensorData sensorData){

		checkRule(sensorData);
		influxDao.addSensorData(sensorData);
	}

	private void checkRule(SensorData sensorData){

		String customerDeviceId = sensorData.getCustomerDeviceId();

		Device device = deviceManagementService.getDeviceByFullId(customerDeviceId);

		if (device != null) {
			sensorData.setDeviceId(device.getId());
			sensorData.setIdentify(device.getIdentifier());

			// 讀取 Device 下限1 下限2
			Map<String, DeviceTempPropData> propDataMap = device.getDeviceTempPropDataMap();
			DeviceTempPropData dtpAlertline1 = propDataMap.get("alertline1");
			DeviceTempPropData dtpAlertline2 = propDataMap.get("alertline2");

			if (dtpAlertline1 != null && dtpAlertline2 != null) {
				String alertline1 = dtpAlertline1.getValue();
				String alertline2 = dtpAlertline2.getValue();

				// 讀取 分區資料 並放入 fishRuleLog.setGroupId
				List<Group> groups = device.getParentGroupList();

				int groupId = 0;

				if (groups.size() > 0) {
					Group group = groups.get(0);

					groupId = group.getId();
				}

				Date now = Calendar.getInstance().getTime();

				Map<String, String> data = sensorData.getData();

				String rule2 = "dissolvedOxygen < " + alertline2;

				boolean result = ScriptUtils.eval(rule2, data);

				if (result == true) {

					addSensorData(sensorData, groupId, data, now, 2);
				} else {

					String rule1 = "dissolvedOxygen < " + alertline1;

					result = ScriptUtils.eval(rule1, data);

					System.out.println(rule1 + " eval Result:" + result);

					if (result == true) {

						addSensorData(sensorData, groupId, data, now, 1);
					}
				}

				System.out.println(rule2 + " eval Result:" + result);
			}

		}
	}

	private void addSensorData(SensorData sensorData, int groupId, Map<String, String> data, Date now, int ruleline) {
		float dissolvedOxygen = 0;
		float temperature = 0;
		String controlCode = "";
		String statusCode = "";

		if (data.get("dissolvedOxygen") != null)
			dissolvedOxygen = Float.parseFloat(data.get("dissolvedOxygen"));

		if (data.get("temperature") != null)
			temperature = Float.parseFloat(data.get("temperature"));

		if (data.get("controlCode") != null)
			controlCode = data.get("controlCode");

		if (data.get("statusCode") != null)
			statusCode = data.get("statusCode");

		FishRuleLog fishRuleLog = new FishRuleLog();

		fishRuleLog.setDeviceId(sensorData.getDeviceId());
		fishRuleLog.setGroupId(groupId);
		fishRuleLog.setAlarmType(ruleline);
		fishRuleLog.setDissolvedOxygen(dissolvedOxygen);
		fishRuleLog.setTemperature(temperature);
		fishRuleLog.setControlCode(controlCode);
		fishRuleLog.setStatusCode(statusCode);

//		List<Group> groupList = groupManagementService.getGroupListByDeviceId(sensorData.getDeviceId());
//		if (groupList.size() > 0) {
//			Group group = groupList.get(0);
//			fishRuleLog.setGroupId(group.getId());
//		}

		fishService.insertOrUpdateFishRuleLogAlarm(fishRuleLog);
		
	}

}
