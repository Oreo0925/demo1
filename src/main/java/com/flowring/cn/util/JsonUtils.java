package com.flowring.cn.util;

import com.google.gson.Gson;

public class JsonUtils {
	
	public static String toJson(Object obj) {
		return new Gson().toJson(obj);
		
	}

}
