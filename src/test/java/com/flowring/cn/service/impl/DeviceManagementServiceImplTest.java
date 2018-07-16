package com.flowring.cn.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.flowring.cn.dao.DeviceDao;
import com.flowring.cn.entity.Device;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeviceManagementServiceImplTest {
	
	@Autowired 
	private DeviceDao deviceDao;
	
//	@Test
//	public void testGetAllDevice() {
//		int expected = 4;
//		List<Device> deviceList = deviceDao.getAllDevice();
//		int actual = deviceList.size();
//		assertEquals(expected, actual);
//	}

//	@Test
//	public void testGetDeviceById() {
//		int expected = 1;
//		Device device = deviceDao.getDeviceById(1);
//		int actual = device == null ? 0:1;
//		assertEquals(expected, actual);
//	}

//	@Test
//	public void testGetDeviceNotIsDeletedByParentId() {
//		int expected = 2;
//		List<Device> deviceList = deviceDao.getDeviceNotIsDeletedByParentId(0);
//		int actual = deviceList.size();
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void testGetDeviceNotIsDeletedByGroupId() {
//		int expected = 2;
//		List<Device> deviceList = deviceDao.getDeviceNotIsDeletedByGroupId(0);
//		int actual = deviceList.size();
//		assertEquals(expected, actual);
//	}

//	@Test
//	public void testInsertDevice() {
//		Device device = new Device();
//		device.setParentId(0);
//		device.setGroupId(0);
//		device.setDeleted(false);
//		int expected = 9;
//		int actual = deviceDao.insertDevice(device);
//		assertEquals(expected, actual);
//	}

//	@Test
//	public void testUpdateDevice() {
//		Device device = new Device();
//		device.setId(1);
//		device.setParentId(0);
//		device.setGroupId(0);
//		device.setDeleted(true);
//		int expected = 1;
//		int actual = deviceDao.updateDevice(device);
//		assertEquals(expected, actual);
//	}
//
//	@Test
//	public void testUpdateIsDeletedForDeleteDevice() {
//		int expected = 1;
//		int actual = deviceDao.updateIsDeletedForDeleteDevice(2);
//		assertEquals(expected, actual);
//	}
//	
//	// 裝置管理不實作刪除功能，故皆回傳false
//	@Test
//	public void testDeleteDeviceById() {
//		boolean expected = false;
//		boolean actual = deviceDao.deleteDeviceById(2);
//		assertEquals(expected, actual);
//	}

}
