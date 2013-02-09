package com.raschka.android.bpa;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.R;
import com.raschka.android.bpa.domain.BenzinSorte;
import roboguice.inject.InjectView;
import roboguice.util.Strings;

import javax.inject.Inject;

public class DefaultBenzinSortenChooser implements AdapterView.OnItemSelectedListener{

    @InjectView(R.id.benzin)
    private Spinner spinner;

    @Inject
    private Context context;

    @InjectView(R.id.output)
    private TextView output;
    private boolean first = true;


    public void chooseBenzinsorte() {
        if (!hasDefaultBenzinsorte(context)){
            log("Sie haben noch keine Benzinsorte eingestellt");
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.benzinsorten, android.R.layout.simple_spinner_item);
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setVisibility(View.VISIBLE);
        } else {
            log(defaultSorte());
        }
    }

    public BenzinSorte getChoosenBenzinSorte(){
        return BenzinSorte.from(defaultSorte());
    }

    public String defaultSorte() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs_file", Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.benzinsorte),null);
    }

    private boolean hasDefaultBenzinsorte(Context context) {
        return !Strings.isEmpty(defaultSorte());
    }

    private void log(String text){
        output.append(text);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (first){
            first = false;
            return;
        }
        String sorte = (String) parent.getItemAtPosition(pos);
        saveDefaultSorte(sorte);

        //contentView.refreshDrawableState();
    }

    private void saveDefaultSorte(String sorte) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.benzinsorte),sorte);
        editor.commit();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        log("nothing");
    }
}
