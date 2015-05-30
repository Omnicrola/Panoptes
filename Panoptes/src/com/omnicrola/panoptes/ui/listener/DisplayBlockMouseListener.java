package com.omnicrola.panoptes.ui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.omnicrola.panoptes.ui.BlockPanel;
import com.omnicrola.panoptes.ui.DisplayBlockMouseHandler;

public class DisplayBlockMouseListener implements MouseListener, MouseMotionListener {

    private final BlockPanel blockPanel;
    private final DisplayBlockMouseHandler mouseHandler;
    private boolean isSelecting;

    public DisplayBlockMouseListener(BlockPanel blockPanel, DisplayBlockMouseHandler mouseHandler) {
        this.blockPanel = blockPanel;
        this.mouseHandler = mouseHandler;
        this.isSelecting = false;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) {
            this.mouseHandler.loadSelectionAt(event.getX(), event.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (this.isSelecting) {
            this.mouseHandler.setDragEnd(event.getX(), event.getY());
            mouseMoved(event);
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        this.blockPanel.displayMousetip(true);
        this.blockPanel.repaint();
    }

    @Override
    public void mouseExited(MouseEvent event) {
        this.blockPanel.displayMousetip(false);
        this.blockPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        this.mouseHandler.mouseMoved(event.getX(), event.getY());
        this.blockPanel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            this.isSelecting = true;
            this.mouseHandler.setDragStart(event.getX(), event.getY());
            this.blockPanel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            this.isSelecting = false;
            this.mouseHandler.setDragEnd(event.getX(), event.getY());
            this.blockPanel.repaint();
        }
    }

}
