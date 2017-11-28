package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

public class MapActivity extends AppCompatActivity implements
        MapFragment.OnFragmentInteractionListener,
        ManageServiceFragment.OnFragmentInteractionListener,
        LocationListener {


    private LocationManager locationManager;
    private static final int REQUEST_LOCATION_FINE = 100;
    private static final int REQUEST_LOCATION_COURSE = 101;
    private static final int REQUEST_LOCATION_GPS = 102;
    private static final int REQUEST_LOCATION_HARDWARE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_LOCATION_FINE);
        askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_LOCATION_COURSE);
        askPermission(Manifest.permission.LOCATION_HARDWARE, REQUEST_LOCATION_HARDWARE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, this);

        setContentView(R.layout.activity_map);

    }

    private void askPermission(String accessCoarseLocation, int requestLocationCourse) {
        if (ContextCompat.checkSelfPermission(this, accessCoarseLocation) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{accessCoarseLocation}, requestLocationCourse);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("location", location.getLatitude() + "");
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_COURSE: {
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

            case REQUEST_LOCATION_FINE: {
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
            case REQUEST_LOCATION_HARDWARE: {
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
