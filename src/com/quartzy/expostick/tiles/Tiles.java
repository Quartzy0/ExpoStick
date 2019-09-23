package com.quartzy.expostick.tiles;

public class Tiles {

    public static final Tile fullLabTile = new FullLabTile();
    public static final Tile air = new Air();
    public static final Tile toxicGoo = new ToxicGoo();
    public static final Tile toxicGooTop = new ToxicGooTop();

    public static final Tile[] tiles = {fullLabTile, air, toxicGoo, toxicGooTop};
    public static int TILE_WIDTH = 16, TILE_HEIGHT = 16;
    public static final int TILE_WIDTH_ORIGINAL = 16, TILE_HEIGHT_ORIGINAL = 16;
    public static final int TILE_TEXTURE_WIDTH = 64, TILE_TEXTURE_HEIGHT = 64;

    public static Tile findTileById(int id){
        for (int a = 0;a<tiles.length;a++){
            if (tiles[a].getId()==id){
                return tiles[a];
            }
        }
        return null;
    }

    public static Tile findTileByName(String name){
        for (int a = 0;a<tiles.length;a++){
            if (tiles[a].getName().equals(name)){
                return tiles[a];
            }
        }
        return null;
    }

}
