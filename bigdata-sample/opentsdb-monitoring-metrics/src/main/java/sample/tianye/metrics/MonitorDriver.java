package sample.tianye.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sample.tianye.metrics.influxdb.InfluxdbMetricBean;
import sample.tianye.metrics.influxdb.MetricsSave;
import sample.tianye.metrics.opentsdb.MetricsCapture;
import sample.tianye.metrics.opentsdb.OpentsdbMetricBean;

public class MonitorDriver {

	private String configFilepath;

	private MetricsCapture metricsCapture;
	private MetricsSave metricsSave;

	public MonitorDriver(String configFilepathPara) {

		this.configFilepath = configFilepathPara;

		AppConfig appConfig = AppConfig.getInstance();
		appConfig.initConfig(configFilepath);

		metricsCapture = new MetricsCapture();
		metricsSave = new MetricsSave();
	}

	public void call() {

		AppConfig appConfig = AppConfig.getInstance();

		int interval = appConfig.getMetricInterval();

		long startTime = 0;
		long endTime = System.currentTimeMillis();

		while (true) {

			if (endTime - startTime > interval) {
				startTime = endTime;
				monitor();
			} else {
				// try {
				// //Thread.sleep(50L);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}

			endTime = System.currentTimeMillis();
		}
	}

	private void monitor() {

		List<OpentsdbMetricBean> opentsdbMetricBeanList = metricsCapture.call();

		List<InfluxdbMetricBean> influxdbMetricBeanList = convertToInfluxdbMetric(opentsdbMetricBeanList);

		metricsSave.call(influxdbMetricBeanList);
	}

	private List<InfluxdbMetricBean> convertToInfluxdbMetric(List<OpentsdbMetricBean> opentsdbMetriBeanList) {

		List<InfluxdbMetricBean> influxdbMetricList = new ArrayList<>();

		opentsdbMetriBeanList.forEach(opentsdbMetriBean -> {

			if (filterMetric(opentsdbMetriBean)) {
				influxdbMetricList.add(convertToInfluxdbMetric(opentsdbMetriBean));
			}
		});

		return influxdbMetricList;
	}

	private boolean filterMetric(OpentsdbMetricBean opentsdbMetriBean) {

		AppConfig appConfig = AppConfig.getInstance();

		List<String> monitorMetricList = appConfig.getMetricList();

		return monitorMetricList.contains(opentsdbMetriBean.getMetric());
	}

	private InfluxdbMetricBean convertToInfluxdbMetric(OpentsdbMetricBean opentsdbMetriBean) {

		InfluxdbMetricBean influxdbMetricBean = new InfluxdbMetricBean();

		influxdbMetricBean.setTimestamp(opentsdbMetriBean.getTimestamp());

		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put("Metrics", opentsdbMetriBean.getMetric());
		tagsMap.putAll(opentsdbMetriBean.getTags());
		influxdbMetricBean.setTagsMap(tagsMap);

		Map<String, Long> fieldsMap = new HashMap<String, Long>();
		fieldsMap.put("value", Long.parseLong(opentsdbMetriBean.getValue()));
		influxdbMetricBean.setFieldsMap(fieldsMap);

		return influxdbMetricBean;
	}

}
