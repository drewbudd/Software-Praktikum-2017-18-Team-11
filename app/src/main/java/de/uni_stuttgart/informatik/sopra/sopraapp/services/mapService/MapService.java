package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.security.acl.NotOwnerException;
import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * provides all Map functions
 */

public class MapService {


    public static Field findCurrentField(LatLng markerPosition){
        for(Field field : App.dataService.getFields()){
            if(field.contains(markerPosition)){
                return field;
            }
        }
       return null;
    }
}
