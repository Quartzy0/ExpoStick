package com.quartzy.expostick.netty;

import com.quartzy.expostick.netty.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<Packet>{
    
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packetIn, ByteBuf out){
        out.writeInt(packetIn.getID());
        packetIn.encode(channelHandlerContext, out);
    }
}
