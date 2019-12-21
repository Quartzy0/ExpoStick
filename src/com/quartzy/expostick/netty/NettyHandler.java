package com.quartzy.expostick.netty;

import com.quartzy.expostick.netty.packets.AuthPacket;
import com.quartzy.expostick.netty.packets.Packet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NettyHandler{
    
    private ClientHandler clientHandler;
    private static HashMap<Integer, PacketReceivedEvent> listeners = new HashMap<>();
    private final Lock _mutex = new ReentrantLock(true);
    
    private static List<Packet> packetQueue = new ArrayList<>();
    
    public void connect(String host, int port){
        Thread nettyThread = new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            
            try{
                Bootstrap b = new Bootstrap();
                b.group(workerGroup);
                b.channel(NioSocketChannel.class);
                b.option(ChannelOption.SO_KEEPALIVE, true);
                b.handler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    public void initChannel(SocketChannel ch){
                        clientHandler = new ClientHandler();
                        ch.pipeline().addLast(new Decoder(), new Encoder(), clientHandler);
                    }
                });
                
                ChannelFuture f = b.connect(host, port).sync();
    
                System.out.println("Authenticating!");
                sendPacket(new AuthPacket("Hi", "Bye"));
                
                Thread nettyPacketThread = new Thread(() -> {
                    long lastTimeSent = -1L;
                    while(true){
                        _mutex.lock();
                        if(!packetQueue.isEmpty() && (lastTimeSent==-1L || System.currentTimeMillis()-lastTimeSent>=50)){
                            clientHandler.sendPacket(packetQueue.get(0));
                            System.out.println("Sent packet");
                            packetQueue.remove(0);
                            lastTimeSent = System.currentTimeMillis();
                        }
                        _mutex.unlock();
                    }
                });
                nettyPacketThread.setDaemon(true);
                nettyPacketThread.setName("Netty Packet Thread");
                nettyPacketThread.start();
                
                f.channel().closeFuture().sync();
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally{
                workerGroup.shutdownGracefully();
            }
        });
        nettyThread.setName("Netty Thread");
        nettyThread.setDaemon(true);
        nettyThread.start();
    }
    
    static HashMap<Integer, PacketReceivedEvent> getListeners(){
        return listeners;
    }
    
    public void sendPacket(Packet packet){
        _mutex.lock();
        for(int i = 0; i < packetQueue.size(); i++){
            if(packetQueue.get(i).getID()==packet.getID()){
                packetQueue.set(i, packet);
                _mutex.unlock();
                return;
            }
        }
        packetQueue.add(packet);
        _mutex.unlock();
    }
    
    public static void setPacketListener(PacketReceivedEvent event, int packetID){
        if(listeners.get(packetID)!=null){
            listeners.remove(packetID);
        }
        listeners.put(packetID, event);
    }
}
