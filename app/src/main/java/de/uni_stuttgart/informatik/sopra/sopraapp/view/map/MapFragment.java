package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements
        View.OnClickListener,
        OnMapReadyCallback,
        MapView.OnMapChangedListener,
        MapboxMap.OnMapClickListener, MapboxMap.OnPolygonClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MapView mapView;
    private View rootView;
    private List<LatLng> currentMarkerPositions;

    FloatingActionButton fab;
    private MapFragment mapFragment;
    private OfflineRegion[] offlineRegions;


    private MapEditingStatus currentStatus = MapEditingStatus.DEFAULT;
    private List<Field> allSavedFields = new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
        currentMarkerPositions = new ArrayList<>();
        mapFragment = this;
        offlineRegions = new OfflineRegion[10];
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        allSavedFields = App.dataService.loadFields();

        return rootView;
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
                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.addAll(currentMarkerPositions);
                Polygon newPolygon = mapboxMap.addPolygon(polygonOptions);
                newPolygon.setFillColor(Color.RED);
                // TODO: name, type from other fragment
                Field field = new Field(FieldType.CORN);
                field.setMarkerPosition(currentMarkerPositions);
                App.dataService.saveField(field);
                currentMarkerPositions.clear();
            }
        });
    }

    final String TAG = "TAG";

    private MapboxMap mapboxMapGlobal;
    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        mapboxMapGlobal = mapboxMap;

        mapboxMap.setOnMapClickListener(mapFragment);

        /* Campus coordinates*/
        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(48.74641, 9.10623))
                .zoom(11).build());


        OfflineManager offlineManager = OfflineManager.getInstance(getActivity());

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(new LatLng(48.74641, 9.10623))
                .include(new LatLng(48.73641, 9.11623))

                .build();

        OfflineTilePyramidRegionDefinition definition = new
                OfflineTilePyramidRegionDefinition(
                mapboxMap.getStyleUrl(),
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

    @Override
    public void onMapChanged(int change) {
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
        switch (currentStatus) {

            case CREATE_FIELD:
                this.currentMarkerPositions.add(point);
                mapboxMapGlobal.addMarker(new MarkerOptions().setPosition(point));
                break;
            case CREATE_DAMAGE:
                break;
            case MODIFY_FIELD:
                break;
            case CREATED_FIELD_DONE:
                break;
            case CREATED_DAMAGE_DONE:
                break;
            case DEFAULT:
                this.currentStatus = MapEditingStatus.CREATE_FIELD;
                break;
        }

    }

    private void showNewFieldOnMap(List<LatLng> markers) {

    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {


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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onStart();

        // TODO: change accessToken
        Mapbox.getInstance(getContext(), "pk.eyJ1IjoiemluZGxzbiIsImEiOiJjajlzbmY4aDg0NXF6MzNwZzBmNTBuN3MzIn0.jTKwbr6lki_d531dDpGI_w");
        mapView = getView().findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.addOnMapChangedListener(this);
        mapView.getMapAsync(this);
        mapboxMapGlobal.setOnPolygonClickListener(this);

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
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}