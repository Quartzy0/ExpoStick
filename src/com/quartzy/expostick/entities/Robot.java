package com.quartzy.expostick.entities;

import com.quartzy.expostick.gfx.Animation;
import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

import java.awt.*;
import java.util.Arrays;

public class Robot extends HostileEntity {

    public Robot(Vector2 position, double scale, Vector2 velocity, Handler handler) {
        super(position, Assets.robotIdle[0], scale/2, velocity, "Robot", EntityType.ROBOT, true,  50, handler, new Animation(Arrays.asList(Assets.robotIdle), 100), 10);
    }

    @Override
    public void tick() {
        super.tick();
        setHitbox(new Rectangle(getPosition().getX(), getPosition().getY()-5, (int) (getTexture().getWidth()*getScale()), (int) (getTexture().getHeight()*getScale())+5));
    }
}
