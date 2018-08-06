package com.pruk.socket;
import java.net.InetSocketAddress;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

@Component
public class SocketServer {
	private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	private Channel channel;
	
	public ChannelFuture start(InetSocketAddress address) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new SocketInitializer(channelGroup))
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		ChannelFuture future = bootstrap.bind(address).syncUninterruptibly();
		channel = future.channel();
		return future;
	}
	
	public void destroy() {
		if(channel != null) {
			channel.close();
		}
		
		channelGroup.close();
		workGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

}