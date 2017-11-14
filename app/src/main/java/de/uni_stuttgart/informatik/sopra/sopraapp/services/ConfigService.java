package de.uni_stuttgart.informatik.sopra.sopraapp.services;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Config service
 *
 */

public class ConfigService {

    /**
     * instance of the configService
     */
    public static ConfigService instance;


    private ConfigService(){

        // Safaty singelton pattern
        if(instance == null){
            instance = new ConfigService();
        }
    }
}
