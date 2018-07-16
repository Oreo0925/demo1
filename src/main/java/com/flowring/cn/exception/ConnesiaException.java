package com.flowring.cn.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flowring.cn.enums.ConnesiaEnum;

public class ConnesiaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(ConnesiaException.class);
	private Integer code;
	private String message = "";
	private boolean success;
	
	public ConnesiaException(ConnesiaEnum connesiaError) {
		this.code = connesiaError.getCode();
		this.message = connesiaError.getMessage();
		this.success = connesiaError.isSuccess();
		logger.debug(connesiaError.getMessage());
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
