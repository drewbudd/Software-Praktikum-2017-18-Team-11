package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.network.ConnectivityReceiver;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapInitialization;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.dialogs.AddDamageDialog;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.dialogs.AddFieldDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        MapView.OnMapChangedListener,
        MapboxMap.OnMapClickListener,
        LocationListener,
        ConnectivityReceiver.ConnectivityReceiverListener {
    private static final int REQUEST_LOCATION_COURSE = 42;
    private static final int REQUEST_LOCATION_FINE = 7;
    public static MapEditingStatus currentMapEditingStatus = MapEditingStatus.DEFAULT;
    private static MapboxMap mapboxMapGlobal;
    FloatingActionButton menuFAB;
    LinearLayout fieldsFAB;
    LinearLayout damagesFAB;
    LinearLayout settingsFAB;
    AddFieldDialog addFieldDialogFragment;
    AddDamageDialog addDamageDialogFragment;
    MapObject newMapObject;
    Field fieldFromDamage = null;
    private OnFragmentInteractionListener mListener;
    private MapView mapView;
    private View rootView;
    private List<LatLng> currentBorderPoints = new ArrayList<>();
    private LocationManager locationManager;
    private boolean isFABOpen;
    private MapFragment mapFragment;
    private NewAreaMode currentMODE = NewAreaMode.GPS;
    private double gpsLat;
    private double gpsLng;
    private MarkerOptions lastGPSLocation;
    private List<Marker> displayingMarkerOptions = new ArrayList<Marker>();
    private int foundFieldID = -1;
    private TextView statusText;


    public MapFragment() {
        mapFragment = this;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(java.lang.String param1, java.lang.String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @return
     */
    public static MapEditingStatus getCurrentMapEditingStatus() {
        return currentMapEditingStatus;
    }

    /**
     * @param currentMapEditingStatus
     */
    public static void setCurrentMapEditingStatus(MapEditingStatus currentMapEditingStatus) {
        MapFragment.currentMapEditingStatus = currentMapEditingStatus;
    }

    /**
     * checks if network is available
     *
     * @param context
     * @param networkTypes
     * @return
     */
    public static boolean isNetworkAvailable(Context context, int[] networkTypes) {
        /*
        try {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo netInfo = cm.getNetworkInfo(networkType);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
        */

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_LOCATION_FINE);
        askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_LOCATION_COURSE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mapFragment);


    }

    private void askPermission(java.lang.String accessCoarseLocation, int requestLocationCourse) {
        if (ContextCompat.checkSelfPermission(getActivity(), accessCoarseLocation) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new java.lang.String[]{accessCoarseLocation}, requestLocationCourse);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        final FloatingActionButton fabGPS = rootView.findViewById(R.id.fab_gps);
        final TextView fabGPSLabel = rootView.findViewById(R.id.gps_button_label);
        menuFAB = rootView.findViewById(R.id.fab);
        fieldsFAB = rootView.findViewById(R.id.fab1_and_label);
        damagesFAB = rootView.findViewById(R.id.fab2_and_label);
        settingsFAB = rootView.findViewById(R.id.fab3_and_label);
        statusText = rootView.findViewById(R.id.networkStatus);
        isFABOpen = false;

        fabGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Math.round(gpsLat) != 0 || Math.round(gpsLng) != 0) {
                    switch (currentMapEditingStatus) {
                        case START_CREATE_FIELD_COORDINATES:
                            addFieldPoint(new LatLng(gpsLat, gpsLng));
                            break;
                        case START_CREATE_DAMAGE_COORDINATES:
                            addDamagePoint(new LatLng(gpsLat, gpsLng));
                            break;
                    }
                } else {
                    Snackbar.make(rootView, "No GPS connection", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        menuFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        rootView.findViewById(R.id.field_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentMapEditingStatus) {
                    case DEFAULT:
                        createField();
                        break;
                    case START_CREATE_FIELD_COORDINATES:
                        FragmentManager fm = getActivity().getFragmentManager();
                        addFieldDialogFragment = AddFieldDialog.newInstance("Add Field");
                        addFieldDialogFragment.show(fm, "dialog_fragment_add_field");
                        break;
                }
            }
        });

        rootView.findViewById(R.id.damages_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView label = rootView.findViewById(R.id.damages_button_label);
                switch (currentMapEditingStatus) {
                    case DEFAULT:
                        currentMapEditingStatus = MapEditingStatus.START_CREATE_DAMAGE_COORDINATES;
                        Snackbar.make(getView(), "Create a damage within a field", Snackbar.LENGTH_SHORT).show();
                        newMapObject = new Damage();
                        newMapObject.setContext(mapboxMapGlobal, mapView);
                        disableFieldsFAB();
                        disableSettingsFAB();
                        disableMenuFAB();
                        showAndEnableGPSButton(fabGPS, fabGPSLabel);
                        label.setText("Finish adding points");


                        break;
                    case START_CREATE_DAMAGE_COORDINATES:
                        currentMapEditingStatus = MapEditingStatus.END_CREATE_DAMAGE_COORDINATES;
                        FragmentManager fm = getActivity().getFragmentManager();
                        addDamageDialogFragment = AddDamageDialog.newInstance("Add Damage");
                        addDamageDialogFragment.show(fm, "dialog_fragment_add_damage");

                        break;
                     /*
                        } else {
                            fieldsFAB.animate().alpha(1.0f).setDuration(100);
                            fieldsFAB.setEnabled(true);
                            //settingsFAB.animate().alpha(1.0f).setDuration(100);
                            settingsFAB.setEnabled(true);
                            label.setText("Report Damage");
                            enableMenuFAB();
                            hideAndDisableGPSButton(fabGPS, fabGPSLabel);
                            creatingNewField.setMarkerPosition(currentMarkerFieldPositions);
                            FragmentManager fm = getActivity().getFragmentManager();
                            addDamageDialogFragment = AddDamageDialog.newInstance("Add Damage");
                            addDamageDialogFragment.show(fm, "dialog_fragment_add_damage");
                        } */
                }


            }
        });

        settingsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(rootView, "Placeholder button, not yet implemented", Snackbar.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void saveField() {
        newMapObject.draw();
        MapActivity.dataService.addField((Field) newMapObject);
        MapActivity.dataService.saveFields();
        currentMapEditingStatus = MapEditingStatus.DEFAULT;
        damagesFAB.animate().alpha(1.0f).setDuration(100);
        damagesFAB.setEnabled(true);
        //settingsFAB.animate().alpha(1.0f).setDuration(100);
        settingsFAB.setEnabled(true);
        menuFAB.animate().alpha(1.0f).setDuration(100);
        menuFAB.setEnabled(true);
        TextView label = rootView.findViewById(R.id.field_button_label);
        label.setText("Add field");
        getAddFieldDialog().dismiss();
    }

    private void createField() {
        currentMapEditingStatus = MapEditingStatus.START_CREATE_FIELD_COORDINATES;
        newMapObject = new Field();
        newMapObject.setContext(mapboxMapGlobal, mapView);
        damagesFAB.animate().alpha(0.3f).setDuration(100);
        damagesFAB.setEnabled(false);
        settingsFAB.setEnabled(false);
        menuFAB.animate().alpha(0.3f).setDuration(100);
        menuFAB.setEnabled(false);
        TextView label = rootView.findViewById(R.id.field_button_label);
        label.setText("Finish adding points");
        Snackbar.make(getView(), "Add Marker", Snackbar.LENGTH_SHORT).show();
    }

    private void disableDamagesFAB() {
        damagesFAB.animate().alpha(0.3f).setDuration(100);
        damagesFAB.setEnabled(false);
    }

    private void enableDamagesFAB() {
        damagesFAB.animate().alpha(1.0f).setDuration(100);
        damagesFAB.setEnabled(true);
    }

    private void disableFieldsFAB() {
        fieldsFAB.animate().alpha(0.3f).setDuration(100);
        fieldsFAB.setEnabled(false);
    }

    private void disableSettingsFAB() {
        settingsFAB.animate().alpha(0.3f).setDuration(100);
        settingsFAB.setEnabled(false);
    }

    private void disableMenuFAB() {
        menuFAB.animate().alpha(0.3f).setDuration(100);
        menuFAB.setEnabled(false);
    }

    private void enableSettingsFAB() {
        settingsFAB.animate().alpha(1.0f).setDuration(100);
        settingsFAB.setEnabled(true);
    }

    private void enableFieldsFAB() {
        fieldsFAB.animate().alpha(1.0f).setDuration(100);
        fieldsFAB.setEnabled(true);
    }

    private void enableMenuFAB() {
        menuFAB.animate().alpha(1.0f).setDuration(100);
        menuFAB.setEnabled(true);
    }

    private void hideAndDisableGPSButton(FloatingActionButton fabGPS, TextView fabGPSLabel) {
        fabGPS.animate().alpha(0.0f).setDuration(100);
        fabGPS.setEnabled(false);
        fabGPS.setVisibility(View.GONE);
        fabGPSLabel.animate().alpha(0.0f).setDuration(100);
    }

    private void showAndEnableGPSButton(FloatingActionButton fabGPS, TextView fabGPSLabel) {
        fabGPS.animate().alpha(1.0f).setDuration(100);
        fabGPS.setEnabled(true);
        fabGPSLabel.animate().alpha(1.0f).setDuration(100);
    }

    private void showFABMenu() {
        isFABOpen = true;
        fieldsFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_55)).setDuration(200);
        damagesFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_105)).setDuration(400);
        settingsFAB.animate().translationY(-getResources().getDimension(R.dimen.standard_155)).setDuration(600);
        rootView.findViewById(R.id.field_button_label).animate().alpha(1.0f).setDuration(300);
        rootView.findViewById(R.id.damages_button_label).animate().alpha(1.0f).setDuration(600);
        rootView.findViewById(R.id.fab3_label).animate().alpha(0.3f).setDuration(900);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fieldsFAB.animate().translationY(0);
        damagesFAB.animate().translationY(0);
        settingsFAB.animate().translationY(0);
        rootView.findViewById(R.id.fab1_and_label).animate().alpha(0.0f).setDuration(200);
        rootView.findViewById(R.id.fab2_and_label).animate().alpha(0.0f).setDuration(200);
        rootView.findViewById(R.id.fab3_and_label).animate().alpha(0.0f).setDuration(200);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        mapboxMapGlobal = mapboxMap;
        MapInitialization initialization = new
                MapInitialization();
        initialization.loadFields(mapFragment);
        MapService.getInstance().setMap(this);
        MapService.getInstance().drawAllFields();
        mapboxMap.setOnMapClickListener(this);
        mapboxMap.setOnMapClickListener(this);
        mapboxMap.setOnMapClickListener(this);

         /* Campus coordinates*/
        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(48.74641, 9.10623))
                .zoom(15).build());
    }

    private void uploadMapForOfflineMode() {

    }

    @Override
    public void onMapChanged(int change) {


    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        switch (currentMapEditingStatus) {

            case START_CREATE_FIELD_COORDINATES:
                Marker marker = new Marker(new MarkerOptions());
                marker.setPosition(point);
                if (newMapObject.addMarker(marker.getPosition())) {
                    newMapObject.drawMarker(point);
                    this.displayingMarkerOptions.add(marker);
                } else {
                    Snackbar.make(getView(), "Marker outside of a field", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case END_CREATE_FIELD_COORDINATES:
                break;
            case CREATE_FIELD_DONE:
                break;
            case START_CREATE_DAMAGE_COORDINATES:
                if (fieldFromDamage == null) {
                    if (MapService.findCurrentField(point) != null) {
                        fieldFromDamage = MapService.findCurrentField(point);
                        newMapObject.setField(fieldFromDamage);
                    }
                }
                if (fieldFromDamage == null) {
                    Snackbar.make(getView(), "Marker inside of a field", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (fieldFromDamage.contains(point)) {
                        newMapObject.addMarker(point);
                        newMapObject.drawMarker(point);
                    }
                }

                break;
            case CREATED_DAMAGE_DONE:
                break;
            case CREATED_FIELD_DONE:
                break;
            case CREATE_DAMAGE:
                break;
            case MODIFY_FIELD:
                break;
            case END_CREATE_DAMAGE_COORDINATES:
                break;
            case DEFAULT:
                break;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (this.currentMODE == NewAreaMode.GPS) {
            this.currentBorderPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }
        mapboxMapGlobal.setCameraPosition(new CameraPosition.Builder().target(new LatLng(gpsLat, gpsLng)).build());

        gpsLat = location.getLatitude();
        gpsLng = location.getLongitude();

        IconFactory iconFactory = IconFactory.getInstance(rootView.getContext());
        Icon icon = iconFactory.fromResource(R.drawable.mapbox_mylocation_icon_default);

        switch (currentMapEditingStatus) {
            case START_CREATE_FIELD_COORDINATES:
            case START_CREATE_DAMAGE_COORDINATES:
                if (lastGPSLocation != null) {
                    mapboxMapGlobal.removeMarker(lastGPSLocation.getMarker());
                }
                lastGPSLocation = new MarkerOptions().position(new LatLng(gpsLat, gpsLng)).icon(icon);
                mapboxMapGlobal.addMarker(lastGPSLocation);
                mapboxMapGlobal.setCameraPosition(new CameraPosition.Builder().target(new LatLng(gpsLat, gpsLng)).build());
                break;
        }

    }

    @Override
    public void onStatusChanged(java.lang.String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(java.lang.String provider) {

    }

    @Override
    public void onProviderDisabled(java.lang.String provider) {

    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onStart();

        // TODO: change accessToken
        Mapbox.getInstance(getContext(), "pk.eyJ1IjoiemluZGxzbiIsImEiOiJjajlzbmY4aDg0NXF6MzNwZzBmNTBuN3MzIn0.jTKwbr6lki_d531dDpGI_w");
        mapView = getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.addOnMapChangedListener(this);

        mapView.getMapAsync(this);


        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        if (isNetworkAvailable(this.getContext(), networkTypes)) {
            statusText.setText(R.string.onlineStatusText);
        } else {
            statusText.setText(R.string.offlineStatusText);
        }

    }

    public MapboxMap getMapBox() {
        return mapboxMapGlobal;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void saveDamage(Damage damage) {
        newMapObject.draw();
        Damage createdDamage = (Damage) newMapObject;
        createdDamage.setDamageType(damage.getDamageType());
        createdDamage.setSize(damage.getSize());
        fieldFromDamage.addDamage((Damage) newMapObject);
        MapActivity.dataService.saveFields();
        fieldFromDamage = null;
        addDamageDialogFragment.dismiss();

    }

    public AddDamageDialog getAddDamageDialog() {
        return addDamageDialogFragment;
    }

    public AddFieldDialog getAddFieldDialog() {
        return addFieldDialogFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        mapFragment.setConnectivityListener(this);
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        MapActivity.dataService.saveFields();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        updateNetworkStatus(isConnected);
    }

    private void updateNetworkStatus(boolean isConnected) {
        if (isConnected) {
            statusText.setText(R.string.onlineStatusText);
        } else {
            statusText.setText(R.string.offlineStatusText);
        }
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            statusText.setText(R.string.onlineStatusText);
        } else {
            statusText.setText(R.string.offlineStatusText);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}