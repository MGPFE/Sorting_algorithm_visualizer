package com.mg.algorithms;

import com.mg.gui.Drawable;
import java.awt.EventQueue;

public class MergeSort {
    private final Drawable drawable;

    public MergeSort(Drawable drawable) {
        this.drawable = drawable;
    }

    public void mergeSort(Integer[] arr, boolean reverse) {
        mergeSort(arr, 0, arr.length - 1, reverse);
    }

    private void mergeSort(Integer[] arr, int start, int end, boolean reverse) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid, reverse);
            mergeSort(arr, mid + 1, end, reverse);
            merge(arr, start, mid, end, reverse);
            EventQueue.invokeLater(drawable::drawData);
            try {
                Thread.sleep(drawable.getAlgoSpeed());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void merge(Integer[] arr, int start, int mid, int end, boolean reverse) {
        int[] arrCopy = new int[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= end) {
            if (reverse) {
                if (arr[i] <= arr[j]) {
                    arrCopy[k++] = arr[i++];
                } else {
                    arrCopy[k++] = arr[j++];
                }
            } else {
                if (arr[i] >= arr[j]) {
                    arrCopy[k++] = arr[i++];
                } else {
                    arrCopy[k++] = arr[j++];
                }
            }
        }

        while (i <= mid) {
            arrCopy[k++] = arr[i++];
        }

        while (j <= end) {
            arrCopy[k++] = arr[j++];
        }

        for (i = start; i <= end; i++) {
            arr[i] = arrCopy[i - start];
        }
    }
}
