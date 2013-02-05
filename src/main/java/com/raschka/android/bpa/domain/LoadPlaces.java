package com.raschka.android.bpa.domain;

import android.app.ProgressDialog;
import android.location.Location;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadPlaces {

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Google Places
    GooglePlaces googlePlaces;

    // Places List
    PlacesList nearPlaces;

    // Button
    Button btnShowOnMap;

    // Progress dialog
    ProgressDialog pDialog;

    // Places Listview
    ListView lv;

    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();

    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name


        /**
         * getting Places JSON
         * */
        public List<Place> doInBackground(Location location) {
            // creating Places class object
            googlePlaces = new GooglePlaces();

            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = "gas_station"; // Listing places only cafes, restaurants

                // Radius in meters - increase this value if you don't find any places
                double radius = 5000; // 1000 meters

                // get nearest places
                return googlePlaces.search(location.getLatitude(),
                        location.getLongitude(), radius, types).results;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}
