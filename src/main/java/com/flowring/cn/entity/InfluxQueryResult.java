package com.flowring.cn.entity;

import java.util.List;

public class InfluxQueryResult {

	int statement_id;
	List<InfluxQuerySeries> series;

	public int getStatement_id() {
		return statement_id;
	}

	public void setStatement_id(int statement_id) {
		this.statement_id = statement_id;
	}

	public List<InfluxQuerySeries> getSeries() {
		return series;
	}

	public void setSeries(List<InfluxQuerySeries> series) {
		this.series = series;
	}

}
