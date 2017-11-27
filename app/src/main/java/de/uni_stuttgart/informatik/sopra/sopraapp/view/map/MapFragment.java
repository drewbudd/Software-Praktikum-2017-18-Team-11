package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.controller.MainController;
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
public class MapFragment extends SupportMapFragment implements
        OnMapReadyCallback,
        MapboxMap.OnMapClickListener,
        MapView.OnMapChangedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<LatLng> temporarySaveMarkerLatLng = new ArrayList<>();

    private MainController mainController;
    private MapEditingStatus currentStatus = MapEditingStatus.DEFAULT;

    private OnFragmentInteractionListener mListener;
    private MapView mapView;
    private Mapbox mapbox;
    private MapboxMap mapboxMap;

    public MapFragment() {
        // Required empty public constructor
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

        //mainController = new MainController();


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: change accessToken
        mapbox = Mapbox.getInstance(view.getContext(), "pk.eyJ1IjoiemluZGxzbiIsImEiOiJjajlzbmY4aDg0NXF6MzNwZzBmNTBuN3MzIn0.jTKwbr6lki_d531dDpGI_w");
        mapView.onCreate(savedInstanceState);
        mapView.addOnMapChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map3, container, false);
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
    public void onMapReady(MapboxMap mapboxMap) {
        mapboxMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        switch (this.currentStatus) {

            case CREATE_FIELD:
                this.temporarySaveMarkerLatLng.add(latLng);
                break;
            case CREATE_DAMAGE:
                break;
            case MODIFY_FIELD:
                break;
            case CREATED_FIELD_DONE:
                // TODO: get fieldType from Activity
                Field newField = new Field(FieldType.CORN);
                newField.setMarkerPosition(this.temporarySaveMarkerLatLng);
                App.dataService.saveField(newField);
                this.temporarySaveMarkerLatLng.clear();
                break;
            case CREATED_DAMAGE_DONE:
                break;
            case DEFAULT:
                break;
        }
    }

    public void createFieldDone() {
        this.currentStatus = MapEditingStatus.CREATED_DAMAGE_DONE;
        // TODO: get fieldType from Activity
        Field newField = new Field(FieldType.CORN);
        newField.setMarkerPosition(this.temporarySaveMarkerLatLng);
        App.dataService.saveField(newField);
        this.temporarySaveMarkerLatLng.clear();
    }

    public void addFieldToMap(MapboxMap mapBox, Field field) {
        PolygonOptions options = new PolygonOptions();
        options.addAll(field.getMarkerPosition());
        // TODO: set color from field
    }

    @Override
    public void onMapChanged(int i) {

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
