package com.example.demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class KafkaStreamsExample {
	private static final Logger logger = LogManager.getLogger(KafkaStreamsExample.class);

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

	public static void main(String[] args) throws InterruptedException {
		// Set up configuration properties for Kafka Streams
		logger.info("hello: ");

		Properties config = null;
		try {
			config = loadConfig("client.properties");

		} catch (Exception e) {

		}

		config.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams-5678");
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG,CustomProductionExceptionHandler.class );

		// Build the topology of the Kafka Streams application
		Serde<String> stringSerde = Serdes.String();
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> inputStream = builder.stream("streams_input");
		KStream<String, String> outputStream = inputStream.mapValues(value -> {
			 if (value.equalsIgnoreCase("paris2")) {
			        //throw new StreamsException("Custom exception message");
				try {
					Thread.sleep(10000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    }
			 return value;
		});
		logger.info("Observed event: ");
		
		outputStream.to("streams_output", Produced.with(stringSerde, stringSerde));
		outputStream.print(Printed.toSysOut());
		
		/*
		 * builder .stream("streams_input", Consumed.with(stringSerde, stringSerde))
		 * .peek((k,v) -> System.out.println("Observed event: {}"+ v)) .mapValues(s ->
		 * s.toUpperCase()) .peek((k,v) ->
		 * System.out.println("Transformed event: {}"+v)) .to("stream_output",
		 * Produced.with(stringSerde, stringSerde));
		 */
		// Create a Kafka Streams instance with the configuration
		KafkaStreams streams = new KafkaStreams(builder.build(), config);
		//streams.setUncaughtExceptionHandler(new StreamsCustomUncaughtExceptionHandler());
		// Start the Kafka Streams application
		streams.start();

		// Add shutdown hook to gracefully close the streams application
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	}
}
