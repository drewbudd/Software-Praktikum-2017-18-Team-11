package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import android.graphics.Color;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapFragment;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public class FieldCreation implements FieldOnMap {

    private MapboxMap mapboxMap;
    private MapView mapView;
    private Field field;
    private int minMarkers = 3;

    public FieldCreation(MapFragment mapFragment){
        this.mapboxMap = mapFragment.getMapBox();
        this.mapView = mapFragment.getMapView();
        field = new Field();
    }


    @Override
    public void createNewField(Field field) {

    }

    @Override
    public void saveNewField() {
        App.dataService.addField(field);
    }

    @Override
    public void deleteField() {

    }

    @Override
    public void modifyField( Field updatedField) {

    }

    @Override
    public void drawFieldPolygon() {
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(field.getMarkerPositions());
            Polygon newPolygon = mapboxMap.addPolygon(polygonOptions);
            newPolygon.setFillColor(Color.RED);
            mapView.refreshDrawableState();
            saveNewField();
    }

    @Override
    public boolean addMarkerPosition(LatLng point) {
        if(!field.contains(point)) {
            this.field.addMarkerPosition(point);
            return true;
        }
        return false;
    }

    @Override
    public void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setPosition(point);
        mapboxMap.addMarker(markerOptions);
    }

    public boolean isDrawReady() {
        return field.getMarkerPositions().size()>= this.minMarkers;
    }
}
