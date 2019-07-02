package sample.tianye.opentsdb.client;

import java.io.IOException;

import sample.tianye.opentsdb.client.builder.MetricBuilder;
import sample.tianye.opentsdb.client.request.QueryBuilder;
import sample.tianye.opentsdb.client.response.Response;
import sample.tianye.opentsdb.client.response.SimpleHttpResponse;

public interface Client {

	String PUT_POST_API = "/api/put";

    String QUERY_POST_API = "/api/query";

	/**
	 * Sends metrics from the builder to the KairosDB server.
	 */
	Response pushMetrics(MetricBuilder builder) throws IOException;

	SimpleHttpResponse pushQueries(QueryBuilder builder) throws IOException;
}