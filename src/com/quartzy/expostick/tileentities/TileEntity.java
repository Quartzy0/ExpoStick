package com.quartzy.expostick.tileentities;

import com.quartzy.expostick.gfx.Animation;
import com.quartzy.expostick.utills.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileEntity{
    
    private Vector2 position;
    private String name;
    private boolean focused;
    private double scale;
    private BufferedImage baseTexture;
    private Rectangle hitbox;
    private boolean colidable;
    private Animation animation;
    private boolean visible;
    
    public TileEntity(Vector2 position, String name, boolean focused, double scale, BufferedImage baseTexture, Rectangle hitbox, boolean colidable, Animation animation, boolean visible){
        this.position = position;
        this.name = name;
        this.focused = focused;
        this.scale = scale;
        this.baseTexture = baseTexture;
        this.hitbox = hitbox;
        this.colidable = colidable;
        this.animation = animation;
        this.visible = visible;
    }
}
