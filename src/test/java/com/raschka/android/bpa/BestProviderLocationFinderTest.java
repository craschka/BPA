package com.raschka.android.bpa;

import android.content.Context;
import android.location.LocationManager;
import junit.framework.TestCase;

public class BestProviderLocationFinderTest extends TestCase {
    private LocationManager locationManager;
    private Context context;
    private GeocoderProvider geocoderProvider;

    public BestProviderLocationFinderTest() {
    }

    @Override
    public void setUp() throws Exception {
        //sut = new BestProviderLocationFinder(locationManager,geocoderProvider);
    }
}
