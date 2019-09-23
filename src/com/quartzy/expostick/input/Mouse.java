package com.quartzy.expostick.input;

import java.util.ArrayList;
import java.util.List;

public class Mouse {
    public static boolean BUTTON1;
    public static boolean BUTTON2;
    public static boolean BUTTON3;
    public static boolean mouseInWindow = true;
    static List<Clicked> clickedList = new ArrayList<>();
    static List<MouseWheelMoved> mouseWheelMovedList = new ArrayList<>();
    static List<Dragged> draggedList = new ArrayList<>();
    public static int x;
    public static int y;

    public static void addClickEvent(Clicked clicked){
        clickedList.add(clicked);
    }

    public static void addMouseWheelEvent(MouseWheelMoved mouseWheelMoved){
        mouseWheelMovedList.add(mouseWheelMoved);
    }

    public static void addMouseDragEvent(Dragged dragged){
        draggedList.add(dragged);
    }
}
