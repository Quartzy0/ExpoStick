package com.quartzy.expostick.gui;

import com.quartzy.expostick.utills.Handler;

import java.awt.*;

public class GuiIngame extends Gui {

    private GuiProgressBar progressBar;
    private GuiText hpTxt;

    public GuiIngame(Handler handler) {
        super("Ingame Gui", false, handler);
        hpTxt = new GuiText(10, 60, "100/100", Color.BLACK);
        addComponet(hpTxt);
        progressBar = new GuiProgressBar(10, 20, 100, 100, 20, Color.BLACK, Color.RED, Color.BLUE, 5);
        addComponet(progressBar);
    }

    @Override
    public void tick() {
        super.tick();
        progressBar.setPercent(handler.getPlayer().getHealth());
        hpTxt.setText(handler.getPlayer().getHealth() + "/100");
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }
}
