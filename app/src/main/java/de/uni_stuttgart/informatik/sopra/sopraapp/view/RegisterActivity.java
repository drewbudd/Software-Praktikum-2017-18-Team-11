package de.uni_stuttgart.informatik.sopra.sopraapp.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveUser(view);
            }
        });
    }

    public void saveUser(View view) {
        EditText username = findViewById(R.id.Registerusername);
        EditText password = findViewById(R.id.password);
        Spinner userRole = findViewById(R.id.userRole);

        User user = new User(username.getText().toString(),password.getText().toString());
        switch(userRole.getSelectedItemPosition()){
            case 0:
                user.setUserRole(UserRole.GUTACHTER);
                break;
            case 1:
                user.setUserRole(UserRole.LANDWIRT);
                break;
        }


        UserService.getInstance(LoginActivity.getCurrentContext()).saveUser(user);
        this.finish();
    }
}
