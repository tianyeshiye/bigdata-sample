package sample.tianye.metrics.influxdb;

import java.util.Map;

public class InfluxdbMetricBean {

	/**
	 * timestamp = 1560386102
	 */
	private long timestamp;

	/**
	 * tags index <br>
	 * e.g. <br>
	 * Metrics=tsd.connectionmgr.connections<br>
	 * type=open<br>
	 * host=hadoop-data-01<br>
	 */
	private Map<String, String> tagsMap;

	/**
	 * field value <br>
	 * e.g.<br>
	 * value=2<br>
	 */
	private Map<String, Long> fieldsMap;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Long> getFieldsMap() {
		return fieldsMap;
	}

	public void setFieldsMap(Map<String, Long> fieldsMap) {
		this.fieldsMap = fieldsMap;
	}

	public Map<String, String> getTagsMap() {
		return tagsMap;
	}

	public void setTagsMap(Map<String, String> tagsMap) {
		this.tagsMap = tagsMap;
	}
}
