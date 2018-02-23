package com.skcc.rtspgw.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServerJpegEncoder extends MessageToByteEncoder<ResponseData> {
    private static Logger logger = LoggerFactory.getLogger(TcpServerJpegEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        logger.debug(msg.toString());

        out.writeBytes(msg.getTag().getBytes());
        out.writeInt(msg.getWidth());
        out.writeInt(msg.getHeight());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getBytes());

        ctx.flush();
    }
}
