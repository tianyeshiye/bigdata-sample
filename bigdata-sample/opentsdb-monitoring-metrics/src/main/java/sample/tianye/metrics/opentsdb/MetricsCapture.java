package sample.tianye.metrics.opentsdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sample.tianye.metrics.utils.ConnectType;
import sample.tianye.metrics.utils.Telnets;
import sample.tianye.metrics.AppConfig;

public class MetricsCapture {

	// e.g. "http://localhost:4242/api/stats"
	// private List<String> urlList;

	private AppConfig appConfig;

	private ConnectType connetcType;

	public MetricsCapture() {

		this.appConfig = AppConfig.getInstance();

		connetcType = appConfig.getConnectType();
	}

	public List<OpentsdbMetricBean> call() {

		List<OpentsdbMetricBean> metricList = new ArrayList<OpentsdbMetricBean>();

		switch (connetcType) {

		case HTTP:
			List<String> urlList = appConfig.getOpenTSDBUrlList();
			urlList.forEach(url -> {
				metricList.addAll(captureFromOpenTSDBByHttp(url));
			});
			break;
		case TELNET:

			List<String> telnetList = appConfig.getOpenTSDBTelnetList();
			telnetList.forEach(telnetStr -> {
				metricList.addAll(captureFromOpenTSDBByTelnet(telnetStr));
			});
			break;
		}

		return metricList;
	}

	private List<OpentsdbMetricBean> captureFromOpenTSDBByHttp(String url) {

		OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder().url(url).get().build();

		String jsonStr = "";

		List<OpentsdbMetricBean> list = new ArrayList<OpentsdbMetricBean>();
		while (true) {

			list = new ArrayList<OpentsdbMetricBean>();

			try (Response response = okHttpClient.newCall(request).execute()) {
				jsonStr = response.body().string();

				list = JSON.parseArray(jsonStr, OpentsdbMetricBean.class);

				if (list.size() == 0) {
					continue;
				}

				break;
			} catch (IOException e) {
				continue;
			}
		}

		// test鏃跺�欏埄鐢�
		// jsonStr = "["
		// + "{\"metric\": \"tsd.connectionmgr.connections\", \"timestamp\":
		// 1369350222, \"value\": \"100\", \"tags\": { \"host\": \"wtdb-1-4\" }
		// },"
		// + "{\"metric\": \"tsd.rpc.received\", \"timestamp\": 1369350222,
		// \"value\": \"200\", \"tags\": { \"host\": \"wtdb-1-4\", \"type\":
		// \"telnet\" } }"
		// + "]";

		return list;
	}

	private List<OpentsdbMetricBean> captureFromOpenTSDBByTelnet(String telnetStr) {

		String[] telnetArr = telnetStr.split(":");

		String ip = telnetArr[0];
		String port = telnetArr[1];
		String user = appConfig.getOpenTSDBUser();
		String pwd = appConfig.getOpenTSDBPwd();

		Telnets telnets = new Telnets(ip, port, user, pwd);
		telnets.connection();
		String resultStr = telnets.execute("/api/stats");
		telnets.close();

		List<OpentsdbMetricBean> list = JSON.parseArray(resultStr, OpentsdbMetricBean.class);
		return list;
	}
}
