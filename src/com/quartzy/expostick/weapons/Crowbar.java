package com.quartzy.expostick.weapons;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

public class Crowbar extends MeleeWeapon {
    public Crowbar(Entity user, Handler handler) {
        super(10, "Crowbar", user, Assets.crowbar, new Vector2(16, 37), handler);
    }
}
