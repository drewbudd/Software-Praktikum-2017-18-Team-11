package de.uni_stuttgart.informatik.sopra.sopraapp;

import de.uni_stuttgart.informatik.sopra.sopraapp.services.ConfigService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataStorageService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapService;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * initialize all config, befoe the app can be used correctly
 */

public class Setup {


    /**
     *
     */
    public static DataStorageService dataStorageService = DataStorageService.getInstance();
    public static DataService dataService = DataService.getInstance();
    public static ConfigService configService = ConfigService.getInstance();
    public static MapService mapService = MapService.getInstance();


    public void initializeApp(){

    }

}
