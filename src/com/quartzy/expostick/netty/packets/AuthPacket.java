package com.quartzy.expostick.netty.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class AuthPacket extends Packet{
    
    private String authToken;
    private String uuid;
    
    
    public AuthPacket(){
        super(1111);
    }
    
    public AuthPacket(String authToken, String uuid){
        super(1111);
        this.authToken = authToken;
        this.uuid = uuid;
    }
    
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf out){
        out.writeInt(authToken.length());
        out.writeCharSequence(authToken, StandardCharsets.UTF_8);
        out.writeInt(uuid.length());
        out.writeCharSequence(uuid, StandardCharsets.UTF_8);
    }
    
    @Override
    public Packet decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, int idIn){
        int authTokenLen = in.readInt();
        String authToken = in.readCharSequence(authTokenLen, StandardCharsets.UTF_8).toString();
        int uuidLen = in.readInt();
        String uuid = in.readCharSequence(uuidLen, StandardCharsets.UTF_8).toString();
        return new AuthPacket(authToken, uuid);
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    
    public String getAuthToken(){
        return authToken;
    }
    
    public String getUuid(){
        return uuid;
    }
}
