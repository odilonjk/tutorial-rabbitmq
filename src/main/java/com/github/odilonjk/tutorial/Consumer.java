package com.github.odilonjk.tutorial;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		
		ChannelFactory channelFactory = new ChannelFactory();
		
		Channel channel = channelFactory.createChannel();
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume("my-queue", false, consumer);
		
		while(true) {
			Delivery delivery = consumer.nextDelivery();
			if(delivery != null) {
				try {
					String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
					System.out.println("Message consumed: " + message);
					
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				} catch(Exception e) {
					channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
				}
			}
		}
		
	}
	
}
