package com.quartzy.expostick.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public abstract class Packet{
    private int ID;
    
    public Packet(int ID){
        this.ID = ID;
    }
    
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf out){
        out.writeInt(ID);
    }
    
    public Packet decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, int idIn){
        return null;
    }
    
    @Override
    public String toString(){
        return getClass().getName() + " - " + getID();
    }
    
    public int getID(){
        return ID;
    }
    
    private void setID(int ID){
        this.ID = ID;
    }
}
