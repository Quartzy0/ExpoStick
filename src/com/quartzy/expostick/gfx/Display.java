package com.quartzy.expostick.gfx;

import com.quartzy.expostick.Game;
import com.quartzy.expostick.utills.Handler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Display implements Serializable {

    private JFrame frame;
    private Canvas canvas;
    private String title;
    private int width, height;
    private Graphics2D g;
    private BufferStrategy bf;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        canvas = new Canvas();
        canvas.setSize(width-10, height-10);
        frame.add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        g = (Graphics2D) canvas.getGraphics();
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-frame.getHeight()/2-20);
        Handler handler = new Handler();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handler.quit(0);
            }
        });
        try {
            frame.setIconImage(ImageIO.read(new File(Handler.GAME_DIR_PATH + "/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game gameLoop = new Game(handler);
        gameLoop.start();
    }

    public Graphics2D getG() {
        return g;
    }

    public void setG(Graphics2D g) {
        this.g = g;
    }

    public BufferStrategy getBf() {
        return bf;
    }

    public void setBf(BufferStrategy bf) {
        this.bf = bf;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
