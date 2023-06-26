package com.example.demo;

import org.apache.kafka.clients.producer.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class KafkaProducerSample {
	public static Properties loadConfig(final String configFile) throws IOException {
		if (!Files.exists(Paths.get(configFile))) {
			throw new IOException(configFile + " not found.");
		}
		final Properties cfg = new Properties();
		try (InputStream inputStream = new FileInputStream(configFile)) {
			cfg.load(inputStream);
		}
		return cfg;
	}

	

	public static void main(String args[]) {
		Properties props =null;
		try {
			 props = loadConfig("client.properties");
			 props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			 props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		} catch (Exception e) {

		}
		Producer<String, String> producer = (Producer<String, String>) new KafkaProducer(props);
		 Map<String, String> countries = new HashMap<>();
	        countries.put("1606", "a");
	        countries.put("kk 2Kingdom1", "b");
	        countries.put("2", "c");
	        countries.put("cc12", "d");
	        countries.put("pp12", "e");
        for (Map.Entry<String, String> entry : countries.entrySet()) {
            String country = entry.getKey();
            String capital = entry.getValue();
            System.out.println("Country: " + country + ", Capital: " + capital);
            producer.send(new ProducerRecord<>("streams_input", country, capital));
        }
		
		
		
		
		
		producer.close();
	}
}