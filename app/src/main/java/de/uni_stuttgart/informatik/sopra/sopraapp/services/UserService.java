package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.Helpers;
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
    private Context context;

    public static UserService getInstance(Context context) {
        if (instance == null) {
            context = context;
            instance = new UserService(context);
        }
        return instance;
    }

    private UserService(Context context){
        this.context = context.getApplicationContext();
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
        this.allUsers.add(user);
        saveUsers();
    }

    public void loadUsers(){
        Gson gson = new Gson();
        List<User> fields = gson.fromJson(Helpers.loadUsersFromStorage(), new TypeToken<List<User>>() {
        }.getType());
        if (fields != null) {
            allUsers.addAll(fields);
        }
    }

    public void saveUsers() {
        Helpers.saveUsers(context, allUsers);
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
