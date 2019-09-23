package com.quartzy.expostick;

import com.quartzy.expostick.gfx.Display;

import java.util.Arrays;

public class Main {

    public static Display display;

    public static void main(String[] args){
        System.out.println(Arrays.toString(args));
        
        display = new Display("Expo stick", 700, 700);
        display.init();
    }

}
