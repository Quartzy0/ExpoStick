package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Weapon {
    private int damagePerHit;
    private String name;
    protected Handler handler;
    private BufferedImage texture;
    private Vector2 holdPosition;

    public Entity getUser() {
        return user;
    }

    public void setUser(Entity user) {
        this.user = user;
    }

    protected Entity user;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Weapon(int damagePerHit, String name, Entity user, BufferedImage texture, Vector2 holdPosition, Handler handler) {
        this.damagePerHit = damagePerHit;
        this.name = name;
        this.handler = handler;
        this.user = user;
        this.texture = texture;
        this.holdPosition = holdPosition;
    }

    public int getDamagePerHit() {
        return damagePerHit;
    }

    public void setDamagePerHit(int damagePerHit) {
        this.damagePerHit = damagePerHit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void attack();

    public void tick(){

    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public Vector2 getHoldPosition() {
        return holdPosition;
    }
}
