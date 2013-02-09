package com.raschka.android.bpa.domain;

public class BenzinSorte {
    private final String value;

    private BenzinSorte(String value) {
        this.value = value;
    }

    public static BenzinSorte E5(){
        return new BenzinSorte("Normal");
    }

    public static BenzinSorte Diesel(){
        return new BenzinSorte("Diesel");
    }

    public static BenzinSorte E10(){
        return new BenzinSorte("Super");
    }

    public static BenzinSorte from(String sorte) {
        if (sorte.toLowerCase().equals("e10")){
            return E10();
        }
        if (sorte.toLowerCase().equals("e5")){
            return E5();
        }
        if (sorte.toLowerCase().equals("diesel")){
            return Diesel();
        }

        return E10();
    }

    public String name() {
        return value;
    }
}
