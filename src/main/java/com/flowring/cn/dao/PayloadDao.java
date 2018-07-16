package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Payload;

public interface PayloadDao {
	
	public int insertPayload(Payload payload);

	public int updatePayload(Payload payload);
	
	public boolean deletePayloadById(int id);
	
	public Payload getPayloadById(int id);
	
	public List<Payload> getPayloadByDeviceTypeId(int deviceTypeId);
}
