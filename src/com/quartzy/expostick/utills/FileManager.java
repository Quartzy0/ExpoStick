package com.quartzy.expostick.utills;

import com.quartzy.expostick.world.WorldData;

import java.io.*;

public class FileManager {
    public static void saveWorld(WorldData data){
        try {
            File file = new File(Handler.GAME_DIR_PATH + "/levels");
            File file1 = new File(file.getAbsolutePath() + "/" + data.name + ".clvlf");
            System.out.println(file1.getAbsolutePath());
            if (!file1.exists()){
                file.mkdirs();
                file1.createNewFile();
            }
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file1));
            os.writeObject(data);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WorldData loadWorld(String name){
        try {
            File file = new File(Handler.GAME_DIR_PATH + "/levels/" + name + ".clvlf");
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            WorldData worldData = (WorldData) is.readObject();
            return worldData;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
