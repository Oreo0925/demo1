package com.flowring.cn.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flowring.cn.dao.SequenceDao;
import com.flowring.cn.enums.SequenceConstantEnum;
import com.flowring.cn.service.SequenceService;

@Service
public class SequenceServiceImpl implements SequenceService {
	
	private static FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Override
	public long getNextSequence(String prefixCode, Date today, int type) {
		long nextId = 0;
		String name = "";
		for (SequenceConstantEnum sequenceConstantEnum : SequenceConstantEnum.values()) {
			if (sequenceConstantEnum.toString().equals(prefixCode)) {
				name = sequenceConstantEnum.getDescription();
			}
		}
		nextId = sequenceDao.getNextSequenceByName(name, prefixCode, today, type);
		return nextId;
	}
	
	@Override
	public long getNextSequenceWithPrefix(String prefixCode, Date today, int type) {
		long nextId = 0;
		String name = "";
		for (SequenceConstantEnum sequenceConstantEnum : SequenceConstantEnum.values()) {
			if (sequenceConstantEnum.toString().equals(prefixCode)) {
				name = sequenceConstantEnum.getPrefixDescription();
			}
		}
		nextId = sequenceDao.getNextSequenceByName(name, prefixCode, today, type);
		return nextId;
	}
	
	@Override
	public long getNextSequenceWithPrefixAndDate(String prefixCode, Date today, int type) {
		long nextId = 0;
		String name = "";
		for (SequenceConstantEnum sequenceConstantEnum : SequenceConstantEnum.values()) {
			if (sequenceConstantEnum.toString().equals(prefixCode)) {
				name = sequenceConstantEnum.getPrefixAndDateDescription();
			}
		}
		nextId = sequenceDao.getNextSequence(name, prefixCode, today, type);
		return nextId;
	}
	
	@Override
	public int getNextSequenceWithNoPrefixAndDate(String prefixCode) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		long id = this.getNextSequence(prefixCode, today, 1);
//		String nextId = StringUtils.leftPad(id + "", length, "0");
		return Integer.parseInt(String.valueOf(id));
	}
	
	@Override
	public String getNextSequenceWithPrefix(String prefixCode, int length) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		long id = this.getNextSequenceWithPrefix(prefixCode, today, 2);
		String strToday = prefixCode;
		int maxLength = length - strToday.length();
		String nextId = StringUtils.leftPad(id + "", maxLength, "0");
		return strToday + nextId;
	}

	@Override
	public String getNextSequenceWithPreFixAndDate(String prefixCode, int length) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		long id = this.getNextSequenceWithPrefixAndDate(prefixCode, today, 3);
		String strToday = prefixCode + fdf.format(today);
		int maxLength = length - strToday.length();
		String nextId = StringUtils.leftPad(id + "", maxLength, "0");
		return strToday + nextId;
	}
	
}
