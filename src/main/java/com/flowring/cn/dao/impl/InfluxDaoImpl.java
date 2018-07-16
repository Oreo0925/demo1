package com.flowring.cn.dao.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.flowring.cn.dao.InfluxDao;
import com.flowring.cn.entity.SensorData;
import com.flowring.cn.util.InfluxDBUtils;

@Repository
public class InfluxDaoImpl implements InfluxDao {
	private static Logger logger = LoggerFactory
			.getLogger(InfluxDaoImpl.class);
	
	@Override
	public boolean addSensorData(SensorData sensorData) {
		boolean result = false;
		// TODO Auto-generated method stub

		try {
			String tableName = sensorData.getFrom();
			String dataEntry = "";
			StringBuffer sb = new StringBuffer();
			sb.append("identify=");
			sb.append(sensorData.getIdentify());
			sb.append(" ");

			Map<String, String> data = sensorData.getData();

			if (data != null) {
				Set<String> keys = data.keySet();
				Iterator<String> i = keys.iterator();
				
				while(i.hasNext()) {
					String key = i.next();
					String value = data.get(key);
					
					sb.append(key);
					sb.append("=");
					sb.append(value);
					
					if(i.hasNext()) {
						sb.append(",");
					}
				}

				dataEntry = sb.toString();
			}
			
			InfluxDBUtils.addData(tableName, dataEntry);

			result = true;
		} catch (Exception e) {
			logger.error("Error addSensorData:", e);
		}
		
		
		return result;
	}

}
