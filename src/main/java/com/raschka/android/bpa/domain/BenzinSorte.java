package com.raschka.android.bpa.domain;

public class BenzinSorte {
    private final String value;

    private BenzinSorte(String value) {
        this.value = value;
    }

    public static BenzinSorte E5(){
        return new BenzinSorte("E5");
    }

    public static BenzinSorte Diesel(){
        return new BenzinSorte("Diesel");
    }

    public static BenzinSorte E10(){
        return new BenzinSorte("E10");
    }
}
