package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
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

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
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

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.AppModus;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final java.lang.String ARG_PARAM1 = "param1";
    private static final java.lang.String ARG_PARAM2 = "param2";
    private static final int REQUEST_LOCATION_COURSE = 42;
    private static final int REQUEST_LOCATION_FINE = 7;
    public static MapEditingStatus currentMapEditingStatus = MapEditingStatus.DEFAULT;
    final java.lang.String TAG = "TAG";
    FloatingActionButton menuFAB;
    LinearLayout fieldsFAB;
    LinearLayout damagesFAB;
    LinearLayout settingsFAB;
    AddFieldDialog addFieldDialogFragment;
    AddDamageDialog addDamageDialogFragment;
    // TODO: Rename and change types of parameters
    private java.lang.String mParam1;
    private java.lang.String mParam2;
    private OnFragmentInteractionListener mListener;
    private MapView mapView;
    private View rootView;
    private List<LatLng> currentMarkerFieldPositions;
    private List<LatLng> currentDamageMarkerPosition;
    private List<LatLng> currentBorderPoints = new ArrayList<>();
    private LocationManager locationManager;
    private boolean isFABOpen;
    private MapFragment mapFragment;
    private OfflineRegion[] offlineRegions;
    private List<Field> allSavedFields = new ArrayList<>();
    private AppModus currentModus = AppModus.OFFLINE;
    private boolean onPolygonClicked = false;
    private FloatingActionButton newDamage;
    private NewAreaMode currentMODE = NewAreaMode.GPS;
    private MapboxMap mapboxMapGlobal;
    private Field damageInField;
    private Field creatingNewField = new Field();
    private Damage creatingNewDamage;
    private double gpsLat;
    private double gpsLng;
    private int foundFieldID = -1;

    public MapFragment() {
        // Required empty public constructor
        currentMarkerFieldPositions = new ArrayList<>();
        mapFragment = this;
        offlineRegions = new OfflineRegion[10];
        currentDamageMarkerPosition = new ArrayList<>();
        App.dataStorageService.getAllFieldsFromEveryUser();

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (!isNetworkAvailable()) {
            this.currentModus = AppModus.OFFLINE;
            initOfflineModus();
        } else {
            this.currentModus = AppModus.ONLINE;
            initOnlineModus();
        }

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

    private void initOnlineModus() {
    }

    private void initOfflineModus() {
        if (Debug.isDebuggerConnected()) {
            downloadMap();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);


        FloatingActionButton fabGPS = rootView.findViewById(R.id.fab_gps);
        menuFAB = rootView.findViewById(R.id.fab);
        fieldsFAB = rootView.findViewById(R.id.fab1_and_label);
        damagesFAB = rootView.findViewById(R.id.fab2_and_label);
        settingsFAB = rootView.findViewById(R.id.fab3_and_label);
        isFABOpen = false;

        fabGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Math.round(gpsLat) != 0 || Math.round(gpsLng) != 0) {
                    mapboxMapGlobal.setCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(gpsLat, gpsLng)).build());
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
                if (currentMapEditingStatus == MapEditingStatus.DEFAULT) {
                    currentMapEditingStatus = MapEditingStatus.START_CREATE_FIELD_COORDINATES;
                    Snackbar.make(getView(), "Add Marker", Snackbar.LENGTH_SHORT).show();
                    damagesFAB.animate().alpha(0.3f).setDuration(100);
                    damagesFAB.setEnabled(false);
                    //settingsFAB.animate().alpha(0.3f).setDuration(100);
                    settingsFAB.setEnabled(false);
                    menuFAB.animate().alpha(0.3f).setDuration(100);
                    menuFAB.setEnabled(false);
                    TextView label = rootView.findViewById(R.id.field_button_label);
                    label.setText("Finish adding points");
                } else if (currentMapEditingStatus == MapEditingStatus.START_CREATE_FIELD_COORDINATES) {
                    if (currentMarkerFieldPositions.size() < 3) {
                        Snackbar.make(getView(), "Please add at least 3 markers", Snackbar.LENGTH_SHORT).show();
                    } else {
                        currentMapEditingStatus = MapEditingStatus.END_CREATE_FIELD_COORDINATES;
                        FragmentManager fm = getActivity().getFragmentManager();
                        damagesFAB.animate().alpha(1.0f).setDuration(100);
                        damagesFAB.setEnabled(true);
                        //settingsFAB.animate().alpha(1.0f).setDuration(100);
                        settingsFAB.setEnabled(true);
                        menuFAB.animate().alpha(1.0f).setDuration(100);
                        menuFAB.setEnabled(true);
                        TextView label = rootView.findViewById(R.id.field_button_label);
                        label.setText("Add field");
                        addFieldDialogFragment = AddFieldDialog.newInstance("Add Field");
                        addFieldDialogFragment.show(fm, "dialog_fragment_add_field");
                    }
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
                        fieldsFAB.animate().alpha(0.3f).setDuration(100);
                        fieldsFAB.setEnabled(false);
                        //settingsFAB.animate().alpha(0.3f).setDuration(100);
                        settingsFAB.setEnabled(false);
                        menuFAB.animate().alpha(0.3f).setDuration(100);
                        menuFAB.setEnabled(false);
                        label.setText("Finish adding points");
                        break;
                    case START_CREATE_DAMAGE_COORDINATES:
                        if (currentDamageMarkerPosition.size() < 3) {
                            Snackbar.make(getView(), "Please add at least 3 markers", Snackbar.LENGTH_SHORT).show();
                        } else {
                            currentMapEditingStatus = MapEditingStatus.END_CREATE_DAMAGE_COORDINATES;
                            fieldsFAB.animate().alpha(1.0f).setDuration(100);
                            fieldsFAB.setEnabled(true);
                            //settingsFAB.animate().alpha(1.0f).setDuration(100);
                            settingsFAB.setEnabled(true);
                            label.setText("Report Damage");
                            menuFAB.animate().alpha(1.0f).setDuration(100);
                            menuFAB.setEnabled(true);
                            creatingNewField.setMarkerPosition(currentMarkerFieldPositions);
                            FragmentManager fm = getActivity().getFragmentManager();
                            addDamageDialogFragment = AddDamageDialog.newInstance("Add Damage");
                            addDamageDialogFragment.show(fm, "dialog_fragment_add_damage");
                        }
                        break;
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

        mapboxMap.setOnMapClickListener(mapFragment);

        /* Campus coordinates*/
        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(48.74641, 9.10623))
                .zoom(15).build());
        if (App.dataService.getAllFields() != null) {
            for (Field field : App.dataService.getAllFields()) {
                drawField(field.getMarkerPosition());
                for (Damage damage : field.getDamages()) {
                    drawDamage(damage.getMarkerPosition());
                }
            }
        }

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
                boolean pointInAField = false;

                if (App.dataService.getAllFields() != null) {
                    for (Field field : App.dataService.getAllFields()) {
                        if (field.contains(point)) {
                            pointInAField = true;
                        }
                    }
                }

                if (!pointInAField) {
                    this.currentMarkerFieldPositions.add(point);
                    mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                } else {
                    Snackbar.make(rootView, "Marker can not be in another field", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case START_CREATE_DAMAGE_COORDINATES:
                if (this.damageInField == null) {
                    List<Field> fields = App.dataService.getAllFields();
                    for (Field field : fields) {
                        if (field.contains(point)) {
                            this.damageInField = field;
                            this.creatingNewDamage = new Damage(damageInField);
                            this.currentDamageMarkerPosition.add(point);
                            this.foundFieldID = fields.indexOf(field);
                            mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                        } else {
                            Snackbar.make(rootView, "Marker has to be in a existing field", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (damageInField.contains(point)) {
                        this.currentDamageMarkerPosition.add(point);
                        mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                    } else {
                        Snackbar.make(rootView, "Marker has to be in the same field", Snackbar.LENGTH_SHORT).show();
                    }
                }

        }
    }

    private void showNewFieldOnMap(List<LatLng> markers) {

    }

    public void newDamage(View view) {
        if (currentMapEditingStatus == MapEditingStatus.CREATE_DAMAGE) {
            currentMapEditingStatus = MapEditingStatus.CREATED_DAMAGE_DONE;
        } else {
            currentMapEditingStatus = MapEditingStatus.CREATE_DAMAGE;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if (this.currentMODE == NewAreaMode.GPS) {
            this.currentBorderPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }

        gpsLat = location.getLatitude();
        gpsLng = location.getLongitude();
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

    public LocationListener getLocationListener() {
        return this;
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
    }

    /**
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    /**
     *
     */
    private void downloadMap() {
        OfflineManager offlineManager = OfflineManager.getInstance(getActivity());

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(new LatLng(48.74641, 9.10623))
                .include(new LatLng(48.73641, 9.11623))

                .build();

        OfflineTilePyramidRegionDefinition definition = new
                OfflineTilePyramidRegionDefinition(
                mapboxMapGlobal.getStyleUrl(),
                latLngBounds,
                10,
                20,
                getActivity().getResources().getDisplayMetrics().density);

        // Implementation that uses JSON to store Yosemite National Park as the offline region name.
        byte[] metadata;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("REGION", "Yosemite National Park");
            java.lang.String json = jsonObject.toString();
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = sharedPref.edit();
            prefEditor.putString("map", json);
            prefEditor.apply();
            metadata = json.getBytes();
        } catch (Exception exception) {
            Log.e(TAG, "Failed to encode metadata: " + exception.getMessage());
            metadata = null;
        }

        // Create the region asynchronously
        offlineManager.createOfflineRegion(definition, metadata,
                new OfflineManager.CreateOfflineRegionCallback() {
                    @Override
                    public void onCreate(final OfflineRegion offlineRegion) {
                        offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);

                        // Monitor the download progress using setObserver
                        offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
                            @Override
                            public void onStatusChanged(OfflineRegionStatus status) {

                                // Calculate the download percentage
                                double percentage = status.getRequiredResourceCount() >= 0
                                        ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
                                        0.0;

                                if (status.isComplete()) {
                                    offlineRegions[0] = offlineRegion;
                                    // TODO: save to file and load
                                    Snackbar.make(mapFragment.getView(), "Download done", Snackbar.LENGTH_LONG).show();
                                    Log.d(TAG, "Region downloaded successfully.");
                                } else if (status.isRequiredResourceCountPrecise()) {
                                    Log.d(TAG, "");
                                }
                            }

                            @Override
                            public void onError(OfflineRegionError error) {
                                // If an error occurs, print to logcat
                                Log.e(TAG, "onError reason: " + error.getReason());
                                Log.e(TAG, "onError message: " + error.getMessage());
                            }

                            @Override
                            public void mapboxTileCountLimitExceeded(long limit) {
                                // Notify if offline region exceeds maximum tile count
                                Log.e(TAG, "Mapbox tile count limit exceeded: " + limit);
                            }
                        });
                    }

                    @Override
                    public void onError(java.lang.String error) {
                        Log.e(TAG, "Error: " + error);
                    }
                });


    }

    /**
     *
     */
    public void clearField() {
        this.currentMarkerFieldPositions.clear();
        this.creatingNewField = new Field();
    }

    public void clearDamage() {
        this.currentDamageMarkerPosition.clear();
        this.creatingNewDamage = null;
        this.damageInField = null;
    }

    public void drawField(List<LatLng> markers) {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(markers);
        Polygon newPolygon = mapboxMapGlobal.addPolygon(polygonOptions);
        newPolygon.setFillColor(Color.RED);
        mapView.refreshDrawableState();
    }

    public void drawDamage(List<LatLng> markers) {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(markers);
        Polygon newPolygon = mapboxMapGlobal.addPolygon(polygonOptions);
        newPolygon.setFillColor(Color.BLUE);
        mapboxMapGlobal.addPolygon(polygonOptions);
    }

    /**
     * @return
     */
    public List<LatLng> getCurrentMarkerFieldPositions() {
        return currentMarkerFieldPositions;
    }

    /**
     * @return
     */
    public List<LatLng> getCurrentDamageMarkerPosition() {
        return currentDamageMarkerPosition;
    }

    /**
     * @return
     */
    public Field getFieldFromDamage() {
        return damageInField;
    }

    /**
     * @param damageInField
     */
    public void setDamageInField(Field damageInField) {
        this.damageInField = damageInField;
    }

    /**
     * @return
     */
    public Field getCreatingNewField() {
        return creatingNewField;
    }

    /**
     * @param creatingNewField
     */
    public void setCreatingNewField(Field creatingNewField) {
        this.creatingNewField = creatingNewField;
    }

    /**
     * @return
     */
    public AddFieldDialog getAddFieldDialogFragment() {
        return addFieldDialogFragment;
    }

    /**
     * @return
     */
    public AddDamageDialog getAddDamageDialogFragment() {
        return addDamageDialogFragment;
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

    public int getFoundFieldID() {
        return foundFieldID;
    }
}