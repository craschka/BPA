package com.raschka.android.bpa.domain;

public class TankstellenId {
    private final long value;

    public TankstellenId(long value) {
        this.value = value;
    }

    public long id(){
        return value;
    }
}
