package com.quartzy.expostick.utills;

import com.quartzy.expostick.entities.EntityData;
import com.quartzy.expostick.world.WorldData;

public class RandomLvlMaker {

    public static void main(String[] args){
        int[][] tiles = new int[60][200];
        for (int a = 0;a<tiles.length;a++){
            for (int b = 0;b<tiles[a].length;b++){
                tiles[a][b] = 0;
            }
        }
        EntityData[] entities = new EntityData[2];
        FileManager.saveWorld(new WorldData("Preset - Empty", tiles, entities));
    }

}
