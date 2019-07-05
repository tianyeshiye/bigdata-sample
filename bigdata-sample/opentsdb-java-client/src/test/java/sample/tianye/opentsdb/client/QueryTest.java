package sample.tianye.opentsdb.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import sample.tianye.opentsdb.client.request.QueryBuilder;
import sample.tianye.opentsdb.client.request.SubQueries;
import sample.tianye.opentsdb.client.response.SimpleHttpResponse;
import sample.tianye.opentsdb.client.util.Aggregator;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class QueryTest {
    @Test
    public void queryTest() throws IOException {
        HttpClientImpl client = new HttpClientImpl("http://hadoop-data-01:4242");


        QueryBuilder builder = QueryBuilder.getInstance();
        SubQueries subQueries = new SubQueries();
        String zimsum = Aggregator.zimsum.toString();
//        subQueries.addMetric("realtime_train_status").addAggregator(zimsum).addDownsample("1s-" +
//                zimsum);
        subQueries.addMetric("realtime_train_status").addAggregator(zimsum).addDownsample("1s-" +
              zimsum);
        long now = new Date().getTime() / 1000;
        builder.getQuery().addStart(126358720).addEnd(now).addSubQuery(subQueries);
        System.out.println(builder.build());

        try {
            SimpleHttpResponse response = client.pushQueries(builder,
                    ExpectResponse.STATUS_CODE);
            String content = response.getContent();
            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                JSONArray jsonArray = JSON.parseArray(content);
                for (Object object : jsonArray) {
                    JSONObject json = (JSONObject) JSON.toJSON(object);
                    String dps = json.getString("dps");
                    Map<String, String> map = JSON.parseObject(dps, Map.class);
                    for (Map.Entry entry : map.entrySet()) {
                        System.out.println("Time:" + entry.getKey() + ",Value:" + entry.getValue());
                        Double.parseDouble(String.valueOf(entry.getValue()));
                    }
                }
            }
            //System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
