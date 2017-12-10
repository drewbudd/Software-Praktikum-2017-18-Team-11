package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import android.graphics.Color;

import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.maps.MapFragment;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public class MapInitialization {

    private MapboxMap mapboxMap;
    private MapView mapView;
    public void loadFields(de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapFragment mapFragment){
        mapboxMap = mapFragment.getMapBox();
        mapView = mapFragment.getMapView();
        App.dataService.loadFields();
        List<Field> allFields = App.dataService.getFields();
        for(Field field : allFields){
            drawFieldPolygon(field);
        }

    }

    private void drawFieldPolygon(Field field) {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(field.getMarkerPositions());
        Polygon newPolygon = mapboxMap.addPolygon(polygonOptions);
        newPolygon.setFillColor(Color.RED);
        mapView.refreshDrawableState();
    }
}
