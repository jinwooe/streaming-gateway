package com.skcc.rtspgw.tcp;

import com.skcc.rtspgw.service.TokenValidationService;
import com.skcc.rtspgw.stream.StreamManagerActivator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    private TokenValidationService tokenValidationService;

    private StreamManagerActivator streamManagerActivator;

    public TcpServerHandler(TokenValidationService tokenValidationService, StreamManagerActivator streamManagerActivator) {
        this.tokenValidationService = tokenValidationService;
        this.streamManagerActivator = streamManagerActivator;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestData requestData = (RequestData) msg;

        if(!tokenValidationService.validate(requestData.getToken())){
            FailedResponse failedResponse = new FailedResponse("Invalid token");
            ctx.writeAndFlush(failedResponse);
            return;
        }

        streamManagerActivator.activateStreamManagerForRgb(requestData.getCameraId(), ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
