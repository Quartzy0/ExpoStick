package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

import java.awt.image.BufferedImage;

public class ProjectileWeapon extends Weapon {
    private Projectile projectile;

    public ProjectileWeapon(int damagePerHit, String name, Entity user, BufferedImage texture, Vector2 holdPosition, Projectile projectile, Handler handler) {
        super(damagePerHit, name, user, texture, holdPosition, handler);
        this.projectile = projectile;
        this.projectile.setDamage(damagePerHit);
    }

    @Override
    public void attack() {
        handler.getLoadedWorld().addProjectile(projectile.fire());
    }
}
