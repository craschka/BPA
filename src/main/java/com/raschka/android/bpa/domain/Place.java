package com.raschka.android.bpa.domain;

import com.google.api.client.util.Key;

import java.io.Serializable;

public class Place implements Serializable, Tankstelle{
    @Key
    public String id;

    @Key
    public String name;

    @Key
    public String reference;

    @Key
    public String icon;

    @Key
    public String vicinity;

    @Key
    public Geometry geometry;

    @Key
    public String formatted_address;

    @Key
    public String formatted_phone_number;

    @Override
    public String toString() {
        return name + " - " + id + " - " + reference;
    }

    public String name() {
        return name;
    }

    public String adresse() {
        return vicinity;
    }

    public Location location() {
        return geometry.location;
    }

    public Tankstelle tankstelle() {
        return this;
    }

    public static class Geometry implements Serializable
    {
        @Key
        public Location location;
    }

    public static class Location implements Serializable
    {
        @Key
        public double lat;

        @Key
        public double lng;
    }
}
