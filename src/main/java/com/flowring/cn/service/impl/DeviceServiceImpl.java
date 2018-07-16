package com.flowring.cn.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowring.cn.dao.DeviceDao;
import com.flowring.cn.dao.DevicePropertiesDao;
import com.flowring.cn.dao.DeviceTempDao;
import com.flowring.cn.dao.DeviceTempDynamicPropertiesDao;
import com.flowring.cn.dao.DeviceTypeDao;
import com.flowring.cn.entity.Device;
import com.flowring.cn.entity.DeviceGroup;
import com.flowring.cn.entity.DeviceProperties;
import com.flowring.cn.entity.DeviceStaticProperties;
import com.flowring.cn.entity.DeviceTemp;
import com.flowring.cn.entity.DeviceTempPropData;
import com.flowring.cn.entity.Group;
import com.flowring.cn.entity.DeviceType;
import com.flowring.cn.dao.DeviceStaticPropertiesDao;
import com.flowring.cn.entity.DeviceTempDynamicProp;
import com.flowring.cn.enums.ConnesiaEnum;
import com.flowring.cn.exception.ConnesiaException;
import com.flowring.cn.service.DeviceService;
import com.flowring.cn.service.GroupService;
import com.flowring.cn.service.SequenceService;
import com.flowring.cn.util.Constants;
import com.flowring.cn.enums.SequenceConstantEnum;

