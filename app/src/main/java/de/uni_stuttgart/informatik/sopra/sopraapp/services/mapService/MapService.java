package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapFragment;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * provides all Map functions
 */

public class MapService {


    public static MapService instance = null;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private MapFragment mapFragment;


    /**
     * gets the instance of the MapService
     *
     * @return
     */
    public static MapService getInstance() {
        if (instance == null) {
            instance = new MapService();
        }
        return instance;
    }

    /**
     * checks if the point is within a field and returns this field
     *
     * @param markerPosition
     * @return
     */
    public static int findCurrentField(LatLng markerPosition) {
        for (Field field : MapActivity.dataService.getFields())
            if (field.contains(markerPosition)) {
                return field.getCurrentId();
            }
        return -1;
    }

    /**
     * draws all Fields on the map
     */
    public void drawAllFields() {

        mapboxMap.getPolygons().removeAll(mapboxMap.getPolygons());
        for (Field field : MapActivity.dataService.getFields()) {
            field.draw();
        }

        for (Damage damage : MapActivity.dataService.getDamages()) {
            damage.draw();
        }
        mapView.refreshDrawableState();
    }

    /**
     * deletes a field by id
     *
     * @param id
     */
    public void deletePolygonById(MapObject mapObject) {
        boolean delete = false;
        Polygon delPolygon = null;
        for(Polygon polygon : mapboxMap.getPolygons()){
            for(LatLng options : mapObject.getMarkerPositions()){
                if(polygon.getPoints().contains(options)){
                    delete = true;
                    delPolygon = polygon;
                }else{
                    delPolygon = null;
                    return;
                }
            }
        }
        if(delPolygon != null) {
            mapboxMap.removePolygon(delPolygon);
        }
        mapView.refreshDrawableState();
    }

    /**
     * initilizes the attributes using the map form the app.
     *
     * @param mapFragment
     */
    public void setMap(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        this.mapView = mapFragment.getMapView();
        this.mapboxMap = mapFragment.getMapBox();
    }
}
