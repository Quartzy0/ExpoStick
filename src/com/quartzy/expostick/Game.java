package com.quartzy.expostick;


import com.quartzy.expostick.entities.Entity;
import com.quartzy.expostick.entities.Player;
import com.quartzy.expostick.gfx.Camera;
import com.quartzy.expostick.gfx.Display;
import com.quartzy.expostick.input.KeyListener;
import com.quartzy.expostick.input.MouseManager;
import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.FileManager;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.utills.Vector2;
import com.quartzy.expostick.weapons.Crowbar;
import com.quartzy.expostick.weapons.Pistol;
import com.quartzy.expostick.world.World;
import javafx.application.Platform;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class Game extends Thread{

    boolean running = true;
    Display display;
    public int ticks = 0;
    public Handler handler;
    public Camera camera;
    private boolean firstTime = true;

    public Game(Handler handler) {
        this.handler = handler;
    }

    private void init(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File(Handler.GAME_DIR_PATH + File.separator + "options.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Handler.LEVEL_MAKING = Boolean.parseBoolean(properties.getProperty("level_making", "FALSE"));
        Handler.DEBUG = Boolean.parseBoolean(properties.getProperty("debug_mode", "FALSE"));
        handler.init();
        handler.setDisplay(Main.display);
        this.display = handler.getDisplay();
        handler.setLoadedWorld(new World(Objects.requireNonNull(FileManager.loadWorld("Level_1")), handler));
        camera = new Camera(new Vector2(0,0), handler);
        handler.setCamera(camera);
        KeyListener keyManager = new KeyListener();
        MouseManager mouseManager = new MouseManager();
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getFrame().addMouseWheelListener(mouseManager);
        display.getCanvas().addKeyListener(keyManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseWheelListener(mouseManager);
    }

    @Override
    public void run() {
        Assets.init();
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;


        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                System.out.println("Tick and frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

    public void tick(){
        if (!Handler.LEVEL_MAKING) {
            if (!handler.getCurrentGuiScreen().isPauseGame()) {
                camera.tick();
                for (int a = 0; a < handler.getLoadedWorld().getEntities().size(); a++) {
                    Entity e = handler.getLoadedWorld().getEntities().get(a);
                    e.tick();
                    if (e instanceof Player) {
                        if (firstTime) {
                            firstTime = false;
                            e.setWeapon(new Pistol(e, handler));
                        }
                        camera.setFocusedPos(e.getPosition());
                        handler.setPlayer((Player) e);
                    }
                }
                for (int i = 0; i < handler.getLoadedWorld().getProjectiles().size(); i++) {
                    handler.getLoadedWorld().getProjectiles().get(i).tick();
                }
            }
            handler.getCurrentGuiScreen().tick();
        }else {
            camera.tick();
            handler.getCurrentGuiScreen().tick();
        }
    }

    public void render(){
        display.setBf(display.getCanvas().getBufferStrategy());
        if(display.getBf() == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        display.setG((Graphics2D) display.getBf().getDrawGraphics());

        Graphics2D g = display.getG();
        g.clearRect(0, 0, display.getWidth(), display.getHeight());

        g.setFont(Assets.font);

        camera.render(g);

        handler.getCurrentGuiScreen().render(g);

        display.getBf().show();
        g.dispose();
    }
}
