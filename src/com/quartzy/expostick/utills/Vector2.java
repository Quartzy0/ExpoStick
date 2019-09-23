package com.quartzy.expostick.utills;

import java.io.Serializable;
import java.util.Objects;

public class Vector2 implements Serializable {
    public long serialVersionUID = 26362728213737637L;
    
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vector2 reverse(){
        this.x = -x;
        this.y = -y;
        return this;
    }

    public Vector2 subtract(Vector2 vec2){
        this.x -= vec2.getX();
        this.y -= vec2.getY();
        return this;
    }

    public static Vector2 subtract(Vector2 vec1, Vector2 vec2){
        return new Vector2(vec1.getX()-vec2.getY(), vec1.getY()-vec2.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return x == vector2.x &&
                y == vector2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + "-" + y;
    }
}
