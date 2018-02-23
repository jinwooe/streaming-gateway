package com.skcc.rtspgw.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServerFailedResponseEncoder extends MessageToByteEncoder<FailedResponse> {
    private static Logger logger = LoggerFactory.getLogger(TcpServerFailedResponseEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, FailedResponse msg, ByteBuf out) throws Exception {
        logger.debug(msg.toString());

        out.writeBytes(msg.getTag().getBytes());
        out.writeInt(msg.getMessageLength());
        out.writeBytes(msg.getMessage().getBytes());

        ctx.flush();
    }
}
