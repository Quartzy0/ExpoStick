package com.quartzy.expostick.gfx;

import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {

    private List<BufferedImage> frames;
    private int currentFrame;
    private int delayBtwFrames;
    private long lastTime;

    public Animation(List<BufferedImage> frames, int delayBtwFrames) {
        this.frames = frames;
        this.currentFrame = 0;
        this.delayBtwFrames = delayBtwFrames;
    }

    public BufferedImage getCurrentFrame(){
        if (frames!=null && frames.size()!=0) {
            return frames.get(currentFrame);
        }
        return null;
    }
    public void check(){
        if (System.currentTimeMillis()-lastTime>=delayBtwFrames){
            currentFrame++;
            if (currentFrame>=frames.size()){
                currentFrame = 0;
            }
            lastTime = System.currentTimeMillis();
        }
    }

    public void addFrame(BufferedImage frame){
        frames.add(frame);
    }

    public void addFrame(BufferedImage frame, int index){
        frames.add(index, frame);
    }

    public void removeFrame(BufferedImage frame){
        frames.remove(frame);
    }

    public void removeFrame(int index){
        frames.remove(index);
    }
}
