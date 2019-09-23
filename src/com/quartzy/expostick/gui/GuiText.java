package com.quartzy.expostick.gui;

import java.awt.*;

public class GuiText extends GuiComponent {
    private String text;
    private Color color;
    private Font customFont;

    public GuiText(int x, int y, String text) {
        super(x, y);
        this.text = text;
    }

    public GuiText(int x, int y, String text, Font customFont) {
        super(x, y);
        this.text = text;
        this.customFont = customFont;
    }

    public GuiText(int x, int y, String text, Color color, Font customFont) {
        super(x, y);
        this.text = text;
        this.color = color;
        this.customFont = customFont;
    }

    public GuiText(int x, int y, String text, Color color) {
        super(x, y);
        this.color = color;
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Color prevColor = g.getColor();
        Font prevFont = g.getFont();
        if (color!=null) {
            g.setColor(color);
        }
        if (customFont!=null){
            g.setFont(customFont);
        }
        g.drawString(text, getX(), getY());
        g.setColor(prevColor);
        g.setFont(prevFont);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getCustomFont() {
        return customFont;
    }

    public void setCustomFont(Font customFont) {
        this.customFont = customFont;
    }
}
