package com.flowring.cn.dao;

import java.util.List;

import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceGroup;
import com.flowring.cn.entity.DeviceProperties;


public interface DeviceGroupDao {
    public List<DeviceGroup> getAllDeviceGroup();
    public List<DeviceGroup> getDeviceGroupByDeviceId(int deviceId);
    public List<DeviceGroup> getDeviceGroupByGroupId(int groupId);
    public int insertDeviceGroup(DeviceGroup deviceGroup);
    public int insertDeviceGroup(List<DeviceGroup> deviceGroupList);
    public int deleteDeviceGroupList(List<DeviceGroup> deviceGroupList);
}