@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private DeviceTempDynamicPropertiesDao tempDynamicPropertiesDao;

	@Autowired
	private DeviceStaticPropertiesDao deviceStaticPropertiesDao;

	// TODO 暫不會用到故無實作，未完成。
	@Autowired
	private DevicePropertiesDao devicePropertiesDao;

	@Autowired
	private DeviceTempDao deviceTempDao;

	@Autowired
	SequenceService sequenceService;

	@Autowired
	private DeviceTypeDao typeDao;

	@Autowired
	private GroupService groupManagementService;

	@Override
	public List<Device> getAllDevice() {
		List<Device> deviceList = deviceDao.getAllDeviceList();
		getDeviceDetail(deviceList);
		if (deviceList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceList;
	}

	@Override
	public List<Device> getDeviceList() {
		List<Device> deviceList = new ArrayList<Device>();
		deviceList = deviceDao.getDeviceListByDeleted(false);
		getDeviceDetail(deviceList);
		if (deviceList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceList;
	}

	@Override
	public Device getDeviceById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Device device = new Device();
		try {
			device = deviceDao.getDeviceById(id);
			getDeviceDetail(device);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return device;
	}

	@Override
	public Device getDeviceByFullId(String fullId) {
		if (fullId == "") {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Device device = new Device();
		try {
			device = deviceDao.getDeviceByFullId(fullId);
			getDeviceDetail(device);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return device;
	}
	
	private void getDeviceDetail(List<Device> deviceList){
		for(Device device:deviceList){
			device = getDeviceDetail(device);
		}
	}
	
	// 取得裝置其他相關資料，如: property, type, groupList
	private Device getDeviceDetail(Device device) {
		int deviceId = device.getId();
		// 取得裝置模板與屬性
		if (device.getTempId() != 0) {
			// 取得所有欄位
			boolean exist = deviceTempDao.isDeviceInTempDynamicTableExist(
					deviceId, device.getTempId());
			if (exist) {
				List<DeviceTempDynamicProp> tempDynamicPropList = tempDynamicPropertiesDao
						.getDevTempDynamicPropListByTempId(device.getTempId());
				// 合併欄位及欄位值
				List<DeviceTempPropData> tempPropDataList = deviceTempDao
						.getDeviceTempDynamicTableData(device.getTempId(),
								deviceId, tempDynamicPropList);
				device.setDeviceTempPropDataList(tempPropDataList);
			}
		}
		// 取得裝置類型
		if (device.getDeviceTypeId() != 0) {
			DeviceType deviceType = new DeviceType();
			try {
				deviceType = typeDao
						.getDeviceTypeById(device.getDeviceTypeId());
				device.setDeviceType(deviceType);
			} catch (EmptyResultDataAccessException e) {
				// 若查無資料不會跳出錯誤
				// throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
			}
		}
		
		
		// 取得 GroupList
		List<Group> groupList = groupManagementService
				.getGroupListByDeviceId(deviceId);
		
		device.setParentGroupList(groupList);
		return device;
	}

	@Override
	public List<Device> getDeviceListByParentId(int parentId) {
		List<Device> deviceList = deviceDao.getDeviceListByParentIdAndDeleted(
				parentId, false);
		getDeviceDetail(deviceList);
		if (deviceList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceList;
	}

	@Override
	public List<Device> getDeviceListByGroupsId(int groupId) {
		List<DeviceGroup> deviceGroupList = new ArrayList<DeviceGroup>();
		List<Device> deviceList = new ArrayList<Device>();
		try {
			deviceGroupList = groupManagementService.getDeviceGroupByGroupsId(groupId);
			deviceGroupList.forEach(deviceGroup -> {
				Device device = deviceDao.getDeviceById(deviceGroup.getDeviceId());
				deviceList.add(device);
			});
		} catch (ConnesiaException e) {

		}
		return deviceList;
	}

	@Override
	public Device getDeviceByIdentifierAndCustomer(String identifier,
			String customer) {
		if (StringUtils.isBlank(identifier)
				&& StringUtils.isBlank(customer)) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		Device device = new Device();
		try {
			device = deviceDao.getDeviceByIdentifierAndCustomer(identifier,
					customer);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		device = getDeviceDetail(device);
		return device;
	}

	@Override
	@Transactional
	public int insertDevice(Device device) {
		if (device == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		String fullId = sequenceService.getNextSequenceWithPrefix(
				SequenceConstantEnum.DEV.toString(), 16);
		device.setFullId(fullId);
		int id = deviceDao.insertDevice(device);
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return id;
	}

	@Override
	@Transactional
	public int updateDevice(Device device) {
		if (device == null || device.getId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int count = deviceDao.updateDevice(device);
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	@Transactional
	public int deleteDeviceById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceDao.updateDeviceDeletedById(id, true);
		if (total <= 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchInsertDeviceList(List<Device> deviceList) {
		if (deviceList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		String fullId = "";
		for (Device device : deviceList) {
			fullId = sequenceService.getNextSequenceWithPrefix(
					SequenceConstantEnum.DEV.toString(), 16);
			device.setFullId(fullId);
		}
		int total = deviceDao.insertDeviceList(deviceList);
		if (deviceList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchDeleteDeviceList(List<Device> deviceList) {
		if (deviceList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceDao.updateDeviceListDeleted(deviceList, true);
		if (deviceList.size() != total) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int updateDeviceLastRecordTimeById(int id, Device device) {
		if (id == 0 || device.getLastRecordTime() == null) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int count = deviceDao.updateDeviceLastRecordTimeById(id, device.getLastRecordTime());
		if (count == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return count;
	}
	
	@Override
	public List<DeviceTempDynamicProp> getDevTempDynamicPropByTempId(int tempId) {
		if (tempId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<DeviceTempDynamicProp> tempDynamicPropList = tempDynamicPropertiesDao
				.getDevTempDynamicPropListByTempIdAndDeleted(tempId, false);
		if (tempDynamicPropList.size() <= 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return tempDynamicPropList;
	}

	@Override
	public DeviceTempDynamicProp getDevTempDynamicPropById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		DeviceTempDynamicProp tempDynamicProp = new DeviceTempDynamicProp();
		try {
			tempDynamicProp = tempDynamicPropertiesDao
					.getDevTempDynamicPropById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return tempDynamicProp;
	}

	@Override
	@Transactional
	public int batchInsertDevTempDynamicPropList(
			List<DeviceTempDynamicProp> tempDynamicPropList) {
		if (tempDynamicPropList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = tempDynamicPropertiesDao
				.insertDevTempDynamicPropList(tempDynamicPropList);
		if (total != tempDynamicPropList.size()) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int deleteDevTempDynamicPropByTempId(int tempId) {
		if (tempId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int count = tempDynamicPropertiesDao
				.updateDevTempDynamicPropDeletedByTempId(tempId, true);
		if (count <= 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return count;
	}

	@Override
	@Transactional
	public int batchDeletedDeviceTempDynamicPropList(
			List<DeviceTempDynamicProp> tempDynamicPropList) {
		if (tempDynamicPropList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = tempDynamicPropertiesDao
				.updateDevTempDynamicPropListDeleted(tempDynamicPropList, true);
		if (total != tempDynamicPropList.size()) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchUpdateDeviceTempPropertiesList(
			List<DeviceTempDynamicProp> tempDynamicPropList) {
		if (tempDynamicPropList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = tempDynamicPropertiesDao
				.updateDevTempDynamicProperList(tempDynamicPropList);
		if (total != tempDynamicPropList.size()) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public boolean isDevDynamicPropExist(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int rs = tempDynamicPropertiesDao.isDevDynamicPropExist(id);
		return rs <= 0 ? true : false;
	}

	@Override
	public List<DeviceStaticProperties> getAllDevStaticPropList() {
		List<DeviceStaticProperties> staticPropertiesList = deviceStaticPropertiesDao
				.getAllDevStaticPropList();
		if (staticPropertiesList.size() <= 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return staticPropertiesList;
	}

	@Override
	public DeviceStaticProperties getDevStaticPropListById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		DeviceStaticProperties staticProperties = new DeviceStaticProperties();
		try {
			staticProperties = deviceStaticPropertiesDao
					.getDevStaticPropListById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return staticProperties;
	}

	@Override
	@Transactional
	public int batchInsertDevStaticPropList(
			List<DeviceStaticProperties> staticPropList) {
		if (staticPropList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceStaticPropertiesDao
				.insertDevStaticPropList(staticPropList);
		if (total != staticPropList.size()) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int batchUpdateDevStaticPropList(
			List<DeviceStaticProperties> staticPropList) {
		if (staticPropList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceStaticPropertiesDao
				.updateDevStaticPropList(staticPropList);
		if (total != staticPropList.size()) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<DeviceProperties> getAllDeviceProperties() {
		List<DeviceProperties> devicePropertiesList = new ArrayList<DeviceProperties>();
		devicePropertiesList = devicePropertiesDao.getAllDeviceProperties();
		return devicePropertiesList;
	}

	@Override
	public List<DeviceProperties> getDevicePropertiesByDeviceId(int deviceId) {
		if (deviceId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		List<DeviceProperties> devicePropertiesList = devicePropertiesDao
				.getDevicePropertiesByDeviceId(deviceId);
		return devicePropertiesList;
	}

	@Override
	public DeviceProperties getDevicePropertiesById(int id) {
		DeviceProperties deviceProperties = new DeviceProperties();
		try {
			deviceProperties = devicePropertiesDao.getDevicePropertiesById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}

		return deviceProperties;
	}

	@Override
	@Transactional
	public int insertDeviceProperties(
			List<DeviceProperties> devicePropertiesList) {
		int rs = devicePropertiesDao
				.insertDeviceProperties(devicePropertiesList);
		return rs;
	}

	@Override
	public int updateDeviceProperties(
			List<DeviceProperties> devicePropertiesList) {
		int rs = devicePropertiesDao
				.updateDeviceProperties(devicePropertiesList);
		return rs;
	}

	@Override
	@Transactional
	public boolean deleteDevicePropertiesList(
			List<DeviceProperties> devicePropertiesList) {
		boolean rs = devicePropertiesDao
				.deleteDevicePropertiesList(devicePropertiesList);
		return rs;
	}

	@Override
	public Map<String, Object> devicePropertiesSetting(Integer tempId) {
		Map<String, Object> devicePropertiesMap = new HashMap();
		List<DeviceTempDynamicProp> tempDynamicPropertiesList = new ArrayList<DeviceTempDynamicProp>();
		if (tempId != 0) {
			tempDynamicPropertiesList = getDevTempDynamicPropByTempId(tempId);
		}
		List<DeviceStaticProperties> staticPropertiesList = getAllDevStaticPropList();
		devicePropertiesMap.put(Constants.DEVICE_DYNAMIC,
				tempDynamicPropertiesList);
		devicePropertiesMap.put(Constants.DEVICE_STATIC, staticPropertiesList);
		return devicePropertiesMap;
	}

	@Override
	public List<DeviceTemp> getAllDeviceTemp() {
		List<DeviceTemp> deviceTempList = deviceTempDao.getAllDeviceTemp();
		List<DeviceTempDynamicProp> dynamicPropList = new ArrayList<DeviceTempDynamicProp>();
		for (DeviceTemp deviceTemp : deviceTempList) {
			dynamicPropList = tempDynamicPropertiesDao
					.getDevTempDynamicPropListByTempId(deviceTemp.getId());
			deviceTemp.setDynamicPropList(dynamicPropList);
		}
		if (deviceTempList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceTempList;
	}

	@Override
	public DeviceTemp getDeviceTempByTempId(int tempId) {
		if (tempId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		DeviceTemp deviceTemp = new DeviceTemp();
		try {
			deviceTemp = deviceTempDao.getDeviceTempByTempId(tempId);
			List<DeviceTempDynamicProp> dynamicPropList = tempDynamicPropertiesDao
					.getDevTempDynamicPropListByTempId(deviceTemp.getId());
			deviceTemp.setDynamicPropList(dynamicPropList);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceTemp;
	}

	@Override
	@Transactional
	public int insertDeviceTemp(DeviceTemp deviceTemp) {
		int lastId = deviceTempDao.insertDeviceTemp(deviceTemp);
		List<DeviceTempDynamicProp> dynamicPropList = deviceTemp
				.getDynamicPropList();
		boolean success = deviceTempDao.createDeviceTempDynamicTable(lastId,
				dynamicPropList);
		int propCount = tempDynamicPropertiesDao.insertDevTempDynamicPropList(
				lastId, dynamicPropList);

		if (lastId == 0 || !success || propCount != dynamicPropList.size()) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return lastId;
	}

	@Override
	@Transactional
	public int updateDeviceTemp(DeviceTemp deviceTemp) {
		int tempId = deviceTemp.getId();
		if (tempId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		// 更改模版名稱
		int total = deviceTempDao.updateDeviceTemp(deviceTemp);
		// 取得原本模板屬性 List
		List<DeviceTempDynamicProp> oldPropList = tempDynamicPropertiesDao
				.getDevTempDynamicPropListByTempId(tempId);
		// 取得欲更新的模板屬性 List
		List<DeviceTempDynamicProp> newPropList = deviceTemp
				.getDynamicPropList();
		// 更新模板屬性 device_temp_{tempId}
		boolean success = deviceTempDao.updateDeviceTempDynamicTable(tempId,
				oldPropList, newPropList);
		// 修改模板屬性對應 device_temp_dynamic_properties
		int propTotal = tempDynamicPropertiesDao
				.updateDevTempDynamicProperList(deviceTemp.getDynamicPropList());
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	@Transactional
	public int deletedDeviceTempByTempId(int tempId) {
		if (tempId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceTempDao.updateDeviceTempDeletedById(tempId, true);
		int propTotal = tempDynamicPropertiesDao
				.updateDevTempDynamicPropDeletedByTempId(tempId, true);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int insertDeviceTempDynamicTableData(int tempId, int deviceId) {
		if (tempId == 0 || deviceId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceTempDao.insertDeviceTempDynamicTableData(tempId,
				deviceId);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public List<DeviceTempPropData> getDeviceTempDynamicTableData(int tempId,
			int deviceId, List<DeviceTempDynamicProp> tempDynamicPropList) {
		List<DeviceTempPropData> tempPropDataList = new ArrayList<DeviceTempPropData>();
		tempPropDataList = deviceTempDao.getDeviceTempDynamicTableData(tempId,
				deviceId, tempDynamicPropList);
		return tempPropDataList;
	}

	@Override
	public int batchUpdateDeviceTempDynamicTableData(int tempId, int deviceId,
			List<DeviceTempPropData> tempPropDataList) {
		if (tempPropDataList.size() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = deviceTempDao.updateDeviceTempDynamicTableData(tempId,
				deviceId, tempPropDataList);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public boolean isDeviceInTempDynamicTableExist(int deviceId, int tempId) {
		if (deviceId == 0 || tempId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		boolean exist = deviceTempDao.isDeviceInTempDynamicTableExist(deviceId,
				tempId);
		if (exist) {
			throw new ConnesiaException(
					ConnesiaEnum.DEVICE_IS_EXIST_TEMP_DYNAMIC_TABLE);
		}
		return exist;
	}

	@Override
	public DeviceType getDeviceTypeById(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		DeviceType deviceType = new DeviceType();
		try {
			deviceType = typeDao.getDeviceTypeById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ConnesiaException(ConnesiaEnum.NO_DATA_ERROR);
		}
		return deviceType;
	}

	@Override
	public int insertDeviceType(DeviceType type) {
		int lastId = typeDao.insertDeviceType(type);
		if (lastId == 0) {
			throw new ConnesiaException(ConnesiaEnum.INSERT_FAILD_ERROR);
		}
		return lastId;
	}

	@Override
	public int updateDeviceType(DeviceType type) {
		if (type.getId() == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = typeDao.updateDeviceType(type);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.UPDATE_FAILD_ERROR);
		}
		return total;
	}

	@Override
	public int deleteDeviceType(int id) {
		if (id == 0) {
			throw new ConnesiaException(ConnesiaEnum.INPUT_NULL_DATA);
		}
		int total = typeDao.deleteDeviceType(id);
		if (total == 0) {
			throw new ConnesiaException(ConnesiaEnum.DELETE_FAILD_ERROR);
		}
		return total;
	}



}
