package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

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
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;

public class MapSettings extends AppCompatActivity {
    private Spinner changeLang;
    private Button logout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // spinner to change the current language
        changeLang = findViewById(R.id.changeLang);
        changeLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // depending on the selection the setLanguage method changes the current language
                switch (position) {
                    case 1:
                        setLanguage("de");
                        break;
                    case 2:
                        setLanguage("en");
                        /*
                        Configuration newConfig1 = new Configuration();
                        newConfig1.locale = Locale.ENGLISH;
                        onConfigurationChanged(newConfig1);*/
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // button to log out the user
        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the current user to null
                UserService.getInstance(getApplicationContext()).setCurrentUser(null);
                // go back to login
                Intent loggingOut = new Intent(getApplicationContext(), LoginActivity.class);
                // if you press the back button on the phone you won't be in the previous activity again
                loggingOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loggingOut);
            }
        });

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
