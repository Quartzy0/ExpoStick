package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.utills.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile {
    private BufferedImage texture;
    private int x, y;
    private double speed;
    private boolean firing;
    private int timeBeforeRemoved;
    private long timeWhenFired = 0;
    private Handler handler;
    private Rectangle hitbox;
    private int damage;
    private Entity owner;
    private boolean firtsTime = true;

    public Projectile(BufferedImage texture, double speed, int timeBeforeRemoved, Entity owner, Handler handler) {
        this.texture = texture;
        this.speed = speed;
        this.timeBeforeRemoved = timeBeforeRemoved;
        this.handler = handler;
        this.owner = owner;
        hitbox = new Rectangle(texture.getWidth(), texture.getHeight());
    }

    public Projectile fire(){
        Projectile projectile = new Projectile(texture, speed, timeBeforeRemoved, owner, handler);
        projectile.setX((int) (owner.getPosition().getX() + owner.getHitbox().getWidth() / 2) + 50);
        projectile.setY((int) (owner.getPosition().getY() + owner.getHitbox().getHeight() / 2));
        projectile.setDamage(damage);
        projectile.setFiring(true);
        return projectile;
    }

    public void tick(){
        if (firing){
            if (firtsTime) {
                timeWhenFired = System.currentTimeMillis();
                firtsTime = false;
            }
            if (System.currentTimeMillis()-timeWhenFired>=timeBeforeRemoved){
                handler.getLoadedWorld().getProjectiles().remove(this);
            }
            x+=speed;
            hitbox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isFiring() {
        return firing;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public int getTimeBeforeRemoved() {
        return timeBeforeRemoved;
    }

    public void setTimeBeforeRemoved(int timeBeforeRemoved) {
        this.timeBeforeRemoved = timeBeforeRemoved;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
