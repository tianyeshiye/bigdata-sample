package sample.tianye.opentsdb.client;

import java.io.IOException;

import sample.tianye.opentsdb.client.builder.MetricBuilder;
import sample.tianye.opentsdb.client.request.QueryBuilder;
import sample.tianye.opentsdb.client.response.Response;
import sample.tianye.opentsdb.client.response.SimpleHttpResponse;

public interface HttpClient extends Client {

	public Response pushMetrics(MetricBuilder builder,
			ExpectResponse exceptResponse) throws IOException;

	public SimpleHttpResponse pushQueries(QueryBuilder builder,
                                          ExpectResponse exceptResponse) throws IOException;
}