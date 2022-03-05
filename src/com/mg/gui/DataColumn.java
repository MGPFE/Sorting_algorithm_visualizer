package com.mg.gui;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a single column in dataPanel
 * inside of {@link Frame Frame}'s method {@link Frame#drawData() drawData()}.
 * @see Frame
 */
public class DataColumn extends JPanel {
    private final int DATA_VALUE;

    public DataColumn(int dataValue) {
        this.DATA_VALUE = dataValue;
    }

    @Override
    public void paintComponent(Graphics g) {
        int tmpColor;

        if (DATA_VALUE > 255) tmpColor = 255;
        else tmpColor = Math.max(DATA_VALUE, 0);

        // Modulus 31 creates a gradient effect
        g.setColor(new Color(tmpColor, tmpColor % 31, tmpColor % 31));
        g.fillRect(0, this.getHeight() - DATA_VALUE - 200, this.getWidth(), this.getHeight());
    }
}
