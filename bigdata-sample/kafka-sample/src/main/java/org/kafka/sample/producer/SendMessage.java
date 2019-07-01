package org.kafka.sample.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SendMessage {

	public static void main(String[] args) throws Exception {

		SendMessage sendMessage = new SendMessage();

		while (true) {
			sendMessage.sendMsgSyn();
			Thread.sleep(10L);
		}
	}

	// 同步发送
	public void sendMsgSyn() {

		Properties kafkaProps = getProperties();

		// 实例化出producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);

		ProducerRecord<String, String> record = new ProducerRecord<String, String>("topicTest", "Hello",
				getTestValue());
		try {
			// 发送消息，调用get() 方法等待
			producer.send(record).get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

	// 异步发送
	public void sendMsgAsyn() {

		Properties kafkaProps = getProperties();

		// 实例化出producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProps);

		ProducerRecord<String, String> record = new ProducerRecord<String, String>("topicTest", "Hello",
				getTestValue());

		try {
			producer.send(record, new DemoCallback());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

	private Properties getProperties() {

		Properties kafkaProps = new Properties();
		// 指定broker
		kafkaProps.put("bootstrap.servers", "hadoop-data-01:9092,hadoop-data-02:9092,hadoop-data-03:9092");

		kafkaProps.put("acks", "all");
		kafkaProps.put("retries", 0);

		// 自定义partitioner
		// kafkaProps.put("partitioner.class", "com.crrc.MyPartitioner");

		// 消费者群组
		kafkaProps.put("group.id", "groupTest");
		// 设置序列化
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		return kafkaProps;
	}

	private String getTestValue() {
		
		long l = 190618135030L;
		
		String str = "{\"p_id\":109,\"train_id\":\"NLCJ_T10\",\"time\":\""+    String.valueOf(l++)    +"\",\"v\":{\"1\":{\"v_id\":1,\"value\":\"1\"},\"2\":{\"v_id\":2,\"value\":\"2\"}}}";
		
		//str = "{\"id\":109,\"definition\":\"NLCJ_T10\"}";
		
		return str;
	}

}
