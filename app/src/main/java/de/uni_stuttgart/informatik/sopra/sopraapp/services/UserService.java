package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.os.Debug;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public class UserService implements IUserService {

    private List<User> allUsers = new ArrayList<>();
    public  static UserService instance = null;
    private User currentUser = null;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService(){
        loadStubUser();

    }
    @Override
    public void loadStubUser() {
        User gutachter = new User(UserRole.GUTACHTER);
        gutachter.setName("Gutachter");
        gutachter.setPassword(".");

        User landwirt = new User(UserRole.LANDWIRT);
        landwirt.setName("Landwirt");
        landwirt.setPassword(".");
        this.allUsers.add(gutachter);
        this.allUsers.add(landwirt);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public List<User> getUsers() {


        return this.allUsers;
    }

    @Override
    public boolean loginUser(User loggingInUser) {
        for (User user : allUsers) {
            if (loggingInUser.getName().equals(user.getName()) &&
                    loggingInUser.getPassword().equals(user.getPassword())) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }
}
