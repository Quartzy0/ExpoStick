package com.quartzy.expostick.utills;

import com.quartzy.expostick.entities.EntityData;
import com.quartzy.expostick.entities.EntityType;
import com.quartzy.expostick.world.WorldData;
import main.java.ExternalLIBS.Config;

public class WorldDataParser {

    public static WorldData parse(String fileName){
        Config config = new Config("res/levels/" + fileName + ".txt");
        String name = config.getString("name");
        int tileW = config.getInt("tileW");
        int tileH = config.getInt("tileH");
        String tileIds = config.getString("tiles");
        int entityLength = config.getInt("enitiesLength");
        String entitySpawns = config.getString("entities");
        String[] tiles1D = tileIds.split(",");
        int[][] tiles = new int[tileH][tileW];
        int counter1 = 0;
        int counter2 = 0;
        for (int a = 0;a<tiles1D.length;a++){
            tiles[counter1][counter2] = Integer.parseInt(tiles1D[a]);
            counter2++;
            if (counter2==tileH){
                counter2 = 0;
                counter1++;
            }
        }

        //{Pos:10-10,Type:ROBOT,Health:50}
        String[] undoneEntities = entitySpawns.split("\\|");
        EntityData[] entityData = new EntityData[entityLength];
        for (int a = 0;a<undoneEntities.length;a++){
            String formatingToBeDoneHere = undoneEntities[a].replace("{", "").replace("}", "");
            Vector2 pos = new Vector2(Integer.parseInt(formatingToBeDoneHere.substring(formatingToBeDoneHere.indexOf(':'), formatingToBeDoneHere.indexOf(',')).split("-")[0]), Integer.parseInt(formatingToBeDoneHere.substring(formatingToBeDoneHere.indexOf(':'), formatingToBeDoneHere.indexOf(',')).split("-")[1]));
            EntityType type = null;
            if (formatingToBeDoneHere.substring(formatingToBeDoneHere.indexOf(":", formatingToBeDoneHere.indexOf(":") + 3)).equals("ROBOT")){
                type = EntityType.ROBOT;
            }
            int health = Integer.parseInt(formatingToBeDoneHere.substring(formatingToBeDoneHere.lastIndexOf(":"), formatingToBeDoneHere.length()-1));
            entityData[a] = new EntityData(pos, type, true, health);
        }
        return new WorldData(name, tiles, entityData);
    }

}
