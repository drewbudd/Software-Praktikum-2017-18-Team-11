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
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.FieldType;
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
        View.OnClickListener,
        OnMapReadyCallback,
        MapView.OnMapChangedListener,
        MapboxMap.OnMapClickListener, MapboxMap.OnPolygonClickListener,
        LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_LOCATION_COURSE = 42;
    private static final int REQUEST_LOCATION_FINE = 7;
    final String TAG = "TAG";
    FloatingActionButton fab;
    LinearLayout fab1;
    LinearLayout fab2;
    LinearLayout fab3;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
    public static MapEditingStatus currentMapEditingStatus = MapEditingStatus.DEFAULT;
    private List<Field> allSavedFields = new ArrayList<>();
    private AppModus currentModus = AppModus.OFFLINE;
    private boolean onPolygonClicked = false;
    private FloatingActionButton newDamage;
    private NewAreaMode currentMODE = NewAreaMode.GPS;
    private MapboxMap mapboxMapGlobal;

    public MapFragment() {
        // Required empty public constructor
        currentMarkerFieldPositions = new ArrayList<>();
        mapFragment = this;
        offlineRegions = new OfflineRegion[10];
        currentDamageMarkerPosition = new ArrayList<>();

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
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, mapFragment);


    }

    private void askPermission(String accessCoarseLocation, int requestLocationCourse) {
        if (ContextCompat.checkSelfPermission(getActivity(), accessCoarseLocation) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{accessCoarseLocation}, requestLocationCourse);
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

        fab = rootView.findViewById(R.id.fab);

        fab1 = rootView.findViewById(R.id.fab1_and_label);
        fab2 = rootView.findViewById(R.id.fab2_and_label);
        fab3 = rootView.findViewById(R.id.fab3_and_label);

        isFABOpen = false;

        rootView.findViewById(R.id.fab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMapEditingStatus == MapEditingStatus.DEFAULT) {
                    currentMapEditingStatus = MapEditingStatus.START_CREATE_FIELD_COORDINATES;
                } else if (currentMapEditingStatus == MapEditingStatus.START_CREATE_FIELD_COORDINATES) {
                    currentMapEditingStatus = MapEditingStatus.END_CREATE_FIELD_COORDINATES;
                    FragmentManager fm = getActivity().getFragmentManager();
                    AddFieldDialog addFieldDialogFragment = AddFieldDialog.newInstance("Some Title");
                    addFieldDialogFragment.show(fm, "dialog_fragment_add_field");
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (currentMapEditingStatus){
                    case DEFAULT:
                }

                FragmentManager fm = getActivity().getFragmentManager();
                AddDamageDialog addDamageDialogFragment = AddDamageDialog.newInstance("Add Damge");
                addDamageDialogFragment.show(fm, "dialog_fragment_add_damage");
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getFragmentManager();
                AddFieldDialog addFieldDialogFragment = AddFieldDialog.newInstance("Some Title");
                addFieldDialogFragment.show(fm, "dialog_fragment_add_field");
            }
        });

        newDamage = rootView.findViewById(R.id.newDamage);
        newDamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //newDamage(v);
            }
        });
        return rootView;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55)).setDuration(200);
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105)).setDuration(400);
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155)).setDuration(600);
        rootView.findViewById(R.id.fab1_label).animate().alpha(1.0f).setDuration(300);
        rootView.findViewById(R.id.fab2_label).animate().alpha(1.0f).setDuration(600);
        rootView.findViewById(R.id.fab3_label).animate().alpha(1.0f).setDuration(900);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        rootView.findViewById(R.id.fab1_label).animate().alpha(0.0f).setDuration(200);
        rootView.findViewById(R.id.fab2_label).animate().alpha(0.0f).setDuration(200);
        rootView.findViewById(R.id.fab3_label).animate().alpha(0.0f).setDuration(200);
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
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                if (currentMapEditingStatus == MapEditingStatus.CREATED_DAMAGE_DONE) {
                    PolygonOptions polygonOptions = new PolygonOptions();
                    polygonOptions.addAll(currentDamageMarkerPosition);
                    Polygon newPolygon = mapboxMap.addPolygon(polygonOptions);
                    newPolygon.setFillColor(Color.BLUE);

                    // TODO: name, type from other fragment
                    Field field = new Field(FieldType.CORN);
                    field.setMarkerPosition(currentMarkerFieldPositions);
                    App.dataService.saveField(field);
                    currentDamageMarkerPosition.clear();
                }


                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.addAll(currentMarkerFieldPositions);
                Polygon newPolygon = mapboxMap.addPolygon(polygonOptions);
                newPolygon.setFillColor(Color.RED);

                // TODO: name, type from other fragment
                Field field = new Field(FieldType.CORN);
                field.setMarkerPosition(currentMarkerFieldPositions);
                App.dataService.saveField(field);
                currentMarkerFieldPositions.clear();
            }
        });
    }

    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        mapboxMapGlobal = mapboxMap;

        mapboxMap.setOnMapClickListener(mapFragment);
        mapboxMap.setOnPolygonClickListener(mapFragment);

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
                this.currentMarkerFieldPositions.add(point);
                mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                break;
            /*
            case CREATE_FIELD:
                this.currentMarkerFieldPositions.add(point);
                mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                break;
            case CREATE_DAMAGE:
                if (this.onPolygonClicked) {
                    this.currentDamageMarkerPosition.add(point);
                    mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                }
                break;
            case MODIFY_FIELD:
                break;
            case CREATED_FIELD_DONE:
                break;
            case CREATED_DAMAGE_DONE:
                break;
            case DEFAULT:
                this.currentMapEditingStatus = MapEditingStatus.CREATE_FIELD;
                break;
        }
        */
        }

    }

    private void showNewFieldOnMap(List<LatLng> markers) {

    }

    public void newDamage(View view) {
        if (currentMapEditingStatus == MapEditingStatus.CREATE_DAMAGE) {
            this.currentMapEditingStatus = MapEditingStatus.CREATED_DAMAGE_DONE;
        } else {
            this.currentMapEditingStatus = MapEditingStatus.CREATE_DAMAGE;
        }
    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {
        this.onPolygonClicked = true;

    }

    @Override
    public void onLocationChanged(Location location) {
        if (this.currentMODE == NewAreaMode.GPS) {
            this.currentBorderPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }
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


    public LocationListener getLocationListener() {
        return this;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onStart();

        // TODO: change accessToken
        Mapbox.getInstance(getContext(), "pk.eyJ1IjoiemluZGxzbiIsImEiOiJjajlzbmY4aDg0NXF6MzNwZzBmNTBuN3MzIn0.jTKwbr6lki_d531dDpGI_w");
        mapView = getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.addOnMapChangedListener(this);
        mapView.getMapAsync(this);


//        mapboxMapGlobal.setOnPolygonClickListener(this);

        // Fixme doesnt work offline right now
        /*
        if (!isNetworkAvailable()) {
            // Get the region bounds and zoom and move the camera.
            LatLngBounds bounds = offlineRegions[0].getDefinition().getBounds();
            double regionZoom = ((OfflineTilePyramidRegionDefinition)
                    offlineRegions[0].getDefinition()).getMinZoom();

// Create new camera position
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(bounds.getCenter())
                    .zoom(regionZoom)
                    .build();

// Move camera to new position
        } */

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

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
            String json = jsonObject.toString();
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
                    public void onError(String error) {
                        Log.e(TAG, "Error: " + error);
                    }
                });


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