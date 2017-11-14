package de.uni_stuttgart.informatik.sopra.sopraapp.services;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Defines how the data will be called and
 * saved.
 */

public class DataStorageService {

    public DataStorageService instance;

    public DataStorageService() {

        // Safety singelton pattern
        if (this.instance == null) {
            this.instance = new DataStorageService();
        }
    }

}
