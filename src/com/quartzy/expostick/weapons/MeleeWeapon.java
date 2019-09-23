package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

import java.awt.image.BufferedImage;
import java.util.List;

public class MeleeWeapon extends Weapon {
    public MeleeWeapon(int damagePerHit, String name, Entity user, BufferedImage texture, Vector2 holdPosition, Handler handler) {
        super(damagePerHit, name, user, texture, holdPosition, handler);
    }

    @Override
    public void attack() {
        List<Entity> entities = handler.getLoadedWorld().getEntities();
        for (Entity entity : entities) {
            if (entity!=user && entity.getHitbox().contains(user.getPosition().getX() + user.getHitbox().getWidth(), user.getPosition().getY() + user.getHitbox().getHeight()/2)){
                entity.removeHealth(getDamagePerHit());
            }
        }
    }
}
