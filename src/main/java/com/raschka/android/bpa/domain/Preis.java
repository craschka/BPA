package com.raschka.android.bpa.domain;

public class Preis {
    private final int value;
    private final long timestamp;

    public Preis(int value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }
}
