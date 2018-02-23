package com.skcc.rtspgw.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes("RQST".getBytes());
        buffer.writeInt(16);
        buffer.writeBytes("0123456789abcdef".getBytes());
        buffer.writeLong(100000000000L);

        ctx.writeAndFlush(buffer);
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buffer = (ByteBuf)msg;
//        int bytesLength = buffer.readableBytes();
//
//        System.out.println("read bytes: " + bytesLength);
//
//        buffer = null;
//
//    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
