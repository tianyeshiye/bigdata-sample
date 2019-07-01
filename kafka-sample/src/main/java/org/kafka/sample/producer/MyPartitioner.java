package org.kafka.sample.producer;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

public class MyPartitioner implements Partitioner {

	// @Override
	public void configure(Map<String, ?> configs) {
	}

	// @Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		/**
		 * 由于我们按key分区，在这里我们规定：key值不允许为null。在实际项目中，key为null的消息*，可以发送到同一个分区。
		 */
		if (keyBytes == null) {
			throw new InvalidRecordException("key cannot be null");
		}
		if (((String) key).equals("1")) {
			return 1;
		}
		// 如果消息的key值不为1，那么使用hash值取模，确定分区。
		return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
	}

	// @Override
	public void close() {
	}

}