package com.quartzy.expostick.utills;
import com.quartzy.expostick.gfx.ImageLoader;
import com.quartzy.expostick.gfx.SpriteSheet;
import com.quartzy.expostick.tiles.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {

    public static BufferedImage fullLabTile;
    public static BufferedImage robot;
    public static BufferedImage playerIdle;
    public static BufferedImage baller;
    public static BufferedImage[] robotIdle;
    public static BufferedImage crowbar;
    public static BufferedImage toxicGoo;
    public static BufferedImage toxicGooTop;
    public static BufferedImage pistol;
    public static BufferedImage pistolBullet;
    public static Font font;

    public static void init(){
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(Handler.GAME_DIR_PATH + File.separator + "fonts" + File.separator + "font.ttf")).deriveFont(20F);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        SpriteSheet spriteSheet = new SpriteSheet(ImageLoader.loadImage("sheet1"));
        robotIdle = new BufferedImage[]{spriteSheet.crop(64, spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128*2), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128*3), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128*4), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128*5), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128*6), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128), spriteSheet.crop(64+(128*7), spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2), 128, 128)};
        fullLabTile = spriteSheet.crop(0, 0, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT);
        robot = spriteSheet.crop(Tiles.TILE_TEXTURE_WIDTH*3, Tiles.TILE_TEXTURE_HEIGHT*2, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT);
        playerIdle = spriteSheet.crop(0, spriteSheet.getHeight()-(Tiles.TILE_TEXTURE_HEIGHT*2)-1, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT*2);
        baller = spriteSheet.crop(0, spriteSheet.getHeight()- Tiles.TILE_TEXTURE_HEIGHT*4, Tiles.TILE_TEXTURE_WIDTH*2, Tiles.TILE_TEXTURE_HEIGHT*2);
        crowbar = spriteSheet.crop(Tiles.TILE_TEXTURE_WIDTH*4, Tiles.TILE_TEXTURE_HEIGHT*2, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT);
        toxicGooTop = spriteSheet.crop( Tiles.TILE_TEXTURE_WIDTH*6, 0, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT);
        toxicGoo = spriteSheet.crop( Tiles.TILE_TEXTURE_WIDTH*6, Tiles.TILE_TEXTURE_HEIGHT, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT);
        pistol = spriteSheet.crop(Tiles.TILE_TEXTURE_WIDTH*3+1, Tiles.TILE_TEXTURE_HEIGHT*2+1, Tiles.TILE_TEXTURE_WIDTH, Tiles.TILE_TEXTURE_HEIGHT-1);
        pistolBullet = spriteSheet.crop(spriteSheet.getWidth()-Tiles.TILE_TEXTURE_WIDTH/4, 0, Tiles.TILE_TEXTURE_WIDTH/4, Tiles.TILE_TEXTURE_HEIGHT/4);
    }
}
