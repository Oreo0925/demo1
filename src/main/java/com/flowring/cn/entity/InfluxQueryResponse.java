package com.flowring.cn.entity;

import java.util.List;

public class InfluxQueryResponse {

	private List<InfluxQueryResult> results;

	public List<InfluxQueryResult> getResults() {
		return results;
	}

	public void setResults(List<InfluxQueryResult> results) {
		this.results = results;
	}

}
