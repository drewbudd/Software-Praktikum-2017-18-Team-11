package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.network.ConnectivityReceiver;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.IDataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.BlankFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.ContractsFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.DamagesFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.FieldsFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.ManageServiceFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.SearchFragment;

public class MapActivity extends AppCompatActivity implements
        MapFragment.OnFragmentInteractionListener,
        ManageServiceFragment.OnFragmentInteractionListener,
        ContractsFragment.OnFragmentInteractionListener,
        DamagesFragment.OnFragmentInteractionListener,
        FieldsFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,
        BlankFragment.OnFragmentInteractionListener,
        ConnectivityReceiver.ConnectivityReceiverListener {


    private static final int REQUEST_LOCATION_FINE = 100;
    private static final int REQUEST_LOCATION_COURSE = 101;
    private static final int REQUEST_LOCATION_HARDWARE = 103;
    public static IDataService dataService = null;
    private static MapActivity context;
    private MapFragment mapFragment;
    private ManageServiceFragment manageServiceFragment;
    private boolean useReceiver = false;
    private ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();

    public static Context getInstance() {
        return context;
    }

    /**
     * sets the listener for networking listening
     *
     * @param listener
     */
    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataService = DataService.getInstance(this);
        dataService.loadFields();
        dataService.loadDamages();

        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        setConnectivityListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_map);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        manageServiceFragment = (ManageServiceFragment) getFragmentManager().findFragmentById(R.id.manageServiceFragment);
        manageServiceFragment.getFieldFragment().updateAdapter();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    /**
     * saves a new Field
     * used from the button in the dialog
     *
     * @param view
     */
    public void saveNewField(View view) {
        mapFragment.saveField();
        manageServiceFragment.getFieldFragment().updateAdapter();
    }

    /**
     * saves a new damage
     * uses from the button in the dialog
     *
     * @param view
     */
    public void saveNewDamage(View view) {
        mapFragment.saveDamage();
        manageServiceFragment.getSearchFragment().updateAdapter();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        mapFragment.updateNetworkStatus(isConnected);
    }

    public void openInfos(View view) {

    }

    public MapFragment mapFragment() {
        return mapFragment;
    }
}