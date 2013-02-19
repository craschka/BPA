package com.raschka.android.bpa.parsing;

import com.raschka.android.bpa.domain.Tankstelle;

import java.math.BigDecimal;

public class TankstellenEntry {
    public TankstellenEntry(BigDecimal price, String name, String date, String adresse, Tankstelle tankstelle)
    {
        this.price = price;
        this.name = name;
        this.date = date;
        this.adresse = adresse;
        this.tankstelle = tankstelle;
    }

    private final String date;
    private Tankstelle tankstelle;
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

    public Tankstelle tankstelle() {
        return tankstelle;
    }

    public void setTankstelle(Tankstelle tankstelle) {
        this.tankstelle = tankstelle;
    }
}
