package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEvent;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * provides all Map functions
 */

public class MapService {

    public static MapService instance;

    private ArrayList<Field> fieldsOnMap = new ArrayList<>();
    private ArrayList<DamageEvent> damagesOnMap = new ArrayList<>();

    /**
     *
     */
    private MapService(){
        if(this.instance != null){
            instance = new MapService();
        }
    }

}
