package com.quartzy.expostick.entities;

import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;

public class BallRobot extends HostileEntity {
    public BallRobot(Vector2 position, double scale, Vector2 velocity, Handler handler) {
        super(position, Assets.baller, scale/2, velocity, "Baller", EntityType.BALLROBOT, true, 75, handler, 15);
    }
}
