package com.raschka.android.bpa.domain;

public class Preis {
    private final int value;
    private final long timestamp;
    private final Tankstelle tankstelle;
    private final String datum;

    public Preis(int value, long timestamp, Tankstelle tankstelle, String datum) {
        this.value = value;
        this.timestamp = timestamp;
        this.tankstelle = tankstelle;
        this.datum = datum;
    }

    public Tankstelle tankstelle(){
        return tankstelle;
    }

    public int value(){
        return value;
    }

    public String datum() {
        return datum;
    }
}
