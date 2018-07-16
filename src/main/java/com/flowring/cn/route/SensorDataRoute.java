package com.flowring.cn.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.flowring.cn.entity.SensorData;
import com.flowring.cn.util.Constants;

@Component
public class SensorDataRoute extends RouteBuilder {

	@Override
	public void configure() {
		
		// Restful Sensor Data Receiver
		restConfiguration()
			.component("netty4-http")
			.bindingMode(RestBindingMode.json)
			.port(8081);
		
		
		rest("/data/{from}/{deviceId}").consumes("application/json")
			.post()
			.to("bean:SensorService?method=saveData(${header.from}, ${header.CamelNettyRemoteAddress}, ${header.deviceId},${body})");
		
		rest("/data/{from}").consumes("application/json")
		.post().type(SensorData.class)
		.to("bean:SensorService?method=saveData(${header.from},${header.CamelNettyRemoteAddress}, ${body})");

		// MQTT Sensor Data Receiver	
		/*from("mqtt:connesia?host=" + Constants.MQTT_BROKER + "&subscribeTopicName=#/#/In")
		.log("topic: ${in.header.CamelMQTTSubscribeTopic}" + " Recieved : " + body().convertToString())
		.choice()
			.when(header("CamelMQTTSubscribeTopic").contains("data")).transform(body().convertToString())
			.to("bean:SensorService?method=saveDataMQTT(${in.header.CamelMQTTSubscribeTopic}, ${body})")
		.otherwise()
			.transform(body().convertToString()).unmarshal().json(JsonLibrary.Jackson, SensorData.class)
			.to("bean:SensorService?method=saveDataMQTT(${in.header.CamelMQTTSubscribeTopic}, ${body})")
		.end();*/
		

	}
}
