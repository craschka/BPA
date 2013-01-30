package com.raschka.android.bpa;

public class Postleitzahl {
    private final int value;

    public Postleitzahl(int value) {
        this.value = value;
    }

    public String plz(){
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return "Postleitzahl{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Postleitzahl that = (Postleitzahl) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
