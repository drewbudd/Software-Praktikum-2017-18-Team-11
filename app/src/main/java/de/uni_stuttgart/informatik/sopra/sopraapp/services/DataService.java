package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEvent;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEventArt;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Dataservice saves Dataservice
 */

public class DataService {

    public static DataService instance = null;
    private List<User> allUsers = new ArrayList<>();
    private User currentLoggedInUser;
    private List<Field> allFields = new ArrayList<>();

    List<DamageEvent> allDamageEvents = new ArrayList<>();


    private DataService(){
        // safety singleton pattern

        loadUsers();
        allDamageEvents.add(new DamageEvent(DamageEventArt.WIND));
        allDamageEvents.get(0).setOwner(getCurrentLoggedInUser());
        allDamageEvents.add(new DamageEvent(DamageEventArt.WIND));
        allDamageEvents.get(1).setOwner(getCurrentLoggedInUser());

    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

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
        allUsers.addAll(App.dataStorageService.getStubUsers());
    }

    public void saveUsers(List<User> users){
        this.allUsers = users;
    }


    public void updateDamageEvent(DamageEvent old, DamageEvent updated) {
        // TODO implement
    }

    public void deleteDamageEvent(DamageEvent damageEvent) {

    }

    public void saveField(Field newField) {
        this.allFields.add(newField);
    }

    public List<DamageEvent> getAllDamageEvents() {
        return this.allDamageEvents;
    }

    /**
     * @return
     */
    public List<Field> loadFields() {

        switch (currentLoggedInUser.getCurrentUserRole()) {
            case GUTACHTER:
                return App.dataStorageService.getAllFieldsFromEveryUser();
            default:
                return App.dataStorageService.getAllFieldsBy(getCurrentLoggedInUser());
        }
    }
}
