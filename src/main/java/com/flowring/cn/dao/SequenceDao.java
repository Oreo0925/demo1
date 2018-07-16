package com.flowring.cn.dao;

import java.util.Date;

public interface SequenceDao {
	
	public long getNextSequenceByName(String name, String prefixCode, Date today, int type);
	public long getNextSequence(String name, String prefixCode, Date today, int type);
	public int newSequence(String name, String prefixCode, Date today, int type);
}
