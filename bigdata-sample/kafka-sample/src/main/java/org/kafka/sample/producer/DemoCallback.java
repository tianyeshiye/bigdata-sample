package org.kafka.sample.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class DemoCallback implements Callback {
     private final long startTime;
     private final String key;
     private final String message;
 
     public DemoCallback() {
         this.startTime = System.currentTimeMillis();
         this.key = "";
         this.message = "";
     }
     
     public DemoCallback(long startTime, String key, String message) {
         this.startTime = startTime;
         this.key = key;
         this.message = message;
     }
 
     public void onCompletion(RecordMetadata metadata, Exception exception) {
         long elapsedTime = System.currentTimeMillis() - startTime;
         if (metadata != null) {
             System.out.println(
                     "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                             "), " +
                             "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
         } else {
             exception.printStackTrace();
         }
     }
 }
