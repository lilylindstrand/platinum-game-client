package dev.hepno.platinum_game_client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SocketUtils;

public class UdpClient {

    static final int PORT = 7777;
    private EventLoopGroup eventLoopGroup;
    private Bootstrap bootstrap;
    private Channel channel;

    public void run() throws Exception {

        eventLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientHandler());
            channel = bootstrap.bind(0).sync().channel();

            // Broadcast the request to port 8080
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8),
                    SocketUtils.socketAddress("127.0.0.1", PORT))).sync();

            // Timeout
            if (!channel.closeFuture().await(5000)) {
                System.err.println("Request timed out.");
            }
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

}
