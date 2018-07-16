package com.flowring.cn.service;

import java.util.Date;

public interface SequenceService {
	
	public long getNextSequence(String prefixCode, Date today, int type);
	public long getNextSequenceWithPrefix(String prefixCode, Date today, int type);
	public long getNextSequenceWithPrefixAndDate(String prefixCode, Date today, int type);
	public int getNextSequenceWithNoPrefixAndDate(String prefixCode);
	public String getNextSequenceWithPrefix(String prefixCode, int length);
	public String getNextSequenceWithPreFixAndDate(String prefixCode, int length);

}
