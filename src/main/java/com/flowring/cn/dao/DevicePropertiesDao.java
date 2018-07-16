package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceProperties;


public interface DevicePropertiesDao {
    public List<DeviceProperties> getAllDeviceProperties();
    public List<DeviceProperties> getDevicePropertiesByDeviceId(int deviceId);
    public DeviceProperties getDevicePropertiesById(int id);
    public int insertDeviceProperties(List<DeviceProperties> devicePropertiesList);
    public int updateDeviceProperties(List<DeviceProperties> devicePropertiesList);
    public boolean deleteDevicePropertiesList(List<DeviceProperties> devicePropertiesList);
}
