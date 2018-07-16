package com.flowring.cn.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.flowring.cn.dao.impl.InfluxDaoImpl;

public class YuntongxunUtils {

	private static Logger logger = LoggerFactory.getLogger(YuntongxunUtils.class);

	public static String sendTTS(String phoneNumber, String message) {
		String callSid = "";

		try {
			Map<String, Object> result = null;

			CCPRestSDK restAPI = new CCPRestSDK();

			restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
			restAPI.setAccount("8a216da863aa05670163b53ea07608d3", "8bcbebba503549bb960399475e8299bc");// 初始化主帐号和主帐号TOKEN
			restAPI.setAppId("8a216da863aa05670163b54358c208dd");// 初始化应用ID
			// type=1，则播放默认语音文件,0是自定义语音文件

			String responseURL = "http://139.224.237.0:8888/yuntongxun/resp";
			// result = restAPI.landingCall("18601621926", "", "马博士，您好。1号鱼塘 溶氧值为，十点五，毫克每升
			// ，请注意。", "", "2", "", "", "-500", "20", "300", "0", "0");
			
			logger.info("sendTTS:{} => {}", phoneNumber, message);
			result = restAPI.landingCall(phoneNumber, "", message, "", "2", responseURL, "", "-500", "20", "300", "0",
					"0");

			logger.info("SDKTestLandingCall result=" + result);

			if ("000000".equals(result.get("statusCode"))) {
				// 正常返回输出data包体信息（map）
				Map<String, Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for (String key : keySet) {
					Object object = data.get(key);
					logger.info(key + " = " + object);
				}

				callSid = (String) ((Map) ((Map) result.get("data")).get("LandingCall")).get("callSid");
				
			} else {
				// 异常返回输出错误码和错误信息
				logger.info("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			}
		} catch (Exception e) {
			logger.error("Error sendTTS...", e);
		}
		

		return callSid;
	}

	public static void main(String[] args) {

		String callSid = sendTTS("18912388017", "马博士，您好。1号鱼塘 溶氧值为，十点五，毫克每升 ，请注意。");

		logger.info("callSid=" + callSid);

	}

}
