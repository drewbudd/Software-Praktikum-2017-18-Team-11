package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Dataservice saves Dataservice
 */

public class DataService {

    public static DataService instance;
    List<User> allUsers = new ArrayList<>();

    private DataService(){
        instance = new DataService();
    }

    private User currentLoggedInUser;

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }

    public boolean loginUser(User user){
        return true;
    }

    public void loadUsers(){

    }

    public void saveUsers(List<User> users){
        this.allUsers = users;
    }
}
