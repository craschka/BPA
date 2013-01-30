package com.raschka.android.bpa;

import android.content.Context;
import android.location.Geocoder;
import com.google.inject.Inject;

public class GeocoderProvider {
    private final Context context;

    @Inject
    public GeocoderProvider(Context context) {
        this.context = context;
    }

    public Geocoder createGeocoder() {
        return new Geocoder(context);
    }
}
