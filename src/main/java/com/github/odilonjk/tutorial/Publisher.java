package com.github.odilonjk.tutorial;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class Publisher {

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException, InterruptedException {
		
		ChannelFactory channelFactory = new ChannelFactory();
		
		Channel channel = channelFactory.createChannel();
		
		int count = 0;
		
		while(count < 5000) {
			String message = "Message numer " + count;
			channel.basicPublish("", "my-queue", null, message.getBytes());
			count++;
			System.out.println("Published message: " + message);
			
			Thread.sleep(5000);
		}
	}
	
}
