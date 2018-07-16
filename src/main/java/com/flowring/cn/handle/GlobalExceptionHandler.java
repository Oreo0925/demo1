package com.flowring.cn.handle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flowring.cn.pojo.SingleResponseObject;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public SingleResponseObject<?> handle(Exception e) {
            logger.error(e.getMessage());
            return SingleResponseObject.error(500, e.getMessage());
    }
	
}
