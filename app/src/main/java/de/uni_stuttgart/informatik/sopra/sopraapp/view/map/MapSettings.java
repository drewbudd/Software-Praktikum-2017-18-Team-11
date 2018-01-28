package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;
import java.util.Locale;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

public class MapSettings extends AppCompatActivity {
    private String[] entries;
    ListView l;
    private Spinner changeLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    /**
     * updates the current language to the selected language
     *
     * @param languageToLoad
     */
    private void setLanguage(String languageToLoad) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {


        /*
        Locale locale;
        if(languageToLoad.equals("not-set")){ //use any value for default
            locale = Locale.getDefault();
        }
        else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
                */
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", languageToLoad).commit();
                Configuration config = getBaseContext().getResources().getConfiguration();
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });


    }
}
