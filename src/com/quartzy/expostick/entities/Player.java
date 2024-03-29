package com.quartzy.expostick.entities;

import com.quartzy.expostick.gui.GuiDeath;
import com.quartzy.expostick.input.Clicked;
import com.quartzy.expostick.input.Mouse;
import com.quartzy.expostick.netty.packets.PlayerRequestMovement;
import com.quartzy.expostick.tiles.Tile;
import com.quartzy.expostick.tiles.Tiles;
import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.MovementType;
import com.quartzy.expostick.utills.Vector2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class Player extends Entity {

    private long lastTimeDamaged = System.currentTimeMillis();
    private long lastTimeSouede = System.currentTimeMillis();
    
    private MovementType prevMovement;

    public Player(Vector2 position, double scale, Vector2 velocity, Handler handler) {
        super(position, Assets.playerIdle, scale, velocity, "Player", EntityType.PLAYER, true, 100, new Vector2(47, 90), handler);
        Mouse.addClickEvent(new Clicked() {
            @Override
            public void clicked(MouseEvent event) {
                if (!Handler.LEVEL_MAKING && event.getButton()==MouseEvent.BUTTON1){
                    attack();
                }
            }
        });
    }

    @Override
    public void tick() {
        if(System.currentTimeMillis()-lastTimeSouede>=1000){
            System.out.println(getId().toString());
            lastTimeSouede = System.currentTimeMillis();
        }
        
        List<Entity> colidingEntities = getColidingEntities();
        if (colidingEntities!=null && colidingEntities.size()!=0 && System.currentTimeMillis()-lastTimeDamaged>=1000){
            for (Entity colidingEntity : colidingEntities) {
                if (colidingEntity instanceof HostileEntity){
                    removeHealth(((HostileEntity)colidingEntity).getDamage());
                    lastTimeDamaged = System.currentTimeMillis();
                }
            }
        }
        List<Tile> colidingTiles = getColidingTiles(0, 3);
        if (colidingTiles!=null && colidingTiles.size()!=0){
            for (Tile colidingTile : colidingTiles) {
                if (colidingTile.getId()==2 || colidingTile.getId()==3){
                    setHealth(0);
                }
            }
        }
        if(handler.getPlayer()!=null && this.getId().equals(handler.getPlayer().getId())){
            if(isOnGround()){
                if(Handler.W.isPressed()){
                    addVelocity(0, -20);
                    if(prevMovement!=MovementType.JUMP){
                        handler.getNettyHandler().sendPacket(new PlayerRequestMovement(getId(), MovementType.JUMP));
                        prevMovement = MovementType.JUMP;
                    }
                }
            }else if (getVelocity().getX()>-5) {
                if(Handler.A.isPressed()){
                    addVelocity(-5, 0);
                    if(prevMovement!=MovementType.LEFT){
                        handler.getNettyHandler().sendPacket(new PlayerRequestMovement(getId(), MovementType.LEFT));
                        prevMovement = MovementType.LEFT;
                    }
                }
            }else if (getVelocity().getX()<5) {
                if(Handler.D.isPressed()){
                    addVelocity(5, 0);
                    if(prevMovement!=MovementType.RIGHT){
                        handler.getNettyHandler().sendPacket(new PlayerRequestMovement(getId(), MovementType.RIGHT));
                        prevMovement = MovementType.RIGHT;
                    }
                }
            }else {
                if(prevMovement!=MovementType.NONE){
                    handler.getNettyHandler().sendPacket(new PlayerRequestMovement(getId(), MovementType.NONE));
                    prevMovement = MovementType.NONE;
                }
            }
        }
        if (!(getPosition().getX()>0 && getPosition().getY()>0 && getPosition().getY()<handler.getLoadedWorld().getTiles().length* Tiles.TILE_HEIGHT_ORIGINAL && getPosition().getX()<handler.getLoadedWorld().getTiles()[0].length* Tiles.TILE_WIDTH_ORIGINAL)){
            setHealth(0);
        }
        if (getHealth()<=0){
            handler.setCurrentGuiScreen(new GuiDeath(handler));
        }
        super.tick();
        setHitbox(new Rectangle(getHitbox().x+25, getHitbox().y+15, getHitbox().width-37, getHitbox().height-15));
    }

    private void attack(){
        if (handler.getCurrentGuiScreen().getName().equals("Ingame Gui") && weapon!=null) {
            weapon.attack();
        }
    }
}
