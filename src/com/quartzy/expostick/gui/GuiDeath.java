package com.quartzy.expostick.gui;

import com.quartzy.expostick.utills.Assets;
import com.quartzy.expostick.utills.FileManager;
import com.quartzy.expostick.utills.FontUtils;
import com.quartzy.expostick.utills.Handler;
import com.quartzy.expostick.world.World;

import java.awt.*;
import java.util.Objects;

public class GuiDeath extends Gui {
    private GuiButton restartBtn;
    private GuiButton quitBtn;
    private GuiText deathTxt;

    private boolean first = true;

    public GuiDeath(Handler handler) {
        super("Death screen", false, handler);
        restartBtn = new GuiButton(handler.getDisplay().getWidth()/2-50, handler.getDisplay().getHeight()/2-50, 100, 40, 3, new Color(30, 30, 30), new Color(50, 50, 50), "Restart", new Color(100, 0, 0));
        quitBtn = new GuiButton(handler.getDisplay().getWidth()/2-50, handler.getDisplay().getHeight()/2+10, 100, 40, 3, new Color(30, 30, 30), new Color(50, 50, 50), "Quit", new Color(100, 0, 0));
        deathTxt = new GuiText(0, 0, "You died!", Color.WHITE, Assets.font.deriveFont(20F));
        restartBtn.setClickEvent(new OnClickedEvent() {
            @Override
            public void clicked() {
                handler.setLoadedWorld(new World(Objects.requireNonNull(FileManager.loadWorld(handler.getLoadedWorld().getName())), handler));
                handler.setCurrentGuiScreen(new GuiIngame(handler));
            }
        });
        quitBtn.setClickEvent(new OnClickedEvent() {
            @Override
            public void clicked() {
                Handler.quit(0);
            }
        });
        addComponet(restartBtn);
        addComponet(quitBtn);
        addComponet(deathTxt);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 204));
        g.fillRect(0, 0, handler.getDisplay().getWidth(), handler.getDisplay().getHeight());
        if (first){
            Dimension textSize = FontUtils.getTextSize(deathTxt.getText(), deathTxt.getCustomFont(), g);
            deathTxt.setX((int) (handler.getDisplay().getWidth()/2-textSize.getWidth()/2));
            deathTxt.setY((int) (textSize.getHeight() + 25));
            first = false;
        }
        super.render(g);
    }
}
