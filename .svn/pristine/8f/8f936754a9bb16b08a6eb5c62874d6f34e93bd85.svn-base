package com.skcc.rtspgw.tcp;

import com.skcc.rtspgw.service.TokenValidationService;
import com.skcc.rtspgw.stream.StreamManagerActivator;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component("tcpHandler")
@Order(2)
public class TcpServer implements CommandLineRunner {
    @Value("${tcp.port}")
    private int port;

    @Autowired
    private TokenValidationService tokenValidationService;

    @Autowired
    private StreamManagerActivator streamManagerActivator;

    private EventLoopGroup parentGroup;
    private EventLoopGroup workerGroup;

    public TcpServer() {
        parentGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(2);

            serverBootstrap.group(parentGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast(new TcpServerJpegEncoder(), new TcpServerFailedResponseEncoder(), new TcpServerRequestDecoder());
                            channelPipeline.addLast(eventExecutorGroup, "tcpServerHandler", new TcpServerHandler(tokenValidationService, streamManagerActivator));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }
        finally {
            workerGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void close() {
        workerGroup.shutdownGracefully();
        parentGroup.shutdownGracefully();
    }
}
