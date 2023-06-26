/*
 * package com.example.demo;
 * 
 * import org.apache.kafka.streams.errors.StreamsException; import
 * org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler; import
 * org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.
 * StreamThreadExceptionResponse;
 * 
 * public class StreamsCustomUncaughtExceptionHandler implements
 * StreamsUncaughtExceptionHandler {
 * 
 * @Override public StreamThreadExceptionResponse handle(Throwable exception) {
 * System.out.println(exception.getMessage()); if (exception instanceof
 * StreamsException) { // Throwable originalException = exception.getCause(); //
 * System.out.println(exception.getMessage()); //
 * System.out.println("Inside StreamsCustomExcptiohandler"); if
 * (exception.getMessage().equals("Custom exception message")) { return
 * StreamThreadExceptionResponse.REPLACE_THREAD; } } return
 * StreamThreadExceptionResponse.SHUTDOWN_CLIENT; }
 * 
 * }
 */
