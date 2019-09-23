package com.quartzy.expostick.entities;

import com.quartzy.expostick.gfx.Animation;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

import java.awt.image.BufferedImage;

public class HostileEntity extends Entity {

    private int damage;

    public HostileEntity(Vector2 position, BufferedImage baseTexture, double scale, Vector2 velocity, String name, EntityType type, boolean colidable, int startHealth, Handler handler, int damage) {
        super(position, baseTexture, scale, velocity, name, type, colidable, startHealth, handler);
        this.damage = damage;
    }

    public HostileEntity(Vector2 position, BufferedImage baseTexture, double scale, Vector2 velocity, String name, EntityType type, boolean colidable, int startHealth, Handler handler, Animation animation, int damage) {
        super(position, baseTexture, scale, velocity, name, type, colidable, startHealth, handler, animation);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
