package com.quartzy.expostick.entities;

import com.quartzy.expostick.utills.Vector2;

import java.io.Serializable;

public class EntityData implements Serializable {
    
    public long serialVersionUID = 26362728213737637L;
    
    public Vector2 pos;
    public EntityType type;
    public boolean colidable;
    public int health;


    public EntityData(Vector2 pos, EntityType type, boolean colidable, int health) {
        this.pos = pos;
        this.type = type;
        this.colidable = colidable;
        this.health = health;
    }
}
