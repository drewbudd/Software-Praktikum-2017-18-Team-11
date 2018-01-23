package de.uni_stuttgart.informatik.sopra.sopraapp.model;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Usermodel
 */

public class User {


    private String name;
    private UserRole currentUserRole;
    private String password;
    private int currentId;
    private static int id;
    private ArrayList<Field> fields = new ArrayList<>();
    private ArrayList<Contract> contracts = new ArrayList<>();

    public User(String username, String password){
        this.name = username;
        this.password = password;
        id++;
        currentId = id;
    }

    /**
     * userRole has to be initizize.
     * @param userRole UserRole
     */
    public User(UserRole userRole){
        this.currentUserRole = userRole;
    }

    /**
     * returns if this user is a Gutachter.
     * or not
     * @return
     */
    public boolean isGutachter(){
        return currentUserRole == UserRole.GUTACHTER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * checks if the user has a given permission.
     * @param right Permissions
     * @return boolean return true if the user has the permission
     */

    public void setUserRole(UserRole currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
