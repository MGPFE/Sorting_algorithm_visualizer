package com.mg.gui;

import com.mg.exceptions.AlgorithmNotFoundException;
import com.mg.algorithms.BubbleSort;
import com.mg.algorithms.MergeSort;
import com.mg.algorithms.QuickSort;

import java.util.concurrent.ExecutorService;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;
import java.util.concurrent.Executors;
import javax.swing.event.ChangeEvent;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * This class generates the GUI for sorting algorithm visualization.
 * @author Mariusz Gworek
 * @version 1.0
 */
public class Frame extends JFrame implements Drawable, ItemListener, ActionListener, ChangeListener {
    /**
     * This enum represents all the available sorting algorithms and contains
     * their names for GUI to use.
     */
    private enum Algo {
        MERGE_SORT("Merge sort"),
        QUICK_SORT("Quick sort"),
        BUBBLE_SORT("Bubble sort");

        private final String name;

        Algo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    // Constants
    private final Font FONT = new Font("Consolas", Font.PLAIN, 20);
    /**
     * This constant variable represents the amount of DataColumns to be generated
     * @see DataColumn
     */
    private final int DATA_AMOUNT = 200;
    // Logic
    /**
     * This constant Integer array contains
     * all of the generated numbers from {@link #fillData() FillData}
     * used for DataColumn generation
     * @see DataColumn
     */
    private final Integer[] DATA = new Integer[DATA_AMOUNT];
    private boolean reverseSortOrderFlag = false;
    private boolean dataAlreadySorted = false;
    private ExecutorService executor;
    // GUI
    private JComboBox<String> sortingAlgorithms;
    private Algo selectedAlgo = Algo.MERGE_SORT;
    private JCheckBox reverseSortOrderCheck;
    /**
     * This JSpinner is used to set the speed of sort execution,
     * the lower the count, the higher the execution speed
     */
    private JSpinner algoSpeedSpinner;
    private int algoSpeed = 30;
    /**
     * This JPanel is the main container for DataColumns
     * @see DataColumn
     */
    private JPanel dataPanel;

    public Frame() {
        final int WIDTH = 900;
        final int HEIGHT = 600;

        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setTitle("Sorting algorithm visualizer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setNimbusLook();

        drawControlPanel();
        fillData();
        drawData();

        this.setVisible(true);
    }

    // DRAW FUNCTIONS
    private void drawControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        sortingAlgorithms = new JComboBox<>();
        sortingAlgorithms.setFont(FONT);
        sortingAlgorithms.addItem(Algo.MERGE_SORT.getName());
        sortingAlgorithms.addItem(Algo.QUICK_SORT.getName());
        sortingAlgorithms.addItem(Algo.BUBBLE_SORT.getName());
        sortingAlgorithms.setSelectedItem(0);
        sortingAlgorithms.addItemListener(this);

        JButton sortBtn = new JButton("Sort");
        sortBtn.setFont(FONT);
        sortBtn.addActionListener(this);
        sortBtn.addMouseListener(new SortGenMouseAdapter(sortBtn));

        JButton genBtn = new JButton("Generate");
        genBtn.setFont(FONT);
        genBtn.addActionListener(this);
        genBtn.addMouseListener(new SortGenMouseAdapter(genBtn));

        reverseSortOrderCheck = new JCheckBox("Reverse order");
        reverseSortOrderCheck.setSelected(reverseSortOrderFlag);
        reverseSortOrderCheck.addItemListener(this);

        JLabel algoSpeedLabel = new JLabel("Algorithm speed (5 - Fast, 250 - Slow): ");

        algoSpeedSpinner = new JSpinner(new SpinnerNumberModel(algoSpeed, 5, 250, 5));
        algoSpeedSpinner.addChangeListener(this);

        controlPanel.add(sortingAlgorithms);
        controlPanel.add(sortBtn);
        controlPanel.add(genBtn);
        controlPanel.add(reverseSortOrderCheck);
        controlPanel.add(new JSeparator(SwingConstants.VERTICAL));
        controlPanel.add(algoSpeedLabel);
        controlPanel.add(algoSpeedSpinner);

        this.add(controlPanel, BorderLayout.NORTH);
    }

    public void drawData() {
        drawData(DATA);
    }

    /**
     * This method draws the data on {@link #dataPanel} JPanel
     * @param data data to draw
     */
    private void drawData(Integer[] data) {
        if (dataPanel != null) {
            this.remove(dataPanel);
        }

        dataPanel = new JPanel();
        dataPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        GridLayout layout = new GridLayout(1, DATA_AMOUNT);
        layout.setHgap(1);
        dataPanel.setLayout(layout);

        Arrays.stream(data)
            .forEach(dataValue -> dataPanel.add(new DataColumn(dataValue)));

        this.add(dataPanel, BorderLayout.CENTER);
        dataPanel.updateUI();
    }

    // UTILITY FUNCTIONS

    private void fillData() {
        for (int i = 0; i < DATA_AMOUNT; i++) {
            DATA[i] = i + 1;
        }
        Collections.shuffle(Arrays.asList(DATA));
    }

    /**
     * This method sorts the data based on which
     * sorting algorithm has been chosen
     * @throws AlgorithmNotFoundException when algorithm isn't defined
     */
    private void sortData() {
        executor = Executors.newSingleThreadExecutor();

        switch (selectedAlgo) {
            case MERGE_SORT -> executor.execute(() -> new MergeSort(this).mergeSort(DATA, reverseSortOrderFlag));
            case QUICK_SORT -> executor.execute(() -> new QuickSort(this).quickSort(DATA, reverseSortOrderFlag));
            case BUBBLE_SORT -> executor.execute(() -> new BubbleSort(this).bubbleSort(DATA, reverseSortOrderFlag));
            default -> throw new AlgorithmNotFoundException();
        }

        executor.shutdown();
        dataAlreadySorted = true;
    }

    private void setNimbusLook() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (
            ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e
        ) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAlgoSpeed() {
        return algoSpeed;
    }

    // LISTENERS
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sort")) {
            if (!dataAlreadySorted) {
                sortData();
                drawData();
                dataAlreadySorted = true;
            }
        } else if (e.getActionCommand().equals("Generate")) {
            if (executor != null) {
                executor.shutdownNow();
            }
            // This loop prevents weird artifacts when generate btn
            // is pressed during sorting process
            for (int i = 0; i < 2; i++) {
                fillData();
                drawData();
            }
            dataAlreadySorted = false;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == sortingAlgorithms) {
            for (Algo algo : Algo.values()) {
                if (algo.getName().equals(e.getItem().toString())) {
                    selectedAlgo = algo;
                }
            }
        } else if (e.getSource() == reverseSortOrderCheck) {
            reverseSortOrderFlag = !reverseSortOrderFlag;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == algoSpeedSpinner) {
            algoSpeed = (int) algoSpeedSpinner.getValue();
        }
    }
}
