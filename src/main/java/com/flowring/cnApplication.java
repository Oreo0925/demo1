package com.flowring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.flowring.cn.util.InfluxDBUtils;


@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class cnApplication {

	public static void main(String[] args) {
		SpringApplication.run(cnApplication.class, args);
		
		
//		long lastTime = InfluxDBUtils.getLastDataTime("000004");
//		System.out.println("lastTime:" + lastTime);
	}
}
