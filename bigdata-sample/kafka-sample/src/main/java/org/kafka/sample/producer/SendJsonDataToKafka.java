package org.kafka.sample.producer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SendJsonDataToKafka {

	public static void main(String[] args) {

		SendMessage sendMessage = new SendMessage();
		sendMessage.sendMsgSyn();
	}
	
	
	private String getJson(int i) {

		String pattern = "YYYY-MM-dd:HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		// get Runtime instance
		Runtime instance = Runtime.getRuntime();
		String json = "";

		String date = simpleDateFormat.format(new Date());

		long usedMem = instance.availableProcessors();
		json = "" + "{" + "\"timestamp\": \"" + date + "\"" + ", \"type\": \"Heap utilization statistics\""
				+ ",\"level\":  " + i + "," + "\"message\": \"Available Processors:\"" + usedMem + "}" + "";

		return json;

	}
}
