package com.github.odilonjk.tutorial;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelFactory {

	private ConnectionFactory factory = new ConnectionFactory();
	
	private Connection createConnection() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		factory.setUri("amqp://guest:guest@localhost");
		factory.setConnectionTimeout(300000);
		return factory.newConnection();
	}

	public Channel createChannel() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
		Connection connection = createConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("my-queue", true, false, false, null);
		return channel;
	}
	
}
