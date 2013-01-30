package com.raschka.android.bpa;

import android.app.Activity;
import android.location.*;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class GeoTest extends Activity implements LocationListener {
    private Geocoder geocoder;
    private String best;
    private LocationManager mgr;

    private static final String[] A = {"invalid", "n/a", "fine", "coarse"};
    private static final String[] P = {"invalid", "n/a", "low", "medium", "high"};
    private static final String[] S = {"out of service", "temporary not available", "available"};
    private TextView output;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getApplicationContext());

        log("Location providers");
        dumpProviders();

        Criteria criteria = new Criteria();
        best = mgr.getBestProvider(criteria, true);
        log("\nBest Provider is: " + best);

        log("\nLocations (starting with last known):");
        Location location = mgr.getLastKnownLocation(best);
        dumpLocation(location);
    }

    private void dumpLocation(Location location) {
        if (location == null) {
            log("\nLocation[unknown]");
        }else {
            log("\n" + location.toString());

            try {
                log(geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgr.requestLocationUpdates(best,15000,1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.removeUpdates(this);
    }

    private void dumpProviders() {
        List<String> providers = mgr.getAllProviders();
        for (String provider : providers) {
            dumpProvider(provider);
        }
    }

    private void dumpProvider(String provider) {
        LocationProvider info = mgr.getProvider(provider);
        StringBuilder builder = new StringBuilder();
        builder.append("LocationProvider[")
                .append("name=").append(info.getName())
                .append(", enabled=").append(mgr.isProviderEnabled(provider))
                .append(", accuracy=").append(A[info.getAccuracy() + 1])
                .append(", powerRequirement=").append(P[info.getPowerRequirement() + 1])
                .append(", hasMonetaryCost=").append(info.hasMonetaryCost())
                .append(", requiresCell=").append(info.requiresCell())
                .append(", requiresNetwork=").append(info.requiresNetwork())
                .append(", requiresSatellite=").append(info.requiresSatellite())
                .append(", supportsAltitude=").append(info.supportsAltitude())
                .append(", supportsBearing=").append(info.supportsBearing())
                .append(", supportsSpeed=").append(info.supportsSpeed())
                .append("]");
        log(builder.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        dumpLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        log("\nProvider status changed: "+ provider + ", status=" + S[status] + ", extras=" + extras);
    }

    @Override
    public void onProviderEnabled(String provider) {
        log("\nProvider enabled: "+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        log("\nProvider disabled: "+provider);
    }

    private void log(String text) {
        output.append(text + "\n");
    }
}
