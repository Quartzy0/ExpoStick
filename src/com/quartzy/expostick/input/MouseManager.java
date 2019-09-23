package com.quartzy.expostick.input;

import java.awt.event.*;

public class MouseManager implements MouseMotionListener, MouseListener, MouseWheelListener {

    private boolean drdrrdrdrd;
    private boolean prevrddrrdrdr;

	@Override
	public void mousePressed(MouseEvent e) {
        Mouse.x = e.getX();
        Mouse.y = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1){
            Mouse.BUTTON1 = true;
        }else if(e.getButton() == MouseEvent.BUTTON3){
		    Mouse.BUTTON3 = true;
		}else if (e.getButton() == MouseEvent.BUTTON2){
            Mouse.BUTTON2 = true;
        }
        for (int a = 0;a<Mouse.clickedList.size();a++){
            Mouse.clickedList.get(a).clicked(e);
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
        Mouse.x = e.getX();
        Mouse.y = e.getY();
        if (e.getButton() == MouseEvent.BUTTON1){
            Mouse.BUTTON1 = false;
        }else if(e.getButton() == MouseEvent.BUTTON3){
            Mouse.BUTTON3 = false;
        }else if (e.getButton() == MouseEvent.BUTTON2){
            Mouse.BUTTON2 = false;
        }
        if (drdrrdrdrd && !prevrddrrdrdr){
            prevrddrrdrdr = true;
            for (Dragged d : Mouse.draggedList){
                d.draggingFinished(e);
            }
        }
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.x = e.getX();
		Mouse.y = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
        Mouse.x = e.getX();
        Mouse.y = e.getY();
        for (Dragged d : Mouse.draggedList){
            d.dragged(e);
            drdrrdrdrd = true;
            prevrddrrdrdr = false;
        }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Mouse.mouseInWindow = true;
        Mouse.x = e.getX();
        Mouse.y = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Mouse.mouseInWindow = false;
        Mouse.x = e.getX();
        Mouse.y = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        for (MouseWheelMoved m : Mouse.mouseWheelMovedList){
            m.moved(e);
        }
	}
}