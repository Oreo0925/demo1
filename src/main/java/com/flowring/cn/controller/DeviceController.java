package com.flowring.cn.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceStaticProperties;
import com.flowring.cn.entity.DeviceTemp;
import com.flowring.cn.entity.DeviceTempDynamicProp;
import com.flowring.cn.entity.DeviceTempPropData;
import com.flowring.cn.entity.DeviceType;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.pojo.SingleResponseObject;
import com.flowring.cn.service.DeviceService;

@RestController
@RequestMapping("/")
public class DeviceController {

	@Autowired
	private DeviceService deviceManagementService;

	/*
	 * 取得裝置，不含被使用者刪除的裝置
	 */
	@GetMapping("/devices")
	public SingleResponseObject<List<Device>> getAllDevice() {
		SingleResponseObject<List<Device>> ero = new SingleResponseObject<List<Device>>();
		try {
			List<Device> deviceList = deviceManagementService.getDeviceList();
			ero.setData(deviceList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 依 DeviceId 取得裝置
	 */
	@CrossOrigin
	@GetMapping("/device/{id}")
	public SingleResponseObject<Device> getDeviceById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Device> ero = new SingleResponseObject<Device>();
		try {
			Device device = deviceManagementService.getDeviceById(id);
			ero.setData(device);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 依 fullId 取得裝置
	 */
	@CrossOrigin
	@GetMapping("/device/fullId/{fullId}")
	public SingleResponseObject<Device> getDeviceByFullId(
			@PathVariable("fullId") String fullId) {
		SingleResponseObject<Device> ero = new SingleResponseObject<Device>();
		try {
			Device device = deviceManagementService.getDeviceByFullId(fullId);
			ero.setData(device);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 依 ParentId 取得裝置，不含被使用者刪除的裝置
	 */
	@GetMapping("/devices/parentId/{parentId}")
	public SingleResponseObject<List<Device>> getDeviceNotIsDeletedByParentId(
			@PathVariable("parentId") int parentId) {
		SingleResponseObject<List<Device>> ero = new SingleResponseObject<List<Device>>();
		try {
			List<Device> deviceList = deviceManagementService
					.getDeviceListByParentId(parentId);
			ero.setData(deviceList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	@CrossOrigin
	@GetMapping("/device/identifier/{identifier}/customer/{customer}")
	public SingleResponseObject<Device> getDeviceByIdentifierAndCustomer(
			@PathVariable("identifier") String identifier,
			@PathVariable("customer") String customer) {
		SingleResponseObject<Device> ero = new SingleResponseObject<Device>();
		try {
			Device device = deviceManagementService
					.getDeviceByIdentifierAndCustomer(identifier, customer);
			ero.setData(device);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 新增單一筆裝置
	 */
	@PostMapping("/device")
	public SingleResponseObject<Device> insertDevice(@RequestBody Device device) {
		SingleResponseObject<Device> ero = new SingleResponseObject<Device>();
		try {
			deviceManagementService.insertDevice(device);
			ero.setData(device);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 新增多筆裝置
	 */
	@PostMapping("/devices")
	public SingleResponseObject<Integer> insertDeviceList(
			@RequestBody List<Device> deviceList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = deviceManagementService
					.batchInsertDeviceList(deviceList);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 修改裝置
	 */
	@PutMapping("/device/{id}")
	public SingleResponseObject<Device> updateDevice(
			@PathVariable("id") Integer id, @RequestBody Device device) {
		SingleResponseObject<Device> ero = new SingleResponseObject<Device>();
		try {
			device.setId(id);
			deviceManagementService.updateDevice(device);
			ero.setData(device);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除單一裝置
	 */
	@DeleteMapping("/device/{id}")
	public SingleResponseObject<Integer> deletedDeviceList(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int rs = deviceManagementService.deleteDeviceById(id);
			ero.setTotal(rs);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 修改裝置 lastRecordTime
	 */
	@PutMapping("/device/{id}/lastRecordTime")
	public SingleResponseObject<Integer> updateDeviceLastRecordTimeById(
			@PathVariable("id") Integer id,
			@RequestBody Device device) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int rs = deviceManagementService.updateDeviceLastRecordTimeById(id,
					device);
			ero.setTotal(rs);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除多裝置
	 */
	@DeleteMapping("/devices")
	public SingleResponseObject<Integer> deletedDeviceList(
			@RequestBody List<Device> deviceList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = deviceManagementService
					.batchDeleteDeviceList(deviceList);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 裝置基本設定 回傳的 Data 中包含兩個物件List DeviceTempDynamicProperties 裝置動態屬性
	 * DeviceStaticProperties 裝置靜態屬性(>0筆)
	 */
	@GetMapping("/device/setting/tempId/{tempId}")
	public SingleResponseObject<Object> devicePropertiesSetting(
			@PathVariable("tempId") Integer tempId) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		Map<String, Object> devicePropertiesMap = (Map<String, Object>) deviceManagementService
				.devicePropertiesSetting(tempId);
		ero.setData(devicePropertiesMap);
		return ero;
	}

	/*
	 * 查詢裝置模板動態屬性
	 */
	@GetMapping("/device/properties/tempId/{tempId}")
	public SingleResponseObject<Object> getDeviceTempPropertiesByTempId(
			@PathVariable("tempId") Integer tempId) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			List<DeviceTempDynamicProp> tempDynamicPropertiesList = deviceManagementService
					.getDevTempDynamicPropByTempId(tempId);
			ero.setData(tempDynamicPropertiesList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * By Id 查詢裝置模板動態屬性
	 */
	@GetMapping("/device/property/temp/{id}")
	public SingleResponseObject<Object> getDeviceTempPropertiesById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			DeviceTempDynamicProp tempDynamicProperties = deviceManagementService
					.getDevTempDynamicPropById(id);
			ero.setData(tempDynamicProperties);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 新增裝置模板屬性
	 */
	@PostMapping("/device/properties/temp")
	public SingleResponseObject<Integer> insertDeviceTempPropertiesList(
			@RequestBody List<DeviceTempDynamicProp> dynamicProperties) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = deviceManagementService
					.batchInsertDevTempDynamicPropList(dynamicProperties);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 依 tempId 刪除裝置模板屬性
	 */
	@DeleteMapping("/device/properties/tempId/{tempId}")
	public SingleResponseObject<Integer> deleteDeviceTempProperties(
			@PathVariable("tempId") Integer tempId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int count = deviceManagementService
					.deleteDevTempDynamicPropByTempId(tempId);
			ero.setTotal(count);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 刪除裝置模板屬性 List<DeviceTempProperties>
	 */
	@DeleteMapping("/device/properties/temp")
	public SingleResponseObject<Integer> deleteDeviceTempPropertiesList(
			@RequestBody List<DeviceTempDynamicProp> tempDynamicPropertiesList) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int count = deviceManagementService
					.batchDeletedDeviceTempDynamicPropList(tempDynamicPropertiesList);
			ero.setTotal(count);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/* 修改裝置模板屬性 */
	@PutMapping("/device/properties/temp")
	public SingleResponseObject<Object> updateDeviceTempPropertiesList(
			@RequestBody List<DeviceTempDynamicProp> tempDynamicPropertiesList) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			int rs = deviceManagementService
					.batchUpdateDeviceTempPropertiesList(tempDynamicPropertiesList);
			ero.setTotal(rs);
			ero.setData(tempDynamicPropertiesList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢裝置固定屬性
	 */
	@GetMapping("/device/properties/static")
	public SingleResponseObject<List<DeviceStaticProperties>> getAllDeviceStaticProperties() {
		SingleResponseObject<List<DeviceStaticProperties>> ero = new SingleResponseObject<List<DeviceStaticProperties>>();
		try {
			List<DeviceStaticProperties> staticPropertiesList = deviceManagementService
					.getAllDevStaticPropList();
			ero.setData(staticPropertiesList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢裝置固定屬性ById
	 */
	@GetMapping("/device/property/static/{id}")
	public SingleResponseObject<DeviceStaticProperties> getDeviceSaticPropertiesById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<DeviceStaticProperties> ero = new SingleResponseObject<DeviceStaticProperties>();
		try {
			DeviceStaticProperties staticProperties = deviceManagementService
					.getDevStaticPropListById(id);
			ero.setData(staticProperties);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 新增裝置固定屬性
	 */
	@PostMapping("/device/properties/static")
	public SingleResponseObject<Integer> insertDeviceStaticPropertiesList(
			@RequestBody List<DeviceStaticProperties> dynamicProperties) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = deviceManagementService
					.batchInsertDevStaticPropList(dynamicProperties);
			ero.setTotal(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/* 修改裝置固定屬性 */
	@PutMapping("/device/properties/static")
	public SingleResponseObject<Object> updateDeviceStaticPropertiesList(
			@RequestBody List<DeviceStaticProperties> tempDynamicPropertiesList) {
		SingleResponseObject<Object> ero = new SingleResponseObject<Object>();
		try {
			int rs = deviceManagementService
					.batchUpdateDevStaticPropList(tempDynamicPropertiesList);
			ero.setTotal(rs);
			ero.setData(tempDynamicPropertiesList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢裝置模板
	 */
	@GetMapping("/device/temps")
	public SingleResponseObject<List<DeviceTemp>> getAllDeviceTemp() {
		SingleResponseObject<List<DeviceTemp>> ero = new SingleResponseObject<List<DeviceTemp>>();
		try {
			List<DeviceTemp> deviceTempList = deviceManagementService
					.getAllDeviceTemp();
			ero.setData(deviceTempList);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 查詢裝置模板ById
	 */
	@GetMapping("/device/temp/{tempId}")
	public SingleResponseObject<DeviceTemp> getDeviceTempByTempId(
			@PathVariable("tempId") int tempId) {
		SingleResponseObject<DeviceTemp> ero = new SingleResponseObject<DeviceTemp>();
		try {
			DeviceTemp deviceTemp = deviceManagementService
					.getDeviceTempByTempId(tempId);
			ero.setData(deviceTemp);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增裝置模板
	 */
	@PostMapping("/device/temp")
	public SingleResponseObject<DeviceTemp> insertDeviceTemp(
			@RequestBody DeviceTemp deviceTemp) {
		SingleResponseObject<DeviceTemp> ero = new SingleResponseObject<DeviceTemp>();
		try {
			int lastId = deviceManagementService.insertDeviceTemp(deviceTemp);
			deviceTemp = deviceManagementService.getDeviceTempByTempId(lastId);
			ero.setData(deviceTemp);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改裝置模板
	 */
	@PutMapping("/device/temp")
	public SingleResponseObject<DeviceTemp> updateDeviceTemp(
			@RequestBody DeviceTemp deviceTemp) {
		SingleResponseObject<DeviceTemp> ero = new SingleResponseObject<DeviceTemp>();
		try {
			int count = deviceManagementService.updateDeviceTemp(deviceTemp);
			deviceTemp = deviceManagementService
					.getDeviceTempByTempId(deviceTemp.getId());
			ero.setTotal(count);
			ero.setData(deviceTemp);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 依 id 刪除裝置模板
	 */
	@DeleteMapping("/device/tempId/{tempId}")
	public SingleResponseObject<Integer> deletedDeviceTempByTempId(
			@PathVariable("tempId") Integer tempId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int rs = deviceManagementService.deletedDeviceTempByTempId(tempId);
			ero.setTotal(rs);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 於 device_temp_{tempId} 新增一個裝置
	 */
	@PostMapping("/device/data/tempId/{tempId}/deviceId/{deviceId}")
	public SingleResponseObject<Integer> insertDeviceTempDynamicTableData(
			@PathVariable("tempId") Integer tempId,
			@PathVariable("deviceId") Integer deviceId) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		boolean exist = false;
		try {
			exist = deviceManagementService.isDeviceInTempDynamicTableExist(
					deviceId, tempId);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		if (!exist) {
			try {
				int total = deviceManagementService
						.insertDeviceTempDynamicTableData(tempId, deviceId);
				ero.setData(total);
			} catch (ConnesiaException ce) {
				ero.setErrorMessage(ce);
			}
		}
		return ero;
	}

	/*
	 * 查詢裝置模組的資料 device_temp_{tempId} WHERE device_id = deviceId
	 */
	@GetMapping("/device/datas/tempId/{tempId}/deviceId/{deviceId}")
	public SingleResponseObject<List<DeviceTempPropData>> getDeviceTempDynamicTableData(
			@PathVariable("tempId") Integer tempId,
			@PathVariable("deviceId") Integer deviceId) {
		SingleResponseObject<List<DeviceTempPropData>> ero = new SingleResponseObject<List<DeviceTempPropData>>();
		List<DeviceTempDynamicProp> tempDynamicPropList = deviceManagementService
				.getDevTempDynamicPropByTempId(tempId);
		try {
			List<DeviceTempPropData> tempPropDataList = deviceManagementService
					.getDeviceTempDynamicTableData(tempId, deviceId,
							tempDynamicPropList);
			ero.setData(tempPropDataList);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 於 device_temp_{tempId} 新增一個裝置的其他資料
	 */
	@PutMapping("/device/datas/tempId/{tempId}/deviceId/{deviceId}")
	public SingleResponseObject<Integer> batchUpdateDeviceTempDynamicTableData(
			@PathVariable("tempId") Integer tempId,
			@PathVariable("deviceId") Integer deviceId,
			@RequestBody List<DeviceTempPropData> tempPropData) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int total = deviceManagementService
					.batchUpdateDeviceTempDynamicTableData(tempId, deviceId,
							tempPropData);
			ero.setData(total);
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}

	/*
	 * 查詢裝置類型 ById
	 */
	@GetMapping("/device/type/{id}")
	public SingleResponseObject<DeviceType> getDeviceTypeById(
			@PathVariable("id") Integer id) {
		SingleResponseObject<DeviceType> ero = new SingleResponseObject<DeviceType>();
		try {
			DeviceType type = deviceManagementService.getDeviceTypeById(id);
			ero.setData(type);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 新增裝置類型
	 */
	@PostMapping("/device/type")
	public SingleResponseObject<DeviceType> insertDeviceType(
			@RequestBody DeviceType type) {
		SingleResponseObject<DeviceType> ero = new SingleResponseObject<DeviceType>();
		try {
			deviceManagementService.insertDeviceType(type);
			ero.setData(type);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 修改裝置類型
	 */
	@PutMapping("/device/type")
	public SingleResponseObject<DeviceType> updateDeviceType(
			@RequestBody DeviceType type) {
		SingleResponseObject<DeviceType> ero = new SingleResponseObject<DeviceType>();
		try {
			int count = deviceManagementService.updateDeviceType(type);
			ero.setTotal(count);
			ero.setData(type);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}

	/*
	 * 刪除裝置類型
	 */
	@DeleteMapping("/device/type/{id}")
	public SingleResponseObject<Integer> deleteDeviceType(
			@PathVariable("id") Integer id) {
		SingleResponseObject<Integer> ero = new SingleResponseObject<Integer>();
		try {
			int rs = deviceManagementService.deleteDeviceType(id);
			ero.setTotal(rs);
		} catch (ConnesiaException e) {
			ero.setErrorMessage(e);
		}
		return ero;
	}
	
	@GetMapping("/device/{id}/control")
	public SingleResponseObject<Device> updateDevice(@PathVariable("id") Integer id, @RequestParam("command") String command) {
		SingleResponseObject<Device> ero = new SingleResponseObject<Device>();
		try {
			
			
		} catch (ConnesiaException ce) {
			ero.setErrorMessage(ce);
		}
		return ero;
	}
}
