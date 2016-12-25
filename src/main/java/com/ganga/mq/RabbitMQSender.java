package com.ganga.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSender {
	private final static String QUEUE_NAME = "gangaTestQ";

	public static void main(String[] argv) {
		
		/*
		 * Establishing a Connection with RabbitMQ server localhost:15672
		 */
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();			
			//channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Message sent from RabbitMQSender,java";
			// publishing the message (bytes)
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("Message sent to RabbitMQ: '" + message + "'"+ " Queue Name : " + QUEUE_NAME);
			channel.close();
			connection.close();
		} catch (IOException e) {
			System.out.println("RabbitMQ server is Down !");
			System.out.println(e.getMessage());
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

}
