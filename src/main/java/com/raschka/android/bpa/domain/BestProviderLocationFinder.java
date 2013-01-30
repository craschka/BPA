package com.raschka.android.bpa.domain;

import android.content.Context;
import android.location.*;
import com.raschka.android.bpa.GeocoderProvider;
import com.raschka.android.bpa.Postleitzahl;

import javax.inject.Inject;
import java.io.IOException;

public class BestProviderLocationFinder implements LocationFinder {
    private final LocationManager locationManager;
    private final Geocoder geocoder;

    @Inject
    public BestProviderLocationFinder(LocationManager locationManager, GeocoderProvider geocoderProvider) {
        this.locationManager = locationManager;
        this.geocoder = geocoderProvider.createGeocoder();
    }

    public Postleitzahl findLastKnownPostleitzahl() {
        String best = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(best);
        Address address = null;
        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new Postleitzahl(Integer.parseInt(address.getPostalCode()));
    }
}
