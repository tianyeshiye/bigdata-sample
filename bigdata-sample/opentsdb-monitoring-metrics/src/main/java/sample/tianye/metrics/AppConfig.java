package sample.tianye.metrics;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import sample.tianye.metrics.utils.ConnectType;

public class AppConfig {

	private static AppConfig instance;

	public static AppConfig getInstance() {

		if (instance == null) {
			instance = new AppConfig();
		}
		return instance;
	}

	private String influxDBUrl;

	private String influxDBUser;

	private String influxDBPwd;

	private String influxDBName;

	private String influxDBTableName;

	private List<String> openTSDBUrlList;

	private List<String> openTSDBTelnetList;

	private String openTSDBUser;

	private String openTSDBPwd;

	private List<String> metricList;

	private int metricInterval;

	private ConnectType connectType;

	public ConnectType getConnectType() {
		return connectType;
	}

	public String getInfluxDBUrl() {
		return influxDBUrl;
	}

	public String getInfluxDBUser() {
		return influxDBUser;
	}

	public String getInfluxDBPwd() {
		return influxDBPwd;
	}

	public String getInfluxDBName() {
		return influxDBName;
	}

	public String getInfluxDBTableName() {
		return influxDBTableName;
	}

	public List<String> getOpenTSDBUrlList() {
		return openTSDBUrlList;
	}

	public String getOpenTSDBUser() {
		return openTSDBUser;
	}

	public String getOpenTSDBPwd() {
		return openTSDBPwd;
	}

	public List<String> getMetricList() {
		return metricList;
	}

	public int getMetricInterval() {
		return metricInterval;
	}

	public List<String> getOpenTSDBTelnetList() {
		return openTSDBTelnetList;
	}

	public void initConfig(String path) {
		Properties prop = new Properties();

		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(path))) {

			prop.load(inputStream);

			influxDBUrl = prop.getProperty("influxDB.url");
			influxDBUser = prop.getProperty("influxDB.user");
			influxDBPwd = prop.getProperty("influxDB.password");
			influxDBName = prop.getProperty("influxDB.database");
			influxDBTableName = prop.getProperty("influxDB.table");

			String connectTypeStr = prop.getProperty("openTSDB.connecttype");
			connectType = ConnectType.valueOf(connectTypeStr);

			String openTSDBUrlStr = prop.getProperty("openTSDB.url");
			openTSDBUrlList = Arrays.asList(openTSDBUrlStr.split(","));

			String openTSDBTelnetStr = prop.getProperty("openTSDB.telnet");
			openTSDBTelnetList = Arrays.asList(openTSDBTelnetStr.split(","));

			openTSDBUser = prop.getProperty("openTSDB.user");
			openTSDBPwd = prop.getProperty("openTSDB.password");

			String metricStr = prop.getProperty("metric.list");
			metricList = Arrays.asList(metricStr.split(","));

			metricInterval = Integer.parseInt(prop.getProperty("metric.interval"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
