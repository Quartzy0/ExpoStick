package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.gfx.ImageUtils;
import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

public class Pistol extends ProjectileWeapon {
    public Pistol(Entity user, Handler handler) {
        super(20, "Pistol", user, ImageUtils.resizeImage(Assets.pistol, 32, 32), new Vector2(20, 36), new PistolBullet(user, handler), handler);
    }
}
