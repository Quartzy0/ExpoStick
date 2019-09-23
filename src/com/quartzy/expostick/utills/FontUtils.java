package com.quartzy.expostick.utills;

import java.awt.*;

public class FontUtils {
    public static Dimension getTextSize(String text, Font f, Graphics g) {
        Dimension size=new Dimension();
        g.setFont(f);
        FontMetrics fm=g.getFontMetrics(f);
        size.width=fm.stringWidth(text);
        size.height=fm.getHeight();

        return size;
    }
}
