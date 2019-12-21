package com.quartzy.expostick.netty;

import com.quartzy.expostick.netty.packets.Packet;

public interface PacketReceivedEvent{
    
    void packetReceived(Packet packet);
}
