package com.raschka.android.bpa.domain;

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
        Location location = findLastKnownPosition();
        Address address = null;
        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Postleitzahl(Integer.parseInt(address.getPostalCode()));
    }

    public Location findLastKnownPosition() {
        String best = locationManager.getBestProvider(new Criteria(), true);
        return locationManager.getLastKnownLocation(best);
    }
}
