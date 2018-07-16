package com.flowring.cn.service.impl;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flowring.cn.service.MessageCallBack;
import com.flowring.cn.service.MessageService;
import com.flowring.cn.util.Constants;

@Service
public class MqttMessageServiceImpl implements MessageService {

	private static Logger logger = LoggerFactory.getLogger(MqttMessageServiceImpl.class);

	@Override
	public void publish(String topic, String message) {

		int qos = 0;
		String broker = Constants.MQTT_BROKER;
		String clientId = UUID.randomUUID().toString();
		MemoryPersistence persistence = new MemoryPersistence();

		MqttClient sampleClient = null;

		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			logger.info("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			logger.info("Connected");
			logger.info("Publishing topic: " + topic);
			logger.info("Publishing message: " + message);
			MqttMessage mqttMessage = new MqttMessage(message.getBytes());
			mqttMessage.setQos(qos);
			sampleClient.publish(topic, mqttMessage);
			logger.info("Message published");

		} catch (MqttException me) {
			logger.info("reason " + me.getReasonCode());
			logger.info("msg " + me.getMessage());
			logger.info("loc " + me.getLocalizedMessage());
			logger.info("cause " + me.getCause());
			logger.info("excep " + me);
			me.printStackTrace();
		} finally {

			if (sampleClient != null) {
				try {
					sampleClient.disconnect();
					logger.info("Disconnected");
				} catch (Exception e) {
				}
			}
		}

	}

	@Override
	public void subscribe(String topic, MessageCallBack messageCallBack) {
		// TODO Auto-generated method stub

	}

}
