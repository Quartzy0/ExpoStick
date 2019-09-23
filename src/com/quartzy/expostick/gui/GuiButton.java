package com.quartzy.expostick.gui;

import com.quartzy.expostick.input.Clicked;
import com.quartzy.expostick.input.Mouse;
import com.quartzy.expostick.utills.FontUtils;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GuiButton extends GuiComponent {

    private int width, height;
    private OnClickedEvent clickEvent;
    private String text;
    private Color borderColor, insideColor;
    private int borderThickness;
    private Color textColor;

    public GuiButton(int x, int y, int width, int height, int borderThickness, Color borderColor, Color insideColor, String text, Color textColor) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.borderColor = borderColor;
        this.insideColor = insideColor;
        this.borderThickness = borderThickness;
        this.text = text;
        this.textColor = textColor;
        Mouse.addClickEvent(new Clicked() {
            @Override
            public void clicked(MouseEvent event) {
                if (event.getButton()==MouseEvent.BUTTON1){
                    if (event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height){
                        if (clickEvent!=null){
                            clickEvent.clicked();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void tick() {

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public OnClickedEvent getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(OnClickedEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getInsideColor() {
        return insideColor;
    }

    public void setInsideColor(Color insideColor) {
        this.insideColor = insideColor;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    @Override
    public void render(Graphics g) {
        for (int y = 0;y<height;y++){
            for (int x = 0;x<width;x++){
                if (y < borderThickness || y > height- borderThickness || x < borderThickness || x > width- borderThickness){
                    g.setColor(borderColor);
                }else {
                    g.setColor(insideColor);
                }
                g.fillRect(getX()+x, getY()+y, 1, 1);
            }
        }
        g.setColor(textColor);
        float[] floats = adaptLabelFont(new Rectangle(getWidth() - borderThickness*4, getHeight() - borderThickness*4), g, text);
        g.setFont(g.getFont().deriveFont(floats[0]));
        g.drawString(text, getX() + (getWidth()-borderThickness*2) -(int) floats[2], (int) (getY() + getHeight()/2 + floats[1]/3));
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    protected float[] adaptLabelFont(Rectangle bounds, Graphics g, String text) {
        int fontSize=3;
        int height = 0;
        int width = 0;
        Font f=g.getFont();

        Rectangle r1=new Rectangle();
        Rectangle r2=new Rectangle();
        while (fontSize<255) {
            Dimension textSize = FontUtils.getTextSize(text, f.deriveFont(f.getStyle(), fontSize), g);
            height = textSize.height;
            width = textSize.width;
            r1.setSize(textSize);
            r2.setSize(FontUtils.getTextSize(text, f.deriveFont(f.getStyle(),fontSize+1), g));
            if (bounds.contains(r1) && ! bounds.contains(r2)) {
                break;
            }
            fontSize++;
        }

        return new float[]{fontSize, height, width};
    }
}
