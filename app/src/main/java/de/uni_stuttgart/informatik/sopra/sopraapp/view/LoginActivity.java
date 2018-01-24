package de.uni_stuttgart.informatik.sopra.sopraapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.controller.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     *
     */
    private static Context context;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Spinner langChooser;
    private View rootView;
    private LoginController loginController = new LoginController(this);

    public static Context getCurrentContext() {
        return context;
    }

    public static Context getInstance() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = getApplicationContext();
        rootView = (View) findViewById(R.id.loginRoot);

        // Set up the login form.
        mUsernameView = findViewById(R.id.username);

        mPasswordView = findViewById(R.id.password);
        langChooser = findViewById(R.id.langChooser);

        langChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        mLoginFormView = findViewById(R.id.login_form);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        UserService.getInstance(this).loadUsers();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * updates the selected Language
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

    public void setLoginData(String name, String password) {
        this.mUsernameView.setText(name);
        this.mPasswordView.setText(password);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        if (loginController.checkLoginData(new User(this.mUsernameView.getText().toString(), this.mPasswordView.getText().toString()))) {
            loginController.login();
        } else {
            mPasswordView.setError(getString(R.string.error_invalid_password));
        }
    }

    public void startAgent(View view) {
        this.mUsernameView.setText(UserService.getInstance(this).getUsers().get(0).getName());
        this.mPasswordView.setText(UserService.getInstance(this).getUsers().get(0).getPassword());

        attemptLogin();

    }

    public void startFarmer(View view) {
        this.mUsernameView.setText(UserService.getInstance(this).getUsers().get(1).getName());
        this.mPasswordView.setText(UserService.getInstance(this).getUsers().get(1).getPassword());
        attemptLogin();
    }

    public void registerUser(View view) {

        Intent newIntent = new Intent(this, RegisterActivity.class);
        startActivityForResult(newIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Snackbar.make(rootView, "User saved successully", Snackbar.LENGTH_SHORT).show();
        switch (resultCode) {
            case 100:
                break;
        }

    }

    public void loginUser(View view) {
        attemptLogin();
    }

}
