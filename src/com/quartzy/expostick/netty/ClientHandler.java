package com.quartzy.expostick.netty;

import com.quartzy.expostick.netty.packets.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter{
    private ChannelHandlerContext ctx;
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof Packet){
            Packet msg1 = (Packet) msg;
            PacketReceivedEvent o = NettyHandler.getListeners().get(msg1.getID());
            if(o!=null){
                o.packetReceived(msg1);
            }
        }
    }
    
    public void sendPacket(Packet packet){
        if(ctx!=null){
            ctx.writeAndFlush(packet);
        }
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
