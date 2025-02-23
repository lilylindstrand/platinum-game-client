package dev.hepno.platinum_game_client.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    public void channelRead0(ChannelHandlerContext context, DatagramPacket datagramPacket) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
