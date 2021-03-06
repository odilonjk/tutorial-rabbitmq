package com.github.odilonjk.tutorial;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Worker {

	private static final String ENCODING = "UTF-8";
	private static final String QUEUE = "my-queue";

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ChannelFactory channelFactory = new ChannelFactory();
		
		Channel channel = channelFactory.createChannel();
		
		channel.queueDeclare(QUEUE, true, false, false, null);
		
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, 
					byte[] body) throws IOException {
				
				String message = new String(body, ENCODING);
				System.out.println("Received: " + message);
		
				try {
					doWork(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("Work done!");
				}
				
			};
		};
	
		channel.basicConsume(QUEUE, true, consumer);
		
	}

	private static void doWork(String task) throws InterruptedException {
		for(char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}
	
}
