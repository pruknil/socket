package com.pruk.socket;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import io.netty.channel.ChannelFuture;

@SpringBootApplication
public class NettyWebSocketServerApplication implements CommandLineRunner{

	@Autowired
	private SocketServer chatServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyWebSocketServerApplication.class, args);
    }
    
    @Bean
    public SocketServer chatServer() {
    	return new SocketServer();
    }
    
	@Override
	public void run(String... args) throws Exception {
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9090);
		ChannelFuture future = chatServer.start(address);
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				chatServer.destroy();
			}
		});
		
		future.channel().closeFuture().syncUninterruptibly();
	}
}