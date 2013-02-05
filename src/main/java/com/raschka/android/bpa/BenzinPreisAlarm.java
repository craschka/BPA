package com.raschka.android.bpa;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.R;
import com.raschka.android.bpa.domain.LoadPlaces;
import com.raschka.android.bpa.domain.LocationFinder;
import com.raschka.android.bpa.domain.Place;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import javax.inject.Inject;
import java.util.List;

@ContentView(value = R.layout.main)
public class BenzinPreisAlarm extends RoboActivity  {

    @InjectView(R.id.output)
    private TextView output;

    @Inject
    private LocationFinder locationFinder;

    @Inject
    private Context context;

    @InjectView(R.id.benzin)
    private Spinner spinner;

    @InjectView(R.id.deleteSettings)
    private Button deleteSettings;

    @Inject
    private DefaultBenzinSortenChooser defaultBenzinSortenChooser;

    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deleteSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteAllSettings();
                recreate();
            }
        });

        defaultBenzinSortenChooser.chooseBenzinsorte();

        LoadPlaces loadPlaces = new LoadPlaces();
        Location location = locationFinder.findLastKnownPosition();
        List<Place> tankstellen = loadPlaces.doInBackground(location);

        for (Place place : tankstellen) {
            log("\n"+place.name+"\n"+place.vicinity+"\n");
        }

    }

    private void deleteAllSettings() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editore = sharedPreferences.edit();
        editore.clear().commit();
    }

    private void log(String text){
        output.append(text);
    }
}