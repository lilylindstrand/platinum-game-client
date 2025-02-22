package dev.hepno.platinum_game_client;

import dev.hepno.platinum_api.packet.Packet;
import dev.hepno.platinum_api.packet.PacketType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SocketUtils;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;

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
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    public void write(Packet packet) throws InterruptedException {
        ByteBuf byteBuf = Unpooled.buffer();
        PacketType packetType = PacketType.getPacketType(packet);
        byteBuf.writeInt(packetType.ordinal());
        packet.encode(byteBuf);

        DatagramPacket datagramPacket = new DatagramPacket(
                byteBuf,
                new InetSocketAddress("127.0.0.1", PORT)
        );

        channel.writeAndFlush(datagramPacket).sync();
    }

}
