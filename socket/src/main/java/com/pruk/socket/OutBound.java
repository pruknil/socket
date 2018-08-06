package com.pruk.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutBound extends ChannelOutboundHandlerAdapter{

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		super.write(ctx, msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        System.out.println("Closing ..");
        ctx.close(promise);
	}

}
