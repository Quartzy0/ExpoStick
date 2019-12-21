package com.quartzy.expostick.entities;

import com.quartzy.expostick.gfx.Animation;
import com.quartzy.expostick.tiles.Tile;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;
import com.quartzy.expostick.weapons.Projectile;
import com.quartzy.expostick.weapons.Weapon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class Entity {

    private BufferedImage baseTexture;
    protected Weapon weapon;
    private double scale;
    private Vector2 velocity;
    private String name;
    private boolean focused;
    protected Handler handler;
    private EntityData data;
    private Rectangle hitbox;
    private boolean colidable;
    private boolean onGround;
    private Animation animation;
    private boolean visible;
    private Vector2 weaponHoldPosition;
    private UUID id;

    public Entity(Vector2 position, BufferedImage baseTexture, double scale, Vector2 velocity, String name, EntityType type, boolean colidable, int startHealth, Handler handler) {
        this.baseTexture = baseTexture;
        this.scale = scale;
        this.velocity = velocity;
        this.name = name;
        this.handler = handler;
        this.colidable = colidable;
        this.visible = true;
        this.id = UUID.randomUUID();
        data = new EntityData(position, type, colidable, startHealth, id);
        this.hitbox = new Rectangle(position.getX(), position.getY(), (int) (baseTexture.getWidth()*scale), (int) (baseTexture.getHeight()*scale));
    }

    public Entity(Vector2 position, BufferedImage baseTexture, double scale, Vector2 velocity, String name, EntityType type, boolean colidable, int startHealth, Vector2 weaponHoldPosition, Handler handler) {
        this.baseTexture = baseTexture;
        this.scale = scale;
        this.velocity = velocity;
        this.name = name;
        this.handler = handler;
        this.colidable = colidable;
        this.visible = true;
        this.weaponHoldPosition = weaponHoldPosition;
        this.id = UUID.randomUUID();
        data = new EntityData(position, type, colidable, startHealth, id);
        this.hitbox = new Rectangle(position.getX(), position.getY(), (int) (baseTexture.getWidth()*scale), (int) (baseTexture.getHeight()*scale));
    }

    public int getHealth() {
        return data.health;
    }

    public void setHealth(int health) {
        this.data.health = health;
    }

    public void addHealth(int amount){
        this.data.health+=amount;
    }

    public void removeHealth(int amount){
        this.data.health-=amount;
    }

    public Entity(Vector2 position, BufferedImage baseTexture, double scale, Vector2 velocity, String name, EntityType type, boolean colidable, int startHealth, Handler handler, Animation animation) {
        this.baseTexture = baseTexture;
        this.scale = scale;
        this.velocity = velocity;
        this.name = name;
        this.handler = handler;
        this.colidable = colidable;
        this.animation = animation;
        this.visible = true;
        this.id = UUID.randomUUID();
        this.data = new EntityData(position, type, colidable, startHealth, id);
        this.hitbox = new Rectangle(position.getX(), position.getY(), (int) (baseTexture.getWidth()*scale), (int) (baseTexture.getHeight()*scale));
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Vector2 getPosition() {
        return data.pos;
    }

    public void setPosition(Vector2 position) {
        this.data.pos = position;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public EntityType getEntityType(){
        return data.type;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public BufferedImage getTexture() {
        if (animation==null || animation.getCurrentFrame()==null) {
            return baseTexture;
        }
        return animation.getCurrentFrame();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void tick(){
        colidable = visible;
        if (data.health<=0){
            handler.getLoadedWorld().removeEntity(this);
        }
        if (animation!=null && visible) {
            animation.check();
        }
        checkForProjectileHits();
        addVelocity(0, Handler.GRAVITY);
        velocity.setX(Math.max(Math.min(velocity.getX(), Handler.MAX_VELOCITY), -Handler.MAX_VELOCITY));
        velocity.setY(Math.max(Math.min(velocity.getY(), Handler.MAX_VELOCITY), -Handler.MAX_VELOCITY));
        int newX = data.pos.getX()+velocity.getX(), newY = data.pos.getY()+velocity.getY();
        if(getColidingTiles(velocity.getX(), 0).size()==0){
            setPosition(new Vector2(newX, getPosition().getY()));
        }else {
            setVelocity(new Vector2(0, velocity.getY()));
        }
        if (getColidingTiles(0, velocity.getY()).size()==0){
            setPosition(new Vector2(getPosition().getX(), newY));
            onGround = false;
        }else {
            setVelocity(new Vector2(velocity.getX(), 0));
            if (velocity.getY()<=0) {
                onGround = true;
            }
        }
        if (onGround && velocity.getX()!=0){
            List<Tile> colidingTiles = getColidingTiles();
            int slowdown = 0;
            int tempNum = 0;
            for (Tile t : colidingTiles){
                slowdown+=t.getFriction();
                tempNum++;
            }
            if (slowdown!=0 && tempNum!=0) {
                slowdown = slowdown / tempNum;
            }
            setVelocity(new Vector2(Math.max(Math.min(getVelocity().getX()-slowdown, 0), 0), getVelocity().getY()));
        }
        
        if (weapon!=null) {
            weapon.tick();
        }
        hitbox = new Rectangle(data.pos.getX(), data.pos.getY(), (int) (baseTexture.getWidth()*scale), (int) (baseTexture.getHeight()*scale));
    }

    protected boolean isColiding(){
        Tile[][] tiles = handler.getLoadedWorld().getTiles();
        for (Tile[] ts : tiles){
            for (Tile t : ts){
                if (getHitbox().intersects(t.getHitbox()) && t.isColidable()){
                    return true;
                }
            }
        }
        List<Entity> entities = handler.getLoadedWorld().getEntities();
        for (Entity e : entities){
            if (getHitbox().intersects(e.getHitbox()) && e.isColidable()){
                return true;
            }
        }
        return false;
    }

    protected void checkForProjectileHits(){
        for (int i = 0; i < handler.getLoadedWorld().getProjectiles().size(); i++) {
            Projectile projectile = handler.getLoadedWorld().getProjectiles().get(i);
            if (projectile.getHitbox().intersects(getHitbox())){
                removeHealth(projectile.getDamage());
                handler.getLoadedWorld().removeProjectile(projectile);
            }
        }
    }

    protected List<Tile> getColidingTiles(){
        Tile[][] tiles = handler.getLoadedWorld().getTiles();
        List<Tile> tilesL = new ArrayList<>();
        for (Tile[] ts : tiles){
            for (Tile t : ts){
                if (getHitbox().intersects(t.getHitbox()) && t.isColidable()){
                    tilesL.add(t);
                }
            }
        }
        return tilesL;
    }

    protected List<Tile> getColidingTiles(int offsetX, int offsetY){
        Tile[][] tiles = handler.getLoadedWorld().getTiles();
        List<Tile> tilesL = new ArrayList<>();
        Rectangle recToUse = new Rectangle((int) getHitbox().getX() + offsetX, (int) getHitbox().getY() + offsetY, (int) getHitbox().getWidth(), (int) getHitbox().getHeight());
        for (Tile[] ts : tiles){
            for (Tile t : ts){
                if (recToUse.intersects(t.getHitbox()) && t.isColidable()){
                    tilesL.add(t);
                }
            }
        }
        return tilesL;
    }

    protected List<Entity> getColidingEntities(){
        List<Entity> entities = handler.getLoadedWorld().getEntities();
        List<Entity> entities1 = new ArrayList<>();
        for (Entity e : entities){
            if (getHitbox().intersects(e.getHitbox()) && e.isColidable()){
                entities1.add(e);
            }
        }
        return entities1;
    }

    public void addVelocity(int x, int y){
        velocity.setX(velocity.getX()+x);
        velocity.setY(velocity.getY()+y);
    }

    public boolean isColidable() {
        return colidable;
    }

    public void setColidable(boolean colidable) {
        this.colidable = colidable;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public BufferedImage getBaseTexture() {
        return baseTexture;
    }

    public Vector2 getWeaponHoldPosition() {
        return weaponHoldPosition;
    }
    
    public UUID getId(){
        return id;
    }
    
    public void setId(UUID id){
        this.id = id;
    }
}
