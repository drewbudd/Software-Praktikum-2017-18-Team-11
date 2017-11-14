package de.uni_stuttgart.informatik.sopra.sopraapp;

import de.uni_stuttgart.informatik.sopra.sopraapp.services.ConfigService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;

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
    public static DataService dataService = DataService.instance;
    /**
     *
     */
    public static ConfigService configService = ConfigService.instance;

    public void initializeApp(){

    }

}
