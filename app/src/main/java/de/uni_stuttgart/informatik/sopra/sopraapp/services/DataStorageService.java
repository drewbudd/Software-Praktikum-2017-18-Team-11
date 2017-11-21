package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Defines how the data will be called and
 * saved.
 */

public class DataStorageService {

    public static DataStorageService instance = null;
    private List<User> stubUser = new ArrayList<>();


    private DataStorageService() {
        addStubUsers();
    }

    public static DataStorageService getInstance() {
        if (instance == null) {
            instance = new DataStorageService();
        }
        return instance;
    }

    private void addStubUsers() {
        this.stubUser.add(new User("aa", "a"));

        this.stubUser.add(new User("admin2", "admin"));
        this.stubUser.add(new User("", ""));

        stubUser.get(0).setUserRole(UserRole.LANDWIRT);
        stubUser.get(1).setUserRole(UserRole.GUTACHTER);
        stubUser.get(2).setUserRole(UserRole.LANDWIRT);

    }

    /**
     * Stubusers
     *
     * @return
     */
    public List<User> getStubUsers() {
        return stubUser;
    }

}
