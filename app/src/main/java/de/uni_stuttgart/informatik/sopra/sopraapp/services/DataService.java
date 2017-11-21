package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEvent;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEventArt;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Dataservice saves Dataservice
 */

public class DataService {

    public static DataService instance = null;
    List<User> allUsers = new ArrayList<>();
    List<DamageEvent> allDamageEvents = new ArrayList<>();

    private DataService(){
        // safety singleton pattern


        loadUsers();
        allDamageEvents.add(new DamageEvent(DamageEventArt.WIND));
        allDamageEvents.get(0).setOwner(getCurrentLoggedInUser());

    }


    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    private User currentLoggedInUser;

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }

    public boolean loginUser(User loggingInUser) {
        for (User user : allUsers) {
            if (loggingInUser.getName().equals(user.getName()) &&
                    loggingInUser.getPassword().equals(user.getPassword())) {
                this.currentLoggedInUser = user;
                return true;
            }
        }
        return false;
    }

    public void loadUsers(){
        allUsers.addAll(Setup.dataStorageService.getStubUsers());
    }

    public void saveUsers(List<User> users){
        this.allUsers = users;
    }


    public void updateDamageEvent(DamageEvent old, DamageEvent updated) {
        // TODO implement
    }

    public void deleteDamageEvent(DamageEvent damageEvent) {

    }

    public List<DamageEvent> getAllDamageEvents() {
        return this.allDamageEvents;
    }
}
