package com.flowring.cn.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriUtils;

import com.flowring.cn.entity.InfluxOperate;
import com.flowring.cn.entity.InfluxQueryResponse;
import com.flowring.cn.entity.InfluxQueryResult;
import com.flowring.cn.entity.InfluxQuerySeries;
import com.google.gson.Gson;

public class InfluxDBUtils {

	public static void addData(String tableName, String dataEntry) {

		// CloseableHttpClient httpclient = HttpClients.createDefault();
		int timeout = 2;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpPost httppost = new HttpPost(Constants.INFLUXDB_URL + "/write?db=celefish");
		StringEntity stringEntity = new StringEntity(tableName + "," + dataEntry, "UTF-8");
		// StringEntity stringEntity = new
		// StringEntity("testTable,deviceId=DEV00021527043981329
		// do=8.2,temperature=25,controlCode=03,statusCode=00", "UTF-8");
		System.out.println("StringEntity: " + tableName + "," + dataEntry);

		try {
			httppost.setEntity(stringEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
					System.out.println("--------------------------------------");
				} else {
					System.out.println("Response success");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static long getLastDataTime(String identify) {
		long lastTime = 0L;

		int timeout = 2;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpGet httpget = null;

		try {

			StringBuffer sb = new StringBuffer();
			sb.append(Constants.INFLUXDB_API_URL);

			StringBuffer sb2 = new StringBuffer();
			sb2.append("db=celefish&q=SELECT \"temperature\" FROM \"celefish\" WHERE (\"identify\" = '");
			sb2.append(identify);
			sb2.append("') AND time >= now() - 5m&epoch=ms");

			sb.append(UriUtils.encodeQuery(sb2.toString(), "UTF-8"));

			System.out.println("query url:" + sb.toString());
			httpget = new HttpGet(sb.toString());
			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {

					String json = EntityUtils.toString(entity, "UTF-8");

					System.out.println("--------------------------------------");
					System.out.println("Response content: " + json);
					System.out.println("--------------------------------------");

					/*
					 * { "results": [ { "statement_id": 0, "series": [ { "name": "celefish",
					 * "columns": [ "time", "temperature" ], "values": [ [ 1530063091409, 99.9 ], [
					 * 1530063091441, 29.700000000000003 ] ] } ] } ] }
					 */

					Gson gson = new Gson();
					InfluxQueryResponse influxQueryResponse = gson.fromJson(json, InfluxQueryResponse.class);

					List<InfluxQueryResult> results = influxQueryResponse.getResults();

					if (results != null && results.size() > 0) {
						InfluxQueryResult influxQueryResult = results.get(results.size() - 1);
						List<InfluxQuerySeries> series = influxQueryResult.getSeries();

						if (series != null && series.size() > 0) {
							InfluxQuerySeries row = series.get(series.size() - 1);
							List<List<String>> values = row.getValues();
							List<String> lastValue = values.get(values.size() - 1);
							String value = (String) lastValue.get(0);

							lastTime = Long.parseLong(value);
						}
					}
				} else {
					System.out.println("Response success");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return lastTime;
	}
	
	public static InfluxQuerySeries getDataOfIdentify(String identify, InfluxOperate influxOperate) {
		InfluxQuerySeries row = null;
		String db = influxOperate.getDb();
		String table = influxOperate.getTable();
		if (StringUtils.isNotBlank(db) && StringUtils.isNotBlank(table)) {
			int timeout = 30;
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
					.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
			CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

			HttpGet httpget = null;

			try {
				String columns = "*";
				if (StringUtils.isNotBlank(influxOperate.getColumns())) {
					columns = influxOperate.getColumns();
				}
				StringBuffer conditions = new StringBuffer();
				if (StringUtils.isNotBlank(influxOperate.getConditions())) {
					conditions.append(influxOperate.getConditions());
					// if no time, get one day data
					if (conditions.indexOf("time") < 0) {
						conditions.append(" time >= ").append(CalendarUtils.getStartDate().getTime()).append("ms")
							.append(" AND time <= ").append(CalendarUtils.getEndDate().getTime()).append("ms");
					}
				}

				
				StringBuffer sqlCondition = new StringBuffer();
				if (StringUtils.isNotBlank(identify) || StringUtils.isNotBlank(conditions)) {
					sqlCondition.append(" WHERE ");
					if (StringUtils.isNotBlank(identify)) {
						sqlCondition.append(" identify='").append(identify).append("'");
					}
					if (StringUtils.isNotBlank(conditions)) {
						if (StringUtils.isNotBlank(identify)) {
							sqlCondition.append(" AND ");
						}
						sqlCondition.append(conditions.toString());
					}
				}
				

				StringBuffer sb = new StringBuffer();
				sb.append(Constants.INFLUXDB_URL).append("/query?")
					.append("epoch=ms&db=").append(db)
					.append("&q=SELECT ").append(columns).append(" FROM ").append(table).append(sqlCondition.toString());

				httpget = new HttpGet(UriUtils.encodeQuery(sb.toString(), "UTF-8"));
				//System.out.println("executing request " + httpget.getURI());
				CloseableHttpResponse response = httpclient.execute(httpget);
				try {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String json = EntityUtils.toString(entity, "UTF-8");

//						System.out.println("--------------------------------------");
//						System.out.println("Response content: " + json);
//						System.out.println("--------------------------------------");

						Gson gson = new Gson();
						InfluxQueryResponse influxQueryResponse = gson.fromJson(json, InfluxQueryResponse.class);

						List<InfluxQueryResult> results = influxQueryResponse.getResults();

						if (results != null && results.size() > 0) {
							InfluxQueryResult influxQueryResult = results.get(results.size() - 1);
							List<InfluxQuerySeries> series = influxQueryResult.getSeries();

							if (series != null && series.size() > 0) {
								row = series.get(series.size() - 1);
							}
						}
					} else {
						System.out.println("Response success");
					}
				} finally {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return row;
	}
}
