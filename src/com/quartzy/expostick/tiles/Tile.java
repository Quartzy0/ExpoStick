package com.quartzy.expostick.tiles;

import com.quartzy.expostick.utills.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

public class Tile implements Serializable {

    private BufferedImage texture;
    private int id;
    private boolean colidable;
    private Vector2 position;
    private String name;
    private Rectangle hitbox;
    private int friction;

    public Tile(BufferedImage texture, String name, int friction, int id, boolean colidable) {
        this.texture = texture;
        this.id = id;
        this.colidable = colidable;
        this.name = name;
        this.friction = friction;
        hitbox = new Rectangle(0,0,Tiles.TILE_WIDTH,Tiles.TILE_HEIGHT);
    }

    public int getFriction() {
        return friction;
    }

    public void setFriction(int friction) {
        this.friction = friction;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public boolean isColidable() {
        return colidable;
    }

    public void setColidable(boolean colidable) {
        this.colidable = colidable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return getId() == tile.getId() &&
                Objects.equals(getTexture(), tile.getTexture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTexture(), getId());
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "texture=" + texture +
                ", id=" + id +
                ", colidable=" + colidable +
                '}';
    }

    public void tick() {
        if (position!=null) {
            hitbox.setLocation(position.getX(), position.getY());
            hitbox.setSize(Tiles.TILE_WIDTH, Tiles.TILE_HEIGHT);
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Tile copy() {
        return new Tile(texture, name, friction, id, colidable);
    }
}
