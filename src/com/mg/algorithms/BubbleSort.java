package com.mg.algorithms;

import com.mg.gui.Drawable;
import java.awt.EventQueue;

public class BubbleSort {
    private final Drawable drawable;

    public BubbleSort(Drawable drawable) {
        this.drawable = drawable;
    }

    public void bubbleSort(Integer[] arr, boolean reverse) {
        boolean sorted = false;
        int tmp;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < arr.length; i++) {
                if (reverse) {
                    if (i != (arr.length - 1) && arr[i] < arr[i + 1]) {
                        tmp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = tmp;
                        sorted = false;
                        EventQueue.invokeLater(drawable::drawData);
                        try {
                            Thread.sleep(drawable.getAlgoSpeed());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } else {
                    if (i != (arr.length - 1) && arr[i] > arr[i + 1]) {
                        tmp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = tmp;
                        sorted = false;
                        EventQueue.invokeLater(drawable::drawData);
                        try {
                            Thread.sleep(drawable.getAlgoSpeed());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
    }
}
