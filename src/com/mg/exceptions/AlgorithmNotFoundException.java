package com.mg.exceptions;

public class AlgorithmNotFoundException extends RuntimeException {
    public AlgorithmNotFoundException() {
        super("Algorithm doesn't exist!");
    }
}
