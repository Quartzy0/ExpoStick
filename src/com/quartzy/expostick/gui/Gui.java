package com.quartzy.expostick.gui;

import com.quartzy.expostick.utills.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui {
    private List<GuiComponent> components = new ArrayList<>();
    private String name;
    private boolean pauseGame;
    protected Handler handler;

    public Gui(String name, boolean pauseGame, Handler handler) {
        this.name = name;
        this.pauseGame = pauseGame;
        this.handler = handler;
    }

    public void tick(){
        for (GuiComponent component : components) {
            component.tick();
        }
    }

    public void render(Graphics2D g){
        for (GuiComponent component : components) {
            component.render(g);
        }
    }

    public boolean isPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    public String getName() {
        return name;
    }

    public List<GuiComponent> getComponents() {
        return components;
    }

    public void addComponet(GuiComponent component){
        components.add(component);
    }
}
