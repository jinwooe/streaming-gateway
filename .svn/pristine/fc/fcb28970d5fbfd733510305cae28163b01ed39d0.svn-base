package com.skcc.rtspgw.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TcpServerRequestDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(TcpServerRequestDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] tagBytes = new byte[4];
        in.readBytes(tagBytes);
        String tag = new String(tagBytes);

        int tokenLength = in.readInt();

        byte[] tokenBytes = new byte[tokenLength];
        in.readBytes(tokenBytes);
        String token = new String(tokenBytes);

        long cameraId = in.readLong();

        RequestData request = new RequestData(tag, tokenLength, token, cameraId);

        logger.info(request.toString());

        super.channelRead(ctx, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
