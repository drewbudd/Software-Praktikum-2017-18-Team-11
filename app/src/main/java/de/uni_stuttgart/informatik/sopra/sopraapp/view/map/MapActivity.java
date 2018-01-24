package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

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
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;
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
        View.OnClickListener,
        LocationListener {


    private static final int REQUEST_LOCATION_FINE = 100;
    private static final int REQUEST_LOCATION_COURSE = 101;
    private static final int REQUEST_LOCATION_GPS = 102;
    private static final int REQUEST_LOCATION_HARDWARE = 103;
    private LocationManager locationManager;
    private MapFragment mapFragment;
    private ManageServiceFragment manageServiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        manageServiceFragment = (ManageServiceFragment) getFragmentManager().findFragmentById(R.id.manageServiceFragment);

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

    /**
     * saves a new Field after saveButton pressed in the
     * newFieldDialog
     *
     * @param view
     */
    public void saveNewField(View view) {
        EditText fieldname = mapFragment.getAddFieldDialogFragment().getDialog().findViewById(R.id.text_field_name);
        Spinner contractType = mapFragment.getAddFieldDialogFragment().getDialog().findViewById(R.id.contract_spinner);
        EditText fieldType = mapFragment.getAddFieldDialogFragment().getDialog().findViewById(R.id.text_field_type);

        mapFragment.getCreatingNewField().setMarkerPosition(mapFragment.getCurrentMarkerFieldPositions());
        mapFragment.getCreatingNewField().setFieldType(fieldType.getText().toString());
        mapFragment.getCreatingNewField().setOwner(App.dataService.getCurrentLoggedInUser());
        App.dataService.saveField(mapFragment.getCreatingNewField());

        MapFragment.setCurrentMapEditingStatus(MapEditingStatus.DEFAULT);
        mapFragment.drawField(mapFragment.getCurrentMarkerFieldPositions());
        mapFragment.getAddFieldDialogFragment().dismiss();
        mapFragment.clearField();

    }

    @Override
    public void onClick(View v) {
        Log.v("onClick", v.getId() + "");
    }

    public void saveNewDamage(View view) {
        EditText damageType = mapFragment.getAddDamageDialogFragment().getDialog().findViewById(R.id.text_damage_typeText);
        Damage newDamage = new Damage(mapFragment.getFieldFromDamage());
        newDamage.setDamageType(damageType.getText().toString());
        newDamage.setMarkerPosition(mapFragment.getCurrentMarkerFieldPositions());
        newDamage.setOwner(mapFragment.getFieldFromDamage().getOwner());
        mapFragment.getFieldFromDamage().addDamage(newDamage);
        App.dataService.updateField(mapFragment.getFoundFieldID(), mapFragment.getFieldFromDamage());
        MapFragment.setCurrentMapEditingStatus(MapEditingStatus.DEFAULT);
        mapFragment.getAddDamageDialogFragment().dismiss();
        mapFragment.drawDamage(mapFragment.getCurrentDamageMarkerPosition());
//        manageServiceFragment.getSearchFragment().getSearchAdapter().notifyDataSetChanged();
        mapFragment.clearDamage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        App.dataStorageService.saveAllFields();
    }
}