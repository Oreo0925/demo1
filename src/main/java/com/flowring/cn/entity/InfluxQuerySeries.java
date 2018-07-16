package com.flowring.cn.entity;

import java.util.List;

public class InfluxQuerySeries {

	private String name;
	private List<String> columns;
	private List<List<String>> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<List<String>> getValues() {
		return values;
	}

	public void setValues(List<List<String>> values) {
		this.values = values;
	}

}
