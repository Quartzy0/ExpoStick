package com.quartzy.expostick.utills;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FontLoader {
    public static Font loadFont(String path, float size){
        try {
            ClassLoader cl = FontLoader.class.getClassLoader();
            File file = new File(cl.getResource(path.replace("%20", " ")).getFile());
            File file1 = new File(file.getPath().replace("file:", ""));
            System.out.println(file1.getPath());
            System.out.println(file1.exists());
            BufferedInputStream fontStream = new BufferedInputStream(new FileInputStream(file1));
            return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
