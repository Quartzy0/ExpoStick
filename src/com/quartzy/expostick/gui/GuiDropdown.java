package com.quartzy.expostick.gui;

import com.quartzy.expostick.input.Clicked;
import com.quartzy.expostick.input.Mouse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiDropdown extends GuiButton {

    private HashMap<String, Rectangle> values;
    private boolean open;
    private String value;

    public GuiDropdown(int x, int y, int width, int height, int borderThickness, Color borderColor, Color insideColor, List<String> valuesS, Color textColor) {
        super(x, y, width, height, borderThickness, borderColor, insideColor, valuesS.get(0), textColor);
        values = new HashMap<>();
        for (String value : valuesS) {
            values.put(value, null);
        }
        value = valuesS.get(0);
        setClickEvent(new OnClickedEvent() {
            @Override
            public void clicked() {
                open = !open;
            }
        });
        Mouse.addClickEvent(new Clicked() {
            @Override
            public void clicked(MouseEvent event) {
                if (open && event.getButton()==MouseEvent.BUTTON1) {
                    List<String> strings = new ArrayList<>(values.keySet());
                    if (values.get(strings.get(0))==null) return;
                    for (String string : strings) {
                        if (values.get(string).contains(event.getX(), event.getY())) {
                            value = string;
                            open = false;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        setText(value);
    }

    public List<String> getValues() {
        return new ArrayList<>(values.keySet());
    }

    public boolean isOpen() {
        return open;
    }

    public String getValue() {
        return value;
    }

    public void addValue(String value){
        values.put(value, null);
    }

    public void removeValue(String value){
        values.remove(value);
    }

    public void setValues(List<String> values) {
        this.values.clear();
        for (String value : values) {
            this.values.put(value, null);
        }
        value = values.get(0);
    }

    @Override
    public void render(Graphics g) {
        if (open){
            HashMap<String, Rectangle> newValues = new HashMap<>();
            for (int a = 0;a<values.size();a++){
                g.setColor(getInsideColor());
                g.fillRect(getX() + getBorderThickness(), getY()+getHeight()+(getHeight()*a), getWidth()-getBorderThickness()*2, getHeight());

                List<String> strings = new ArrayList<>(values.keySet());

                newValues.put(strings.get(a), new Rectangle(getX() + getBorderThickness(), getY()+getHeight()+(getHeight()*a), getWidth()-getBorderThickness()*2, getHeight()));

                g.setColor(getTextColor());
                float[] floats = adaptLabelFont(new Rectangle(getWidth() - getBorderThickness()*2, getHeight() - getBorderThickness()*2), g, strings.get(a));
                g.setFont(g.getFont().deriveFont(floats[0]));
                g.drawString(strings.get(a), getX() + (getWidth()-getBorderThickness()*2) -(int) floats[2]+getBorderThickness(), (int) (getY() + getHeight()/2 + floats[1]/4 + getHeight() + getBorderThickness() + (getHeight() * a)));
            }
            values = newValues;
        }
        super.render(g);
    }
}
