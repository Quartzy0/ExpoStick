package com.quartzy.expostick.gfx;

import com.quartzy.expostick.utills.Handler;
import main.java.ExternalLIBS.EncryptedFile;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageLoader {

    public static BufferedImage loadImage(String textureName){
        return new EncryptedFile(new File(Handler.GAME_DIR_PATH + File.separator + "textures" + File.separator + textureName + ".eaf")).getImage();
    }

}
