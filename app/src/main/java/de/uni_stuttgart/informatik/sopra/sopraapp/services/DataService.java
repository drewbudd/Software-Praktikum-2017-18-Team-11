package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.Contract;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Dataservice saves Dataservice
 */

public class DataService implements IDataService{

    public static DataService instance = null;
    private List<User> allUsers = new ArrayList<>();
    private User currentUser;
    private List<Contract> allContracts = new ArrayList<>();


    private DataService() {
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    @Override
    public void saveField(Field damage) {

    }

    @Override
    public List<Field> loadFields() {
        return null;
    }
}
