package sample.tianye.metrics.influxdb;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import sample.tianye.metrics.AppConfig;

public class MetricsSave {

	private InfluxDB influxDB;

	private String dbName;
	private String tableName;
	private String retentionName;

	public MetricsSave() {
		init();
	}

	private void init() {

		AppConfig appConfig = AppConfig.getInstance();

		dbName = appConfig.getInfluxDBName();
		tableName = appConfig.getInfluxDBTableName();
		retentionName = "aRetentionPolicy";

		// "http://172.17.0.2:8086"
		String dbUrl = appConfig.getInfluxDBUrl();
		String dbUser = appConfig.getInfluxDBUser();
		String dbPwd = appConfig.getInfluxDBPwd();

		// https://github.com/influxdata/influxdb-java
		influxDB = InfluxDBFactory.connect(dbUrl, dbUser, dbPwd);
		// influxDB.query(new Query("CREATE DATABASE " + dbName));
		// influxDB.query(new Query(
		// "CREATE RETENTION POLICY " + retentionName + " ON " + dbName + "
		// DURATION 30h REPLICATION 2 DEFAULT"));

		// Flush every 2000 Points, at least every 100ms
		influxDB.enableBatch(BatchOptions.DEFAULTS.actions(2000).flushDuration(100));
	}

	public void call(List<InfluxdbMetricBean> metricsList) {

		writeToDB(metricsList);
	}

	private void writeToDB(List<InfluxdbMetricBean> metricsList) {

		BatchPoints batchPoints = BatchPoints.database(dbName).tag("async", "true") // .retentionPolicy(retentionName)
				.consistency(ConsistencyLevel.ALL).build();

		metricsList.forEach(metrics -> {

			// builder
			org.influxdb.dto.Point.Builder builder = Point.measurement(tableName);
			// timeStamp
			builder.time(metrics.getTimestamp(), TimeUnit.SECONDS);
			// tags
			builder.tag(metrics.getTagsMap());
			// field
			metrics.getFieldsMap().forEach((k, v) -> {
				builder.addField(k, v);
			});
			// point
			Point point = builder.build();
			// point batch
			batchPoints.point(point);
		});

		// write to influxDB
		influxDB.write(batchPoints);
	}
}
