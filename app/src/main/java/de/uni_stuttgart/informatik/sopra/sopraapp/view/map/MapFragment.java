package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLngBounds;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.mapboxsdk.offline.OfflineRegion;
import com.mapbox.mapboxsdk.offline.OfflineRegionError;
import com.mapbox.mapboxsdk.offline.OfflineRegionStatus;
import com.mapbox.mapboxsdk.offline.OfflineTilePyramidRegionDefinition;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.network.ConnectivityReceiver;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;
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
        LocationListener {
    private static final int REQUEST_LOCATION_COURSE = 42;
    private static final int REQUEST_LOCATION_FINE = 7;
    public static MapEditingStatus currentMapEditingStatus = MapEditingStatus.DEFAULT;
    private static MapboxMap mapboxMapGlobal;
    AddFieldDialog addFieldDialogFragment;
    AddDamageDialog addDamageDialogFragment;
    MapObject newMapObject;
    Field fieldFromDamage = null;
    Field createdField = null;
    // variables related to the menu items
    private FloatingActionButton menuFAB;
    private FloatingActionButton fieldsFAB;
    private FloatingActionButton damagesFAB;
    private FloatingActionButton settingsFAB;
    private FloatingActionButton gpsFAB;
    private LinearLayout gpsFABLayout;
    private LinearLayout fieldsFABLayout;
    private LinearLayout damagesFABLayout;
    private LinearLayout settingsFABLayout;
    private boolean isFABOpen;
    private Map<LinearLayout, Float> fabsAndLabels = new HashMap<>();
    private OnFragmentInteractionListener mListener;
    private MapView mapView;
    private View rootView;
    private List<LatLng> currentBorderPoints = new ArrayList<>();
    private LocationManager locationManager;
    private MapFragment mapFragment;
    private NewAreaMode currentMODE = NewAreaMode.GPS;
    private double gpsLat;
    private double gpsLng;
    private MarkerOptions lastGPSLocation;
    private List<Marker> displayingMarkerOptions = new ArrayList<Marker>();
    private TextView statusText;
    private TextView mapLoadedStatus;

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
    public void setCurrentMapEditingStatus(MapEditingStatus currentMapEditingStatus) {
        MapFragment.currentMapEditingStatus = currentMapEditingStatus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_LOCATION_FINE);
        askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_LOCATION_COURSE);


    }

    private void registerLocationListener() {

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
        defineMenuButtons();
        setUpListeners();

        mapLoadedStatus = (TextView)rootView.findViewById(R.id.mapStatus);
        statusText = rootView.findViewById(R.id.networkStatus);

        handleMapLoadedStatus();
        return rootView;
    }

    private void defineMenuButtons() {
        gpsFABLayout = rootView.findViewById(R.id.gps_fab_and_label);
        menuFAB = rootView.findViewById(R.id.fab);
        fieldsFABLayout = rootView.findViewById(R.id.field_fab_and_label);
        damagesFABLayout = rootView.findViewById(R.id.damage_fab_and_label);
        settingsFABLayout = rootView.findViewById(R.id.settings_fab_and_label);
        fieldsFAB = rootView.findViewById(R.id.field_button);
        damagesFAB = rootView.findViewById(R.id.damages_button);
        settingsFAB = rootView.findViewById(R.id.settings_button);
        gpsFAB = rootView.findViewById(R.id.fab_gps);
        fabsAndLabels.put(fieldsFABLayout, -getResources().getDimension(R.dimen.standard_55));
        fabsAndLabels.put(damagesFABLayout, -getResources().getDimension(R.dimen.standard_105));
        fabsAndLabels.put(settingsFABLayout, -getResources().getDimension(R.dimen.standard_155));
        isFABOpen = false;
    }

    private void handleMapLoadedStatus() {


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (!sharedPref.contains("mapLoaded")) {
            setTextMapNotLoaded();
        } else if(sharedPref.contains("mapLoaded") && !ConnectivityReceiver.isConnected()) {
            this.mapLoadedStatus.setText(getResources().getString(R.string.mapNotRefresh));
            this.mapLoadedStatus.setBackgroundColor(Color.YELLOW);
            this.mapLoadedStatus.setTextColor(Color.BLACK);
        } else if(sharedPref.contains("mapLoaded") && ConnectivityReceiver.isConnected()) {
            this.mapLoadedStatus.setText(getResources().getString(R.string.mapLoadedOnline));
            this.mapLoadedStatus.setBackgroundColor(Color.GREEN);
            this.mapLoadedStatus.setTextColor(Color.BLACK);
        }
    }
    private void setTextMapNotLoaded(){
        this.mapLoadedStatus.setText(getResources().getString(R.string.mapNotLoaded));
        this.mapLoadedStatus.setBackgroundColor(Color.RED);
        this.mapLoadedStatus.setTextColor(Color.BLACK);
    }

    private void saveMapLoadedStatus() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("mapLoaded", "Loaded");
        prefEditor.apply();

    }

    private void setUpListeners() {
        // set method to control collapsing menu
        menuFAB.setOnClickListener(view -> changeFABMenuState());

        // set up fields fab
        fieldsFAB.setOnClickListener(v -> {
            switch (currentMapEditingStatus) {
                case DEFAULT:
                    createField();
                    break;
                case START_CREATE_FIELD_COORDINATES:
                    FragmentManager fm = getActivity().getFragmentManager();
                    addFieldDialogFragment = AddFieldDialog.newInstance("Add Field", newMapObject.calculateArea());
                    addFieldDialogFragment.show(fm, "dialog_fragment_add_field");
                    break;
            }
        });

        // set up damages fab
        damagesFAB.setOnClickListener(view -> {
            switch (currentMapEditingStatus) {
                case DEFAULT:
                    createDamage();
                    break;
                case START_CREATE_DAMAGE_COORDINATES:
                    FragmentManager fm = getActivity().getFragmentManager();
                    addDamageDialogFragment = AddDamageDialog.newInstance("Add Damage", newMapObject.calculateArea());
                    addDamageDialogFragment.show(fm, "dialog_fragment_add_field");
                    break;
            }
        });

        // set up settings fab
        settingsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(view.getContext(), MapSettings.class);
                startActivity(settings);
            }
        });

        // set up gps fab
        gpsFAB.setOnClickListener(view -> {
            if ((Math.round(gpsLat) != 0 || Math.round(gpsLng) != 0)) {
                switch (currentMapEditingStatus) {
                    case START_CREATE_FIELD_COORDINATES:
                        mapboxMapGlobal.setCameraPosition(new CameraPosition.Builder()
                                .target(new LatLng(gpsLat, gpsLng)).build());
                        addFieldCoordinate(new LatLng(gpsLat, gpsLng));
                        break;
                    case START_CREATE_DAMAGE_COORDINATES:
                        mapboxMapGlobal.setCameraPosition(new CameraPosition.Builder()
                                .target(new LatLng(gpsLat, gpsLng)).build());
                        addDamageCoordinate(new LatLng(gpsLat, gpsLng));
                        break;
                    default:
                        break;
                }
            } else {
                Snackbar.make(rootView, R.string.error_no_gps_connection, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void focusFABLayout(LinearLayout fabLayout) {
        for (LinearLayout fabAndLabel : fabsAndLabels.keySet()) {
            if (!fabLayout.equals(fabAndLabel)) {
                fabAndLabel.setVisibility(View.GONE);
            }
        }
        menuFAB.setVisibility(View.GONE);
        gpsFABLayout.setVisibility(View.VISIBLE);
        fabLayout.animate().translationY(0).setDuration(300);
    }

    private void releaseFocusFABLayout(LinearLayout fabLayout) {
        fabLayout.animate().translationY(fabsAndLabels.get(fabLayout)).setDuration(100);
        new android.os.Handler().postDelayed(
                () -> {
                    menuFAB.setVisibility(View.VISIBLE);
                    gpsFABLayout.setVisibility(View.GONE);
                    for (LinearLayout fabAndLabel : fabsAndLabels.keySet()) {
                        fabAndLabel.setVisibility(View.VISIBLE);
                    }
                },
                100);

    }

    private void changeFABMenuState() {
        if (!isFABOpen) {
            for (LinearLayout fabAndLabel : fabsAndLabels.keySet()) {
                fabAndLabel.animate().translationY(fabsAndLabels.get(fabAndLabel)).setDuration(300);
                fabAndLabel.animate().alpha(1.0f).setDuration(300);
                fabAndLabel.setVisibility(View.VISIBLE);
            }
            isFABOpen = true;
        } else {
            for (final LinearLayout fabAndLabel : fabsAndLabels.keySet()) {
                fabAndLabel.animate().translationY(0).setDuration(300);
                fabAndLabel.animate().alpha(0.0f).setDuration(300);
                new android.os.Handler().postDelayed(
                        () -> fabAndLabel.setVisibility(View.GONE),
                        300);
            }
            isFABOpen = false;
        }
    }

    private void createField() {
        // gui changes
        focusFABLayout(fieldsFABLayout);
        TextView label = rootView.findViewById(R.id.field_button_label);
        label.setText(R.string.fab_finish_adding);
        Snackbar.make(getView(), R.string.notify_add_marker, Snackbar.LENGTH_SHORT).show();

        // status and variables
        currentMapEditingStatus = MapEditingStatus.START_CREATE_FIELD_COORDINATES;
        newMapObject = new Field();
        newMapObject.setContext(mapboxMapGlobal, mapView);
    }

    public void createDamage() {
        // gui changes
        focusFABLayout(damagesFABLayout);
        TextView label = rootView.findViewById(R.id.damages_button_label);
        label.setText(R.string.fab_finish_adding);
        Snackbar.make(getView(), R.string.message_create_damage, Snackbar.LENGTH_SHORT).show();

        // status and variables
        currentMapEditingStatus = MapEditingStatus.START_CREATE_DAMAGE_COORDINATES;
        newMapObject = new Damage();
        newMapObject.setContext(mapboxMapGlobal, mapView);
    }

    public void saveField() {
        // status, variables and cleanup
        currentMapEditingStatus = MapEditingStatus.DEFAULT;
        createdField = (Field) newMapObject;
        createdField.setSize(createdField.calculateArea());
        createdField.setFieldType(addFieldDialogFragment.getFieldType());
        createdField.setOwner(UserService.getInstance(this.getActivity()).getCurrentUser());
        createdField.setAgent(UserService.getInstance(this.getActivity()).getAllAgents().get(0)); // temporarily set to give all to default agent

        saveFieldToStorage(createdField);
        changeUISaveField();
    }

    public void saveDamage() {
        // status, variables and cleanup
        currentMapEditingStatus = MapEditingStatus.DEFAULT;
        Damage createdDamage = (Damage) newMapObject;
        createdDamage.draw();
        createdDamage.setDamageType(addDamageDialogFragment.getDamageType());
        createdDamage.setSize(createdDamage.calculateArea());
        createdDamage.setCurrentDate();
        createdDamage.setOwner(UserService.getInstance(LoginActivity.getCurrentContext()).getCurrentUser());
        createdDamage.getFieldIds().add(fieldFromDamage.getCurrentId());
        saveDamageToStorage(createdDamage);
        fieldFromDamage = null;
        changeUISaveDamage();
        addDamageDialogFragment.dismiss();
    }
    public void saveFieldToStorage(Field field) {
        MapActivity.dataService.addField(field);
        MapActivity.dataService.saveFields();
    }

    public void changeUISaveField() {
        // gui changes
        createdField.draw();
        releaseFocusFABLayout(fieldsFABLayout);
        TextView label = rootView.findViewById(R.id.field_button_label);
        label.setText(R.string.fab_add_field);
        getAddFieldDialog().dismiss();
    }

    public void changeUISaveDamage() {
        // gui changes
        releaseFocusFABLayout(damagesFABLayout);
        TextView label = rootView.findViewById(R.id.damages_button_label);
        label.setText(R.string.fab_report_damage);
    }



    public void saveDamageToStorage(Damage damage) {
        MapActivity.dataService.addDamage(damage);
        MapActivity.dataService.saveDamages();
        MapActivity.dataService.saveFields();
    }

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
        MapService.getInstance().setMap(this);
        mapboxMap.setOnMapClickListener(this);

        registerLocationListener();

         /* Campus coordinates*/
        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(48.74641, 9.10623))
                .zoom(15).build());
        saveMapLoadedStatus();
        handleMapLoadedStatus();
        for (Field field : MapActivity.dataService.getFields()) {
            field.setContext(mapboxMapGlobal, mapView);
            field.draw();
        }
        for (Damage field : MapActivity.dataService.getDamages()) {
            field.setContext(mapboxMapGlobal, mapView);
            field.draw();
        }
    }


    @Override
    public void onMapChanged(int change) {


    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        switch (currentMapEditingStatus) {

            case START_CREATE_FIELD_COORDINATES:
                addFieldCoordinate(point);
                break;
            case END_CREATE_FIELD_COORDINATES:
                break;
            case CREATE_FIELD_DONE:
                break;
            case START_CREATE_DAMAGE_COORDINATES:
                addDamageCoordinate(point);
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

    public void setNewMapObject(MapObject object){
        this.newMapObject = object;
    }

    private void addDamageCoordinate(@NonNull LatLng point) {
        if (fieldFromDamage == null) {
            for (Field field : MapActivity.dataService.getFields()) {
                if (field.contains(point)) {
                    fieldFromDamage = field;
                    newMapObject.addFieldId(fieldFromDamage.getCurrentId());
                }
            }
        }

        if (fieldFromDamage == null) {
            Snackbar.make(getView(), R.string.notify_inside_of_field, Snackbar.LENGTH_SHORT).show();
        } else {
            if (fieldFromDamage.contains(point)) {
                newMapObject.addMarker(point, currentMapEditingStatus);
                newMapObject.drawMarker(point);
            } else {
                Snackbar.make(getView(), R.string.notify_same_field, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public List<Marker> getDisplayingMarkerOptions() {
        return this.displayingMarkerOptions;
    }

    public void addFieldCoordinate(@NonNull LatLng point) {
        Marker marker = new Marker(new MarkerOptions());
        marker.setPosition(point);
        if (newMapObject.addMarker(marker.getPosition(), currentMapEditingStatus)) {
            newMapObject.drawMarker(point);
            this.displayingMarkerOptions.add(marker);
        } else {
            Snackbar.make(getView(), R.string.notify_outside_of_other_fields, Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (this.currentMODE == NewAreaMode.GPS) {
            this.currentBorderPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }
        if (mapboxMapGlobal != null) {

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
                    break;
            }
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
        mapView.getMapAsync(this);


    }

    /**
     * mapbox  Instance
     * @return
     */
    public MapboxMap getMapBox() {
        return mapboxMapGlobal;
    }

    /**
     * mapview instance
     * @return
     */
    public MapView getMapView() {
        return mapView;
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
        //mapFragment.setConnectivityListener(this);
        registerLocationListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        MapActivity.dataService.saveFields();
        //removeLocatonListener();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    public void updateNetworkStatus(boolean isConnected) {
        String status = "";
        if (isConnected) {
            status = getResources().getString(R.string.onlineStatusText);
            statusText.setText(UserService.getInstance(LoginActivity.getCurrentContext()).getCurrentUser().getName() + " : " + status);
        } else {
            status = getResources().getString(R.string.offlineStatusText);
            statusText.setText(UserService.getInstance(LoginActivity.getCurrentContext()).getCurrentUser().getName() + " : " + status);
        }

        handleMapLoadedStatus();
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