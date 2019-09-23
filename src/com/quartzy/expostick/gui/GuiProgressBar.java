package com.quartzy.expostick.gui;

import java.awt.*;

public class GuiProgressBar extends GuiComponent {
    private double percent;
    private int width, height;
    private Color borderColor, insideColorFilled, insideColorNotFilled;
    private int borderThickness;
    private static double ammountToFillInPixels;

    public GuiProgressBar(int x, int y, int percent, int width, int height, Color borderColor, Color insideColorFilled, Color insideColorNotFilled, int borderWidth) {
        super(x, y);
        this.percent = percent;
        this.width = width;
        this.height = height;
        this.borderColor = borderColor;
        this.insideColorFilled = insideColorFilled;
        this.insideColorNotFilled = insideColorNotFilled;
        this.borderThickness = borderWidth;
    }

    @Override
    public void tick() {
        ammountToFillInPixels = (width- borderThickness *2) *(percent/100);
    }

    public int getPercent() {
        return (int) percent;
    }

    public void setPercent(int percent) {
        if (percent<=100) {
            this.percent = percent;
        }
    }

    @Override
    public void render(Graphics g) {
        for (int y = 0;y<height;y++){
            for (int x = 0;x<width;x++){
                if (y < borderThickness || y > height- borderThickness || x < borderThickness || x > width- borderThickness){
                    g.setColor(borderColor);
                }else if (x-borderThickness<=ammountToFillInPixels){
                    g.setColor(insideColorFilled);
                }else {
                    g.setColor(insideColorNotFilled);
                }
                g.fillRect(getX()+x, getY()+y, 1, 1);
            }
        }
    }
}
