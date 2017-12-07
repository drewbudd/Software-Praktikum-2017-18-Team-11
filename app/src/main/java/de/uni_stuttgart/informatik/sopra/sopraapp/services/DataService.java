package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.Contract;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Dataservice saves Dataservice
 */

public class DataService {

    public static DataService instance = null;
    List<Damage> allDamages = new ArrayList<>();
    private List<User> allUsers = new ArrayList<>();
    private User currentLoggedInUser;
    private List<Field> allFields = new ArrayList<>();


    private DataService() {
        // safety singleton pattern

        loadUsers();
        App.dataStorageService.saveAllFields();
        App.dataStorageService.loadFields();
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

    public void loadUsers() {
        allUsers.addAll(App.dataStorageService.getStubUsers());
    }

    public void saveUsers(List<User> users) {
        this.allUsers = users;
    }


    public void updateDamageEvent(Damage old, Damage updated) {
        // TODO implement
    }

    public void deleteDamageEvent(Damage damage) {

    }

    public void saveField(Field newField) {

        App.dataStorageService.saveNewField(newField);
    }

    public List<Damage> getAllDamages() {
        return App.dataStorageService.getAllDamages();
    }

    /**
     * @return
     */
    public List<Field> loadFields() {

        switch (currentLoggedInUser.getCurrentUserRole()) {
            case GUTACHTER:
                return App.dataStorageService.getAllFields();
            default:
                return App.dataStorageService.getAllFields();
        }
    }

    public List<Field> getAllFields() {
        return App.dataStorageService.getAllFields();
    }

    /**
     * @param id
     * @param damageInField
     */
    public void updateField(int id, Field damageInField) {
        App.dataStorageService.getAllFieldsFromEveryUser().get(id).setDamages(damageInField.getDamages());
    }

    public List<Contract> getAllContracts() {
        return App.dataService.getCurrentLoggedInUser().getContracts();
    }
}
