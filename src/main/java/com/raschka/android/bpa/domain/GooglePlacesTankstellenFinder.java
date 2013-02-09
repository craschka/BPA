package com.raschka.android.bpa.domain;

import android.location.Location;
import android.util.Log;
import com.google.api.client.http.*;
import com.raschka.android.bpa.parsing.RequestFactoryProvider;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GooglePlacesTankstellenFinder implements TankstellenFinder {

    private static final String GOOGLE_API_KEY = "AIzaSyCu0kKct9SVLTNf7cgBvvkoNZUI0m7o1ss";
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";

    @Inject
    private RequestFactoryProvider requestFactoryProvider;

    public List<Tankstelle> findeTankstellen(Location location) {
        try {
            String types = "gas_station";

            double radius = 2500;

            return search(location.getLatitude(),
                    location.getLongitude(), radius, types).tankstellen();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Tankstelle>(0);
    }

    public PlacesList search(double latitude, double longitude, double radiusInMeter, String types)
            throws Exception {

        try {

            HttpRequestFactory httpRequestFactory = requestFactoryProvider.createRequestFactory();
            HttpRequest request = httpRequestFactory
                    .buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            request.getUrl().put("key", GOOGLE_API_KEY);
            request.getUrl().put("location", latitude + "," + longitude);
            request.getUrl().put("radius", radiusInMeter);
            request.getUrl().put("sensor", "false");
            if(types != null)
                request.getUrl().put("types", types);

            return request.execute().parseAs(PlacesList.class);

        } catch (HttpResponseException e) {
            Log.e("Error:", e.getMessage());
            return new PlacesList();
        }
    }
}
