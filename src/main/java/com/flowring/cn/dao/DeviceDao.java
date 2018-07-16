package com.flowring.cn.dao;

import java.util.Date;
import java.util.List;

import com.flowring.cn.entity.Device;

public interface DeviceDao {
	public List<Device> getAllDeviceList();

	public List<Device> getDeviceListByDeleted(boolean deleted);

	public Device getDeviceById(int id);

	public Device getDeviceByFullId(String fullId);

	public List<Device> getDeviceListByParentIdAndDeleted(int parentId,
			boolean deleted);

	public Device getDeviceByIdentifierAndCustomer(String identifier,
			String customer);

	// insert one return last_id
	public int insertDevice(Device device);

	// insert more than one return last_id
	public int insertDeviceList(List<Device> devicelist);

	public int updateDevice(Device device);

	// Delete device is change isDeleted to "false" from true.
	public int updateDeviceDeletedById(int id, boolean deleted);

	public int updateDeviceListDeleted(List<Device> deviceList, boolean deleted);

	// Device 不可刪除，故不實作
	public boolean deleteDeviceById(int id);
	
	public int updateDeviceLastRecordTimeById(int id, Date lastRecordTime);

}
