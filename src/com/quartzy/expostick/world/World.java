package com.quartzy.expostick.world;

import com.quartzy.expostick.entities.*;
import com.quartzy.expostick.entities.Robot;
import com.quartzy.expostick.tiles.Tile;
import com.quartzy.expostick.tiles.Tiles;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;
import com.quartzy.expostick.weapons.Projectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class World {

    private String name;
    private Tile[][] tiles;
    private List<Entity> entities;
    private List<Projectile> projectiles;
    private Handler handler;

    public World(WorldData data, Handler handler) {
        this.name = data.name;
        projectiles = new ArrayList<>();
        this.tiles = new Tile[data.tiles.length][data.tiles[0].length];
        for (int a = 0;a<tiles.length;a++){
            for (int b = 0;b<tiles[a].length;b++){
                tiles[a][b] = Tiles.findTileById(data.tiles[a][b]).copy();
            }
        }
        entities = new ArrayList<>();
        for (int a = 0;a<data.entitySpawnList.length;a++){
            if (data.entitySpawnList[a]!=null) {
                switch (data.entitySpawnList[a].type) {
                    case ROBOT:
                        entities.add(new Robot(data.entitySpawnList[a].pos, 1, new Vector2(0, 0), handler));
                        break;
                    case BALLROBOT:
                        entities.add(new BallRobot(data.entitySpawnList[a].pos, 1, new Vector2(0, 0), handler));
                        break;
                    case PLAYER:
                        entities.add(new Player(data.entitySpawnList[a].pos, 1, new Vector2(0, 0), handler));
                    default:
                        break;
                }
            }
        }
        this.handler = handler;
        for (int a = 0;a<tiles.length;a++){
            for (int b = 0;b<tiles.length;b++){
                tiles[a][b].setPosition(new Vector2(b*Tiles.TILE_WIDTH, a*Tiles.TILE_HEIGHT));
            }
        }
        int a;
    }

    public String getName() {
        return name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof World)) return false;
        World world = (World) o;
        return Objects.equals(getName(), world.getName()) &&
                Arrays.equals(getTiles(), world.getTiles());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName());
        result = 31 * result + Arrays.hashCode(getTiles());
        return result;
    }

    @Override
    public String toString() {
        return "World{" +
                "name='" + name + '\'' +
                ", tiles=" + Arrays.toString(tiles) +
                '}';
    }

    public void removeEntity(Entity entity){
        entities.remove(entity);
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void setTile(int x, int y, Tile tile){
        tiles[y][x] = tile;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile){
        projectiles.remove(projectile);
    }
}