package com.github.odilonjk.tutorial;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class NewTask {

	private static final String QUEUE = "my-queue";

	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException, InterruptedException {
		
		ChannelFactory channelFactory = new ChannelFactory();
		
		Channel channel = channelFactory.createChannel();
		
		Random rand = new Random();

		int count = 0;
		
		while(count < 100) {
			int dotsQuantity = rand.nextInt(5) + 1;
			StringBuilder sb = new StringBuilder("Task");
			for(int i=0; i<dotsQuantity; i++) {
				sb.append(".");
			}
			channel.basicPublish("", QUEUE, null, sb.toString().getBytes());
			count++;
			System.out.println("Sended task with " + dotsQuantity + " seconds of work.");
			
			Thread.sleep(500);
			
		}

		channel.close();
		channel.getConnection().close();
	}

	
}
