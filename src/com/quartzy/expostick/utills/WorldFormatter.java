package com.quartzy.expostick.utills;

import com.google.gson.Gson;
import com.quartzy.expostick.entities.EntityData;
import com.quartzy.expostick.entities.EntityType;
import com.quartzy.expostick.world.WorldData;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class WorldFormatter{
    
    public static void main(String[] args){
//        reformatFile(new File("C:\\Users\\QuartzMiner6000\\AppData\\Roaming\\ExpoStick\\levels\\Level_1.clvlf"), new File("C:\\Users\\QuartzMiner6000\\IdeaProjects\\ExpoStickServer\\res\\levels\\Level_1.bin"));
        WorldData world = getWorld(new File("C:\\Users\\QuartzMiner6000\\IdeaProjects\\ExpoStickServer\\res\\levels\\Level_1.bin"));
        int a = 0;
    }
    
    public static void reformatFile(File fileIn, File fileOut){
        try{
            WorldData worldData = null;
            try {
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileIn));
                worldData = (WorldData) is.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            if(!fileOut.exists()){
                fileOut.createNewFile();
            }
//            byte[] bytesName1 = worldData.name.getBytes(StandardCharsets.UTF_8);
//            byte tileSize1 = (byte) (worldData.tiles.length >> 24);
//            byte tileSize2 = (byte) (worldData.tiles[0].length >> 24);
//            byte[] tilesArr = new byte[worldData.tiles.length * worldData.tiles[0].length];
//            for(int i = 0; i < worldData.tiles.length; i++){
//                for(int i1 = 0; i1 < worldData.tiles[i].length; i1++){
//                    tilesArr[(i+1) * i1] = (byte) (worldData.tiles[i][i1] >> 24);
//                }
//            }
//            byte entityListSize = (byte) (worldData.entitySpawnList.length >> 24);
//            byte[] entityData = new byte[49*worldData.entitySpawnList.length];
//            for(int i = 0; i < worldData.entitySpawnList.length; i++){
//                int bytePos = i*49;
//                entityData[bytePos] = (byte) (worldData.entitySpawnList[i].pos.getX() >> 24);
//                entityData[bytePos+1] = (byte) (worldData.entitySpawnList[i].pos.getX() >> 16);
//                entityData[bytePos+2] = (byte) (worldData.entitySpawnList[i].pos.getX() >> 8);
//                entityData[bytePos+3] = (byte) (worldData.entitySpawnList[i].pos.getX());
//
//                entityData[bytePos+4] = (byte) (worldData.entitySpawnList[i].pos.getY() >> 24);
//                entityData[bytePos+5] = (byte) (worldData.entitySpawnList[i].pos.getY() >> 16);
//                entityData[bytePos+6] = (byte) (worldData.entitySpawnList[i].pos.getY() >> 8);
//                entityData[bytePos+7] = (byte) (worldData.entitySpawnList[i].pos.getY());
//
//                entityData[bytePos+8] = (byte) ((worldData.entitySpawnList[i].type.ordinal() >> 25) | ((byte) (worldData.entitySpawnList[i].colidable ? 0b10000000 : 0b0)));
//
//                entityData[bytePos+9] = (byte) (worldData.entitySpawnList[i].health >> 24);
//                entityData[bytePos+10] = (byte) (worldData.entitySpawnList[i].health >> 16);
//                entityData[bytePos+11] = (byte) (worldData.entitySpawnList[i].health >> 8);
//                entityData[bytePos+12] = (byte) (worldData.entitySpawnList[i].health);
//
//                String s = worldData.entitySpawnList[i].id.toString();
//                for(int i1 = 0; i1 < 36; i1++){
//                    entityData[bytePos+12+i1] = s.getBytes()[i1];
//                }
//            }
//            byte[] finalArr = mergeArrays(new byte[]{tileSize1, tileSize2, entityListSize}, mergeArrays(tilesArr, mergeArrays(entityData, bytesName1)));
            
            Gson gson = new Gson();
            
            FileOutputStream fos = new FileOutputStream(fileOut);
            fos.write(gson.toJson(worldData).getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private static byte[] mergeArrays(byte[] arr, byte[] arr1){
        byte[] newArr = new byte[arr.length+arr1.length];
        for(int i = 0; i < newArr.length; i++){
            if(arr.length-1<i){
                newArr[i] = arr1[i-arr.length];
            }else{
                newArr[i] = arr[i];
            }
        }
        return newArr;
    }
    
    public static WorldData getWorld(File file){
        try{
            byte[] bytes = Files.readAllBytes(file.toPath());
//            int tileSize1 = bytes[0];
//            int tileSize2 = bytes[1];
//            int entityListSize = bytes[2];
//
//            int[][] tiles = new int[tileSize1][tileSize2];
//            for(int a = 0;a<tileSize1;a++){
//                for(int b = 0;b<tileSize2;b++){
//                    tiles[a][b] = bytes[b+3+(a*(tileSize1-1))];
//                }
//            }
//
//            EntityData[] entityDataArr = new EntityData[entityListSize];
//            for(int i = 0; i < entityDataArr.length; i++){
//                int count = i*49;
//                int x = ByteBuffer.wrap(new byte[]{bytes[2+tileSize1*tileSize2+count], bytes[2+tileSize1*tileSize2+count+1], bytes[2+tileSize1*tileSize2+count+2], bytes[2+tileSize1*tileSize2+count+3]}).getInt();
//                int y = ByteBuffer.wrap(new byte[]{bytes[2+tileSize1*tileSize2+count+4], bytes[2+tileSize1*tileSize2+count+5], bytes[2+tileSize1*tileSize2+count+6], bytes[2+tileSize1*tileSize2+count+7]}).getInt();
//
//                EntityType entityType = EntityType.values()[(bytes[2+tileSize1*tileSize2+count+8] >> 1)];
//                boolean colidable = getBit(7, bytes[2+tileSize1*tileSize2+count+8]);
//
//                int health = ByteBuffer.wrap(new byte[]{bytes[2+tileSize1*tileSize2+count+9], bytes[2+tileSize1*tileSize2+count+10], bytes[2+tileSize1*tileSize2+count+11], bytes[2+tileSize1*tileSize2+count+12]}).getInt();
//
//                String id = "";
//                for(int a = 0;a<36;a++){
//                    id += (char) bytes[2+tileSize1*tileSize2+count+13]+a;
//                }
//                entityDataArr[i] = new EntityData(new Vector2(x, y), entityType, colidable, health, UUID.fromString(id));
//            }
//
//            String name = "";
//            int i = 3 + tileSize1 * tileSize2 + (entityListSize * 49);
//            for(int a = 0; a<bytes.length- i; a++){
//                name+= (char) bytes[a+i];
//            }
            Gson gson = new Gson();
            return gson.fromJson(new String(bytes), WorldData.class);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private static boolean getBit(int position, byte n)
    {
        return ((n >> position) & 1) == 1;
    }
    
    private static String readFileAsString(Path fileName) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return text;
    }
}
