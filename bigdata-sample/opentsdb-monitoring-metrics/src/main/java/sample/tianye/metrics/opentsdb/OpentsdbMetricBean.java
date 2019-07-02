package sample.tianye.metrics.opentsdb;

import java.util.Map;

public class OpentsdbMetricBean {

	private String metric;
	private long timestamp;
	private String value;
	private Map<String, String> tags;

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		
		this.metric = metric;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
}
