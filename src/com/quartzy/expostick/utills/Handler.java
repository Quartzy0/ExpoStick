package com.quartzy.expostick.utills;

import com.quartzy.expostick.netty.NettyHandler;
import com.quartzy.expostick.entities.Player;
import com.quartzy.expostick.gfx.Camera;
import com.quartzy.expostick.gfx.Display;
import com.quartzy.expostick.gui.Gui;
import com.quartzy.expostick.gui.GuiIngame;
import com.quartzy.expostick.gui.LevelMakerGui;
import com.quartzy.expostick.input.KeyBind;
import com.quartzy.expostick.input.KeyBinds;
import com.quartzy.expostick.netty.PacketReceivedEvent;
import com.quartzy.expostick.netty.packets.AuthPacket;
import com.quartzy.expostick.netty.packets.Packet;
import com.quartzy.expostick.netty.packets.WorldDataPacket;
import com.quartzy.expostick.world.World;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;

public class Handler implements Serializable {

    private static Display display;
    private World loadedWorld;
    public static KeyBind W;
    public static KeyBind A;
    public static KeyBind S;
    public static KeyBind D;
    public static KeyBind PLUS;
    public static KeyBind MINUS;
    public final static int GRAVITY = 1;
    public static final int MAX_VELOCITY = 10;
    public static boolean DEBUG = false;
    public static boolean LEVEL_MAKING = false;
    private Gui currentGuiScreen;
    private static Player player;
    private Camera camera;
    private NettyHandler nettyHandler;
    
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player playerIn) {
        player = playerIn;
    }

    public static String GAME_DIR_PATH = System.getenv("APPDATA") + File.separator + "ExpoStick";

    public void init(){
        W = new KeyBind("Forward", KeyEvent.VK_W);
        A = new KeyBind("Left", KeyEvent.VK_A);
        S = new KeyBind("Down", KeyEvent.VK_S);
        D = new KeyBind("Right", KeyEvent.VK_D);
        PLUS = new KeyBind("Plus", KeyEvent.VK_PLUS);
        MINUS = new KeyBind("Minus", KeyEvent.VK_MINUS);
        KeyBinds.keyBinds.add(W);
        KeyBinds.keyBinds.add(A);
        KeyBinds.keyBinds.add(S);
        KeyBinds.keyBinds.add(D);
        KeyBinds.keyBinds.add(PLUS);
        KeyBinds.keyBinds.add(MINUS);
        currentGuiScreen = new GuiIngame(this);
        if (Handler.LEVEL_MAKING) currentGuiScreen = new LevelMakerGui(this);
        nettyHandler = new NettyHandler();
    }

    public void quit(int status){
        System.exit(status);
    }

    public Gui getCurrentGuiScreen() {
        return currentGuiScreen;
    }

    public void setCurrentGuiScreen(Gui currentGuiScreen) {
        this.currentGuiScreen = currentGuiScreen;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        Handler.display = display;
    }

    public World getLoadedWorld() {
        return loadedWorld;
    }

    public void setLoadedWorld(World loadedWorld) {
        this.loadedWorld = loadedWorld;
    }
    
    public NettyHandler getNettyHandler(){
        return nettyHandler;
    }
}
