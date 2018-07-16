package com.flowring.cn.entity;


/**
 * In order to operate CRUD in InfluxDB 
 * @author jianren.chen
 */
public class InfluxOperate {
	private String db = "";
	private String table = "";
	private String columns = "";
	private String conditions = "";
	
	public String getDb() {
		return db;
	}
	
	public void setDb(String db) {
		this.db = db;
	}
	
	public String getTable() {
		return table;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getColumns() {
		return columns;
	}
	
	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}
