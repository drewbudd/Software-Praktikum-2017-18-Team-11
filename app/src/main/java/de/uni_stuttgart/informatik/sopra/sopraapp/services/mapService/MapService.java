package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * provides all Map functions
 */

public class MapService {

    public static MapService instance = null;

    private ArrayList<Field> fieldsOnMap = new ArrayList<>();
    private ArrayList<Damage> damagesOnMap = new ArrayList<>();


    public static MapService getInstance() {
        if (instance == null) {
            instance = new MapService();
        }
        return instance;
    }


    public void saveNewField() {

    }

    public void PositionToJson(LatLng position) {

    }


}