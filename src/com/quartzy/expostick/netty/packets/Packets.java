package com.quartzy.expostick.netty.packets;

public class Packets{
    
    public static final Packet[] packets = {new AuthPacket(), new WorldDataPacket(), new PlayerRequestMovement()};
    
    public static Packet getPacketById(int id){
        for(Packet packet : packets){
            if(packet.getID()==id)return packet;
        }
        return null;
    }
}
