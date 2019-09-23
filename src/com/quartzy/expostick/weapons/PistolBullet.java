package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.Handler;

public class PistolBullet extends Projectile {
    public PistolBullet(Entity ownler, Handler handler) {
        super(Assets.pistolBullet, 10, 3000, ownler, handler);
    }
}
