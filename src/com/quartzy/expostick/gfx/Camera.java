package com.quartzy.expostick.gfx;

import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.entities.Player;
import com.quartzy.expostick.input.Pressed;
import com.quartzy.expostick.tiles.Tile;
import com.quartzy.expostick.tiles.Tiles;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;
import com.quartzy.expostick.weapons.Projectile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Camera {

    private Vector2 focusedPos;
    private Handler handler;
    private int offsetX, offsetY;
    private double zoomFactor = 1;

    public Camera(Vector2 focusedPos, Handler handler) {
        this.focusedPos = focusedPos;
        this.handler = handler;
        if (Handler.LEVEL_MAKING) {
            Handler.W.addOnPressed(new Pressed() {
                @Override
                public void pressed(KeyEvent e) {
                    focusedPos.setY(focusedPos.getY()-Tiles.TILE_HEIGHT);
                }
            });
            Handler.S.addOnPressed(new Pressed() {
                @Override
                public void pressed(KeyEvent e) {
                    focusedPos.setY(focusedPos.getY()+Tiles.TILE_HEIGHT);
                }
            });
            Handler.A.addOnPressed(new Pressed() {
                @Override
                public void pressed(KeyEvent e) {
                    focusedPos.setX(focusedPos.getX()-Tiles.TILE_WIDTH);
                }
            });
            Handler.D.addOnPressed(new Pressed() {
                @Override
                public void pressed(KeyEvent e) {
                    focusedPos.setX(focusedPos.getX()+Tiles.TILE_WIDTH);
                }
            });
            Handler.PLUS.addOnPressed(new Pressed() {
                @Override
                public void pressed(KeyEvent e) {
                    zoomFactor+=0.2;
                }
            });
            Handler.MINUS.addOnPressed(new Pressed() {
                @Override
                public void pressed(KeyEvent e) {
                    zoomFactor-=0.2;
                }
            });
        }
    }

    public void tick(){
        Tiles.TILE_WIDTH = (int) (Tiles.TILE_WIDTH_ORIGINAL*zoomFactor);
        Tiles.TILE_HEIGHT = (int) (Tiles.TILE_HEIGHT_ORIGINAL*zoomFactor);
        offsetX = focusedPos.getX()-(handler.getDisplay().getWidth()/2);
        offsetY = focusedPos.getY()-(handler.getDisplay().getHeight()/2);
        for (int a = 0; a < handler.getLoadedWorld().getTiles().length; a++) {
            for (int b = 0; b < handler.getLoadedWorld().getTiles()[a].length; b++) {
                handler.getLoadedWorld().getTiles()[a][b].setPosition(new Vector2(b * Tiles.TILE_WIDTH, a * Tiles.TILE_HEIGHT));
                handler.getLoadedWorld().getTiles()[a][b].tick();
            }
        }
    }

    public void render(Graphics2D g){
        if (handler.getLoadedWorld()!=null){
            g.setColor(Color.BLACK);
            for (int a = 0;a<handler.getLoadedWorld().getTiles().length;a++){
                for (int b = 0;b<handler.getLoadedWorld().getTiles()[a].length;b++){
                    handler.getLoadedWorld().getTiles()[a][b].setPosition(new Vector2(b*Tiles.TILE_WIDTH, a*Tiles.TILE_HEIGHT));
                    Tile currentTile = handler.getLoadedWorld().getTiles()[a][b];
                    BufferedImage textureToDraw = currentTile.getTexture();
                    int xToDraw = (currentTile.getPosition().getX()-offsetX), yToDraw = (currentTile.getPosition().getY()-offsetY);
                    if (xToDraw>= -Tiles.TILE_WIDTH && xToDraw<=handler.getDisplay().getWidth() && yToDraw>= -Tiles.TILE_HEIGHT && yToDraw<=handler.getDisplay().getHeight()) {
                        if (Handler.LEVEL_MAKING){
                            g.drawRect(xToDraw, yToDraw, Tiles.TILE_WIDTH, Tiles.TILE_HEIGHT);
                        }
                        g.drawImage(textureToDraw, xToDraw, yToDraw, Tiles.TILE_WIDTH, Tiles.TILE_HEIGHT, null);
                        if (Handler.DEBUG) {
                            g.setFont(g.getFont().deriveFont(10F));
                            if (currentTile.isColidable()) {
                                g.drawString(currentTile.getPosition().toString(), xToDraw, yToDraw);
                            }
                            if (currentTile.isColidable()) {
                                g.drawRect(currentTile.getHitbox().x, currentTile.getHitbox().y, currentTile.getHitbox().width, currentTile.getHitbox().height);
                            }
                        }
                    }
                }
            }
            for (int i = 0;i<handler.getLoadedWorld().getEntities().size();i++){
                Entity e = handler.getLoadedWorld().getEntities().get(i);
                if (e.isVisible()) {
                    int xToDraw = (int) ((e.getPosition().getX() * zoomFactor - offsetX)), yToDraw = (int) ((e.getPosition().getY() * zoomFactor - offsetY));
                    if (xToDraw >= -e.getTexture().getWidth() && xToDraw <= handler.getDisplay().getWidth() && yToDraw >= -e.getTexture().getHeight() && yToDraw <= handler.getDisplay().getHeight()) {
                        g.drawImage(e.getTexture(), xToDraw, yToDraw, (int) ((int) (e.getTexture().getWidth() * e.getScale()) * zoomFactor), (int) ((int) (e.getTexture().getHeight() * e.getScale()) * zoomFactor), null);
                        if (e.getWeapon() != null && e.getWeaponHoldPosition()!=null) {
                            g.drawImage(e.getWeapon().getTexture(), (xToDraw + e.getWeaponHoldPosition().getX())-e.getWeapon().getHoldPosition().getX()+e.getWeapon().getTexture().getWidth()/2, (yToDraw + e.getWeaponHoldPosition().getY()) - e.getWeapon().getHoldPosition().getY()+e.getWeapon().getTexture().getHeight()/2, (int) (e.getWeapon().getTexture().getWidth() * zoomFactor), (int) (e.getWeapon().getTexture().getHeight() * zoomFactor), null);
                        }
                        if (Handler.DEBUG) {
                            if (e instanceof Player) {
                                g.setColor(Color.BLUE);
                                g.drawString(e.getPosition().toString(), xToDraw, yToDraw);
                            } else {
                                g.setColor(Color.RED);
                            }
                            g.drawRect((int) (e.getHitbox().getX() * zoomFactor - offsetX), (int) (e.getHitbox().getY() * zoomFactor - offsetY), e.getHitbox().width, e.getHitbox().height);
                        }
                    }
                }
            }
            for (int i = 0; i < handler.getLoadedWorld().getProjectiles().size(); i++) {
                Projectile projectile = handler.getLoadedWorld().getProjectiles().get(i);
                g.drawImage(projectile.getTexture(), (int) (projectile.getX() * zoomFactor - offsetX), (int) (projectile.getY() * zoomFactor - offsetY), null);
            }
        }
    }

    public Vector2 getFocusedPos() {
        return focusedPos;
    }

    public void setFocusedPos(Vector2 focusedPos) {
        this.focusedPos = focusedPos;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
}
