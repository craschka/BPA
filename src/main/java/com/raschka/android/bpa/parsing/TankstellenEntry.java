package com.raschka.android.bpa.parsing;

import java.math.BigDecimal;

public class TankstellenEntry {
    public TankstellenEntry(BigDecimal price, String name, String date, String adresse)
    {
        this.price = price;
        this.name = name;
        this.date = date;
        this.adresse = adresse;
    }

    private final String date;
    private final String name;
    private final BigDecimal price;
    private final String adresse;

    public String name()
    {
        return name;
    }

    public String date()
    {
        return date;
    }

    public String price()
    {
        return price.toString();
    }

    public String adresse(){
        return adresse;
    }
}
