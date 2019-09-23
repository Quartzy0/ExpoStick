package com.quartzy.expostick.input;

import java.util.ArrayList;
import java.util.List;

public class KeyBinds {
    public static List<KeyBind> keyBinds = new ArrayList<>();
    public static List<Pressed> allKeyPressEvents = new ArrayList<>();

    public static KeyBind findByName(String name){
        for (KeyBind b : keyBinds){
            if (b.getName().equals(name)){
                return b;
            }
        }
        return null;
    }
}
