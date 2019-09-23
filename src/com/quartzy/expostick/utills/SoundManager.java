package com.quartzy.expostick.utills;


import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    public static void playSound(String name){
            try {
                File yourFile = new File("res/sounds/" + name + ".wav");
                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                Clip clip;

                stream = AudioSystem.getAudioInputStream(yourFile);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
