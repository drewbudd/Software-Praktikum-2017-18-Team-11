package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.maps.MapView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.controller.MainController;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

public class MainActivity extends Activity implements MappedFragment.OnFragmentInteractionListener, LocationListener {

    MainController mainController;

    MarkerOptions myPosition = new MarkerOptions();
    private MapView mapView;

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        mainController = new MainController(this);

        User u = Setup.dataService.getCurrentLoggedInUser();


        // TODO: change accessToken
        Mapbox.getInstance(this, "pk.eyJ1IjoiemluZGxzbiIsImEiOiJjajlzbmY4aDg0NXF6MzNwZzBmNTBuN3MzIn0.jTKwbr6lki_d531dDpGI_w");
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
