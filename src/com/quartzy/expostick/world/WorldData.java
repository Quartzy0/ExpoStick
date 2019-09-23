package com.quartzy.expostick.world;

import com.quartzy.expostick.entities.EntityData;

import java.io.Serializable;

public class WorldData implements Serializable {

    public String name;
    public int[][] tiles;
    public EntityData[] entitySpawnList;
    public int width, height;

    public WorldData(String name, int[][] tiles, EntityData[] entitySpawnList) {
        this.name = name;
        this.tiles = tiles;
        this.entitySpawnList = entitySpawnList;
    }
}
