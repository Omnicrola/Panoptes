package com.omnicrola.panoptes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.omnicrola.panoptes.data.DisplayBlock;
import com.omnicrola.panoptes.ui.DisplayBlockMouseHandler.MousePoint;

public class BlockPanel extends JPanel {

    private static final long serialVersionUID = 1714220089543083502L;

    private final DisplayBlockModelPresenter blockList;
    private final MouseTip mousetip;
    private boolean displayMousetip;
    private final DisplayBlockMouseHandler mouseHandler;

    public BlockPanel(DisplayBlockModelPresenter blockList, MouseTip mousetip,
            DisplayBlockMouseHandler mouseHandler) {
        this.blockList = blockList;
        this.mousetip = mousetip;
        this.mouseHandler = mouseHandler;
    }

    public void displayMousetip(boolean shouldDisplay) {
        this.displayMousetip = shouldDisplay;
        repaint();
    }

    @Override
    public int getHeight() {
        return this.blockList.getHeight();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getWidth() {
        return this.blockList.getWidth();
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.blockList.paint(graphics);
        if (this.displayMousetip) {
            paintMousetip(graphics);
        }
    }

    private void paintMousetip(Graphics graphics) {
        MousePoint currentLocation = this.mouseHandler.getCurrentMouseLocation();
        int x = currentLocation.getX();
        int y = currentLocation.getY();

        this.mousetip.moveToLeftSide(x > getParent().getWidth() - 200);
        this.mousetip.moveToTopSide(y > getParent().getHeight() - 200);
        this.mousetip.setPixelPosition(x, y);

        int columnIndex = x / DisplayBlock.WIDTH;
        int rowIndex = y / DisplayBlock.HEIGHT;
        this.mousetip.updateBlockPosition(rowIndex, columnIndex);
        this.mousetip.paint(graphics);
    }

}
