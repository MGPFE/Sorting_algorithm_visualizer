package com.mg.algorithms;

import com.mg.gui.Drawable;
import java.awt.EventQueue;

public class QuickSort {
    private final Drawable drawable;

    public QuickSort(Drawable drawable) {
        this.drawable = drawable;
    }

    public void quickSort(Integer[] arr, boolean reverse) {
        quickSort(arr, 0, arr.length - 1, reverse);
    }

    public void quickSort(Integer[] arr, int start, int end, boolean reverse) {
        if (start < end) {
            int pivot = partition(arr, start, end, reverse);

            quickSort(arr, start, pivot - 1, reverse);
            quickSort(arr, pivot + 1, end, reverse);
            EventQueue.invokeLater(drawable::drawData);
            try {
                Thread.sleep(drawable.getAlgoSpeed());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private int partition(Integer[] arr, int start, int end, boolean reverse) {
        int pivot = arr[end];
        int i = (start - 1);

        for (int j = start; j < end; j++) {
            if (reverse) {
                if (arr[j] >= pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            } else {
                if (arr[j] <= pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;

        return i + 1;
    }
}
