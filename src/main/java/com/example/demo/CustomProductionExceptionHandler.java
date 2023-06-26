package com.example.demo;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;

public class CustomProductionExceptionHandler implements ProductionExceptionHandler {

    @Override
    public ProductionExceptionHandlerResponse handle(final ProducerRecord<byte[], byte[]> record,
                                                     final Exception exception) {
        System.out.println("Kafka message marked as processed although it failed. Message: [{}], destination topic: [{}]"+exception);
        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(final Map<String, ?> configs) {
    	
    }

}
