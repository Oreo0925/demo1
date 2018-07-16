package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.DeviceStaticProperties;

public interface DeviceStaticPropertiesDao {
	public List<DeviceStaticProperties> getAllDevStaticPropList();
	
	public DeviceStaticProperties getDevStaticPropListById(int id);
	
	public int insertDevStaticPropList(List<DeviceStaticProperties> staticPropList);
	
	public int updateDevStaticPropList(List<DeviceStaticProperties> staticPropList);
	
}
