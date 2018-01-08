package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapFragment;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * provides all Map functions
 */

public class MapService {


    public static MapService instance = null;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private MapFragment mapFragment;


    /**
     * gets the instance of the MapService
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
     * @param markerPosition
     * @return
     */
    public static Field findCurrentField(LatLng markerPosition){
        for(Field field : MapActivity.dataService.getFields()){
            if(field.contains(markerPosition)){
                return field;
            }
        }
       return null;
    }

    /**
     * draws all Fields on the map
     */
    public void drawAllFields(){
        List<Field> allFields = MapActivity.dataService.getFields();
        for(Field field : allFields){
            field.setContext(mapboxMap,mapView);
            field.draw();
        }
        for(Damage damage : MapActivity.dataService.getDamages()){
            damage.setContext(mapboxMap,mapView);
            damage.draw();
        }
        mapView.refreshDrawableState();

    }

    /**
     * deletes a field by id
     * @param id
     */
    public void deleteFieldById(int id){
        int idd = id;
        mapboxMap.removePolygon(mapboxMap.getPolygons().get(id));
        mapView.refreshDrawableState();
    }

    /**
     * initilizes the attributes using the map form the app.
     * @param mapFragment
     */
    public void setMap(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        this.mapView = mapFragment.getMapView();
        this.mapboxMap = mapFragment.getMapBox();
    }
}
