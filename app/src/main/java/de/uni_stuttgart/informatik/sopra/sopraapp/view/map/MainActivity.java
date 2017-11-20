package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.maps.MapFragment;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.controller.MainController;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

public class MainActivity extends Activity implements MainMenuFragment.OnFragmentInteractionListener, MappedFragment.OnFragmentInteractionListener {

    MainController mainController;
    private Button gutachterButton;
    private Button landwirtButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mainController = new MainController(this);

        User u = Setup.dataService.getCurrentLoggedInUser();

        /* Unterscheidung GUtachter <-> Landwirt
        switch (Setup.dataService.getCurrentLoggedInUser().getCurrentUserRole()){

            case LANDWIRT:
                landwirtButton.setVisibility(View.VISIBLE);

                break;
            case GUTACHTER:
                gutachterButton.setVisibility(View.VISIBLE);
                break;
        } */


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MainController.MY_PERMISSIONS_REQUEST_FINE_LOCALTION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
