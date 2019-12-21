package com.quartzy.expostick.netty.packets;

import com.quartzy.expostick.entities.*;
import com.quartzy.expostick.entities.Robot;
import com.quartzy.expostick.tiles.Tile;
import com.quartzy.expostick.tiles.Tiles;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;
import com.quartzy.expostick.world.World;
import com.quartzy.expostick.world.WorldData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldDataPacket extends Packet{
    
    private World world;
    private UUID you;
    
    public WorldDataPacket(){
        super(8732874);
    }
    
    public WorldDataPacket(World world, UUID you){
        super(8732874);
        this.world = world;
        this.you = you;
    }
    
    @Override
    public Packet decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out, int idIn){
        UUID youId = null;
        if(in.readBoolean()){
            int youLen = in.readInt();
            youId = UUID.fromString(in.readCharSequence(youLen, StandardCharsets.UTF_8).toString());
        }
        int entityLength = in.readInt();
        List<Entity> entityDataList = new ArrayList<>();
        for(int i = 0;i<entityLength;i++){
            int health = in.readInt();
            int state = in.readInt();
            int typeId = in.readInt();
            int weaponId = in.readInt();
            int hitboxWidth = in.readInt();
            int hitboxHeight = in.readInt();
            int hitboxX = in.readInt();
            int hitboxY = in.readInt();
            int idLen = in.readInt();
            UUID id = UUID.fromString(in.readCharSequence(idLen, StandardCharsets.UTF_8).toString());
            int nameLen = in.readInt();
            String name = in.readCharSequence(nameLen, StandardCharsets.UTF_8).toString();
            int posX = in.readInt();
            int posY = in.readInt();
            int velX = in.readInt();
            int velY = in.readInt();
            switch(typeId){
                case 0:
                    Player e2 = new Player(new Vector2(posX, posY), 1, new Vector2(velX, velY), null);
                    e2.setHealth(health);
                    e2.setHitbox(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
                    e2.setId(id);
                    if(id.equals(youId)) Handler.setPlayer(e2);
                    entityDataList.add(e2);
                    break;
                case 1:
                    Robot e1 = new Robot(new Vector2(posX, posY), 1, new Vector2(velX, velY), null);
                    e1.setHealth(health);
                    e1.setHitbox(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
                    e1.setId(id);
                    entityDataList.add(e1);
                    break;
                case 2:
                    BallRobot e = new BallRobot(new Vector2(posX, posY), 1, new Vector2(velX, velY), null);
                    e.setHealth(health);
                    e.setHitbox(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
                    e.setId(id);
                    entityDataList.add(e);
                    break;
            }
        }
        int nameLen = in.readInt();
        String name = in.readCharSequence(nameLen, StandardCharsets.UTF_8).toString();
        int tilesLen = in.readInt();
        int tilesLen1 = in.readInt();
        Tile[][] tile = new Tile[tilesLen][tilesLen1];
        for(int i = 0; i < tile.length; i++){
            for(int i1 = 0; i1 < tile[i].length; i1++){
                tile[i][i1] = Tiles.findTileById(in.readInt());
            }
        }
        return new WorldDataPacket(new World(name, tile, entityDataList), youId);
    }
    
    public World getWorld(){
        return world;
    }
    
    public UUID getYou(){
        return you;
    }
}
