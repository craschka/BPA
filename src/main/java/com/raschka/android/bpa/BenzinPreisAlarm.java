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
import com.raschka.android.bpa.domain.*;
import com.raschka.android.bpa.parsing.Downloader;
import com.raschka.android.bpa.parsing.HtmlParser;
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

    @InjectView(R.id.start)
    private Button start;

    @InjectView(R.id.startmoers)
    private Button startmoers;

    @InjectView(R.id.startD)
    private Button startD;

    @Inject
    private DefaultBenzinSortenChooser defaultBenzinSortenChooser;

    private boolean first = true;

    @Inject
    private Downloader downloader;

    @Inject
    private HtmlParser htmlParser;


    @Inject
    private PreisService preisService;

    @Inject
    private TankstellenFinder tankstellenFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);

        deleteSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteAllSettings();
                recreate();
            }
        });

        defaultBenzinSortenChooser.chooseBenzinsorte();

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                start();
            }
        });
        startmoers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startMoers();
            }
        });
        startD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startD();
            }
        });

        } catch (Exception e){
            log(e.getLocalizedMessage());
        }


    }

    private void start(){
        Location location = locationFinder.findLastKnownPosition();
        if (location == null){
            location = emulateLocationAs40237();
        }
        printPreise(location);
    }

    private void startD(){
        printPreise(emulateLocationAs40237());
    }

    private void startMoers(){
        printPreise(emulateLocationAsMoers());
    }

    private void printPreise(Location location) {
        List<Tankstelle> tankstellen = tankstellenFinder.findeTankstellen(location);
        List<Preis> preise = preisService.readPreise(tankstellen,defaultBenzinSortenChooser.getChoosenBenzinSorte());


        for (Preis preis : preise) {
            log("\n"+preis.tankstelle().name()+"\n"+preis.tankstelle().adresse()+"\n");
            log("Aktueller Preis: "+ ((double)preis.value()/1000) + " Euro\n");
            log("Datum: " + preis.datum() + "\n");
        }
    }

    private Location emulateLocationAs40237() {
        Location location = new Location("network");
        location.setLatitude(51.23473);
        location.setLongitude(6.804142);
        return location;
    }

    private Location emulateLocationAsMoers() {
        Location location = new Location("network");
        location.setLatitude(51.464756);
        location.setLongitude(6.61377);
        return location;
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