package com.quartzy.expostick.netty.packets;

import com.quartzy.expostick.utills.MovementType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class PlayerRequestMovement extends Packet{
    
    private UUID entityId;
    private MovementType movementType;
    
    public PlayerRequestMovement(){
        super(23454);
    }
    
    public PlayerRequestMovement(UUID entityId, MovementType movementType){
        super(23454);
        this.entityId = entityId;
        this.movementType = movementType;
    }
    
    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf out){
        String s = entityId.toString();
        out.writeInt(s.length());
        out.writeCharSequence(s, StandardCharsets.UTF_8);
        out.writeInt(movementType.ordinal());
    }
    
    @Override
    public Packet decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, int idIn){
        int idLen = in.readInt();
        UUID entityId1 = UUID.fromString(in.readCharSequence(idLen, StandardCharsets.UTF_8).toString());
        MovementType movementType1 = MovementType.values()[in.readInt()];
        return new PlayerRequestMovement(entityId1, movementType1);
    }
    
    @Override
    public String toString(){
        return super.toString() + " - " + entityId.toString() + " - " + movementType.toString();
    }
    
    public UUID getEntityId(){
        return entityId;
    }
    
    public MovementType getMovementType(){
        return movementType;
    }
}
