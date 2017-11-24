package de.uni_stuttgart.informatik.sopra.sopraapp.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MainActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/18
 */

public class MainController {

    public static final int MY_PERMISSIONS_REQUEST_COARSE_LOCALTION = 101;
    MainActivity mainActivity;
    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCALTION = 100;

    public MainController(MainActivity activity) {
        this.mainActivity = activity;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERMISSIONS_REQUEST_FINE_LOCALTION);

        }
    }


    /**
     * check permission for Location
     */
    public void checkPermission(String permission, int reqeustCode) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mainActivity,
                String.valueOf(permission))
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                    String.valueOf(permission))) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(mainActivity,
                        new String[]{String.valueOf(permission)},
                        reqeustCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    public boolean hasPermission() {

        return true;
    }
}
