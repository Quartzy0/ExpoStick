package com.quartzy.expostick.gui;

import com.quartzy.expostick.entities.*;
import com.quartzy.expostick.entities.Robot;
import com.quartzy.expostick.input.Clicked;
import com.quartzy.expostick.input.Dragged;
import com.quartzy.expostick.input.Mouse;
import com.quartzy.expostick.tiles.Tile;
import com.quartzy.expostick.tiles.Tiles;
import com.quartzy.expostick.utills.FileManager;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;
import com.quartzy.expostick.world.World;
import com.quartzy.expostick.world.WorldData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class LevelMakerGui extends Gui {

    private GuiButton modeBtn;
    private GuiButton loadBtn;
    private GuiButton generateBtn;
    private GuiDropdown dropdown;
    private boolean mode;
    
    private String prevDropdownValue = "";

    public LevelMakerGui(Handler handler) {
        super("Level maker GUI", false, handler);
        modeBtn = new GuiButton(10, 10, 100, 100, 3, Color.CYAN, new Color(64, 64, 64), "Mode: Tiles", Color.RED);
        loadBtn = new GuiButton(110, 10, 100, 100, 3, Color.CYAN, new Color(64, 64, 64), "Load file", Color.RED);
        generateBtn = new GuiButton(210, 10, 100, 100, 3, Color.CYAN, new Color(64, 64, 64), "Generate file", Color.RED);
        List<String> values = new ArrayList<>();
        for (Tile tile : Tiles.tiles) {
            values.add(tile.getName());
        }
        dropdown = new GuiDropdown(310, 30, 100, 40, 3, Color.CYAN, new Color(64, 64, 64), values, Color.RED);
        modeBtn.setClickEvent(new OnClickedEvent() {
            @Override
            public void clicked() {
                mode = !mode;
                if (mode) {
                    modeBtn.setText("Mode: Entities");
                    List<String> values = new ArrayList<>();
                    for (EntityType value : EntityType.values()) {
                        values.add(value.name());
                    }
                    values.add("Eraser");
                    dropdown.setValues(values);
                } else {
                    modeBtn.setText("Mode: Tiles");
                    List<String> values = new ArrayList<>();
                    for (Tile tile : Tiles.tiles) {
                        values.add(tile.getName());
                    }
                    dropdown.setValues(values);
                }
            }
        });
        loadBtn.setClickEvent(new OnClickedEvent() {
            @Override
            public void clicked() {
                String s = JOptionPane.showInputDialog("Enter the name of the file: ");
                handler.setLoadedWorld(new World(Objects.requireNonNull(FileManager.loadWorld(s)), handler));
            }
        });
        generateBtn.setClickEvent(new OnClickedEvent() {
            @Override
            public void clicked() {
                String s = JOptionPane.showInputDialog("Enter the name of the file: ");
                World loadedWorld = handler.getLoadedWorld();
                List<Entity> entities = loadedWorld.getEntities();
                EntityData[] entityData = new EntityData[entities.size()];
                for (int i = 0; i < entities.size(); i++) {
                    entityData[i] = new EntityData(entities.get(i).getPosition(), entities.get(i).getEntityType(), entities.get(i).isColidable(), entities.get(i).getHealth(), UUID.randomUUID());
                }
                Tile[][] tiles = loadedWorld.getTiles();
                int[][] tileIds = new int[tiles.length][tiles[0].length];

                for (int i = 0; i < tiles.length; i++) {
                    for (int i1 = 0; i1 < tiles[i].length; i1++) {
                        tileIds[i][i1] = tiles[i][i1].getId();
                    }
                }

                FileManager.saveWorld(new WorldData(s, tileIds, entityData));
            }
        });
        addComponet(modeBtn);
        addComponet(dropdown);
        addComponet(loadBtn);
        addComponet(generateBtn);

        Mouse.addClickEvent(new Clicked() {
            @Override
            public void clicked(MouseEvent event) {
                if(prevDropdownValue.equals(dropdown.getValue()) && !(event.getX()>dropdown.getX() && event.getY()>dropdown.getY() && event.getY()<dropdown.getY()+dropdown.getHeight() && event.getX()<dropdown.getX()+dropdown.getWidth())){
                    placeThings(event.getX(), event.getY());
                }
                prevDropdownValue = dropdown.getValue();
            }
        });
        Mouse.addMouseDragEvent(new Dragged() {
            @Override
            public void dragged(MouseEvent event) {
                if(prevDropdownValue.equals(dropdown.getValue()) && !(event.getX()>dropdown.getX() && event.getY()>dropdown.getY() && event.getY()<dropdown.getY()+dropdown.getHeight() && event.getX()<dropdown.getX()+dropdown.getWidth())){
                    placeThings(event.getX(), event.getY());
                }
                prevDropdownValue = dropdown.getValue();
            }

            @Override
            public void draggingFinished(MouseEvent e) {

            }
        });
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
//        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F);
//        Composite prevComposite = g.getComposite();
//        g.setComposite(ac);
//        Tile colidingTile = getColidingTile(Mouse.x, Mouse.y);
//        if (colidingTile!=null) {
//            int x1 = (int) (colidingTile.getPosition().getX()/handler.getCamera().getZoomFactor()-handler.getCamera().getOffsetX());
//            int y1 = (int) (colidingTile.getPosition().getY()/handler.getCamera().getZoomFactor()-handler.getCamera().getOffsetY());
//            if (mode) {
//                if (!dropdown.getValue().equals("Eraser")) {
//                    EntityType type = EntityType.valueOf(dropdown.getValue());
//                    switch (type) {
//                        case ROBOT:
//                            g.drawImage(Assets.robotIdle[0], x1, y1, (int) ((Assets.robotIdle[0].getWidth() / 2) * handler.getCamera().getZoomFactor()), (int) ((Assets.robotIdle[0].getHeight() / 2) * handler.getCamera().getZoomFactor()), null);
//                            break;
//                        case BALLROBOT:
//                            g.drawImage(Assets.baller, x1, y1, (int) (Assets.playerIdle.getWidth() * handler.getCamera().getZoomFactor()), (int) (Assets.playerIdle.getHeight() * handler.getCamera().getZoomFactor()), null);
//                            break;
//                        case PLAYER:
//                            g.drawImage(Assets.playerIdle, x1, y1, (int) (Assets.playerIdle.getWidth() * handler.getCamera().getZoomFactor()), (int) (Assets.playerIdle.getHeight() * handler.getCamera().getZoomFactor()), null);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            } else {
//                Tile tile = Tiles.findTileByName(dropdown.getValue());
//                g.drawImage(tile.getTexture(), x1, y1, Tiles.TILE_WIDTH, Tiles.TILE_HEIGHT, null);
//            }
//        }
//        g.setComposite(prevComposite);
        super.render(g);
    }

    public Tile getColidingTile(int x, int y){
        Tile hitbox = null;
        int x1 = (x-(-handler.getCamera().getOffsetX()));
        int y1 = (y-(-handler.getCamera().getOffsetY()));
        for (int i = 0; i < handler.getLoadedWorld().getTiles().length; i++) {
            for (int i1 = 0; i1 < handler.getLoadedWorld().getTiles()[i].length; i1++) {
                handler.getLoadedWorld().getTiles()[i][i1].setPosition(new Vector2(i1 * Tiles.TILE_WIDTH, i * Tiles.TILE_HEIGHT));
                handler.getLoadedWorld().getTiles()[i][i1].tick();
                if (handler.getLoadedWorld().getTiles()[i][i1].getHitbox().contains(x1, y1)){
                    hitbox = handler.getLoadedWorld().getTiles()[i][i1];
                    break;
                }
            }
            if (hitbox!=null){
                break;
            }
        }
        return hitbox;
    }

    public void placeThings(int x, int y){
        Tile tileToUse = getColidingTile(x, y);
        if (tileToUse!=null){
            if (mode) {
                int x1 = (int) (tileToUse.getPosition().getX() / handler.getCamera().getZoomFactor());
                int y1 = (int) (tileToUse.getPosition().getY() / handler.getCamera().getZoomFactor());
                if (dropdown.getValue().equals("Eraser")){
                    for (int i = 0;i<handler.getLoadedWorld().getEntities().size();i++) {
                        if (handler.getLoadedWorld().getEntities().get(i).getPosition().equals(new Vector2(x1, y1))){
                            handler.getLoadedWorld().getEntities().remove(handler.getLoadedWorld().getEntities().get(i));
                        }
                    }
                }else {
                    Entity e = null;
                    switch (EntityType.valueOf(dropdown.getValue())) {
                        case ROBOT:
                            e = new Robot(new Vector2(x1, y1), 1, new Vector2(0, 0), handler);
                            break;
                        case BALLROBOT:
                            e = new BallRobot(new Vector2(x1, y1), 1, new Vector2(0, 0), handler);
                            break;
                        case PLAYER:
                            e = new Player(new Vector2(x1, y1), 1, new Vector2(0, 0), handler);
                            break;
                    }
                    if (e != null) {
                        handler.getLoadedWorld().getEntities().add(e);
                    }
                }
            }else {
                int x2 = tileToUse.getPosition().getX() / Tiles.TILE_WIDTH;
                int y2 = tileToUse.getPosition().getY() / Tiles.TILE_HEIGHT;
                if (handler.getLoadedWorld().getTiles()[y2][x2].getId()!=Tiles.findTileByName(dropdown.getValue()).getId()) {
                    handler.getLoadedWorld().setTile(x2, y2, Tiles.findTileByName(dropdown.getValue()));
                }
            }
        }
    }
}
