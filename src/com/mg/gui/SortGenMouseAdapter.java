package com.mg.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

public class SortGenMouseAdapter extends MouseAdapter {
    private final JButton btn;

    public SortGenMouseAdapter(JButton btn) {
        this.btn = btn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        btn.setBackground(getRandomColor());
    }

    private Color getRandomColor() {
        return new Color(
            ThreadLocalRandom.current().nextInt(0, 255),
            ThreadLocalRandom.current().nextInt(0, 255),
            ThreadLocalRandom.current().nextInt(0, 255)
        );
    }

}
