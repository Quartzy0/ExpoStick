package com.quartzy.expostick.netty;

import com.quartzy.expostick.netty.packets.Packet;
import com.quartzy.expostick.netty.packets.Packets;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }
        int id =  in.readInt();
        Packet packetById = Packets.getPacketById(id);
        if(packetById!=null){
            out.add(packetById.decode(ctx, in, out, id));
        }
    }
}
