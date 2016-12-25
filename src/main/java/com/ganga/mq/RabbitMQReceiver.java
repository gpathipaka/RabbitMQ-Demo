package com.ganga.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMQReceiver {
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
			// creating a channel with Queue
			Channel channel = connection.createChannel();
			// Declate new queue if you don't have one.
			// channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println("Waiting for messages.");
			// creating the Consumer, that will be receive a message and convert
			// to String
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println("Message Received '" + message + "'" + " from Queue : " + QUEUE_NAME);
				}
			};
			// loop that waits for message
			channel.basicConsume(QUEUE_NAME, true, consumer);
		} catch (IOException e) {
			System.out.println("RabbitMQ server is Down");
			System.out.println(e.getMessage());
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
