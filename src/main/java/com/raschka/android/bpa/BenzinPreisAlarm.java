package com.raschka.android.bpa;

import android.os.Bundle;
import android.widget.TextView;
import com.example.R;
import com.raschka.android.bpa.domain.LocationFinder;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import javax.inject.Inject;

@ContentView(value = R.layout.main)
public class BenzinPreisAlarm extends RoboActivity {

    @InjectView(R.id.output)
    private TextView output;

    @Inject
    private LocationFinder locationFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Postleitzahl plz = locationFinder.findLastKnownPostleitzahl();
        log("plz: "+plz);
    }

    private void log(String text){
        output.append(text);
    }
}