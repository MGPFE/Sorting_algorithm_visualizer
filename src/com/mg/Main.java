package com.mg;

import com.mg.gui.Frame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Frame::new);
    }
}
