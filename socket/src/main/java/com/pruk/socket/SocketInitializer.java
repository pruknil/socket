package com.pruk.socket;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class SocketInitializer extends ChannelInitializer<Channel>{
	private final ChannelGroup group;
	
	public SocketInitializer(ChannelGroup group) {
		this.group = group;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LoggingHandler(LogLevel.INFO));
		
		pipeline.addLast(new IdleStateHandler(0, 0, 1800, TimeUnit.SECONDS));
		
	    pipeline.addLast("length-decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2)); 
	    pipeline.addLast("bytearray-decoder", new ByteArrayDecoder());
	    pipeline.addLast("length-encoder", new LengthFieldPrepender(2));  
	    pipeline.addLast("bytearray-encoder", new ByteArrayEncoder());
		pipeline.addLast(new HeartbeatHandler());
		pipeline.addLast(new LengthFieldHandler());
		//pipeline.addLast(new SocketHandler());

		
		//pipeline.addLast(new OutBound());
		
		
		//pipeline.addLast(new HttpServerCodec());
		//pipeline.addLast(new ChunkedWriteHandler());
		//pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		//pipeline.addLast(new HttpRequestHandler("/ws"));
		//pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		//pipeline.addLast(new TextWebSocketFrameHandler(group));		
	}
}