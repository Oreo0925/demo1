package com.flowring.cn.pojo;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.util.Constants;

public class SingleResponseObject<Object> implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success = true;
	private String message = "OK";
	@JsonInclude(Include.NON_NULL)
	private Object data; // 
	private int total = 0;
	private Integer code = 200;	//error code
	
	public SingleResponseObject() {
		super();
	}
	public SingleResponseObject(Object data) {
		this.data = data;
	}
	
	public SingleResponseObject(Integer code, String message, boolean success) {
		this.code = code;
		this.message = message;
		this.success = success;
	}
	
	public static SingleResponseObject error(Integer code, String message) {
		return new SingleResponseObject(code, message, false);
	}
	
	public void setErrorMessage(Integer code, String message) {
		this.success = false;
		this.code = code;
		this.message = message;
	}
	
	public void setErrorMessage(ConnesiaException e){
        this.success = e.isSuccess();
        this.code = e.getCode();
        this.message = e.getMessage();
    }
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object objects) {
		if (objects != null) {
			total = 1;
			if(objects instanceof Collection){
				total = ((Collection) objects).size();
				if (total == 0) {
					this.message = Constants.NODATA;
				}
			}
		}
		this.data = objects;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
