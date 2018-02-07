package com.github.odilonjk.tutorial;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class Publisher {

	private static final String QUEUE = "my-queue";

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException, InterruptedException {
		
		ChannelFactory channelFactory = new ChannelFactory();
		
		Channel channel = channelFactory.createChannel();
		
		int count = 0;
		
		while(count < 50) {
			String message = "Message numer " + count;
			channel.basicPublish("", QUEUE, null, message.getBytes());
			count++;
			System.out.println("Published message: " + message);
			
			Thread.sleep(500);
		}

		channel.close();
		channel.getConnection().close();
	}
	
}
