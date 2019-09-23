package com.quartzy.expostick.utills;

import java.awt.*;

public class Circle {

    private final int radius;
    private final Color color;
    private final Point location;

    public Circle(int x, int y, int radius, Color color) {
        this.location = new Point(x, y);
        this.radius = radius;
        this.color = color;
    }

    public boolean contains(int x, int y) {
        double distance = Point.distance(x, y, location.x, location.y);
        double radiusD = radius;
        if (radiusD >= distance) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(location.x - radius, location.y - radius, radius + radius,
                radius + radius);
    }
}