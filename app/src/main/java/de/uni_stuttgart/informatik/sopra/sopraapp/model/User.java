package de.uni_stuttgart.informatik.sopra.sopraapp.model;

import java.util.ArrayList;
import java.util.Arrays;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.Permissions;
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
    private ArrayList<Permissions> userPermissions = new ArrayList<>();
    private ArrayList<Field> fields = new ArrayList<>();
    private ArrayList<Contract> contracts = new ArrayList<>();

    public User(String username, String password){
        this.name = username;
        this.password = password;
    }

    /**
     * userRole has to be initizize.
     * @param userRole UserRole
     */
    public User(UserRole userRole){
        this.initRights();
        this.currentUserRole = userRole;
    }

    /**
     * initialize Rights for each Role
     */
    private void initRights(){
       switch (currentUserRole){
           case LANDWIRT:
               addLandwirtRigihts();
               break;
           case GUTACHTER:
               addGutatchterRights();
               break;
       }
    }

    /**
     * adds the rights for the Landwirt
     */
    private void addLandwirtRigihts(){
        this.addGutatchterRights();
        this.userPermissions.add(Permissions.VIEW_OWN_DAMAGEEVENTS);
        this.userPermissions.remove(Permissions.EDIT_OWN_DAMAGEEVENTS);
        this.userPermissions.add(Permissions.VIEW_OWN_FIELDS);
        this.userPermissions.add(Permissions.EDIT_OWN_FIELDS);

    }

    /**
    * Gutachter permissions:
     *  includes permissions from Landwirt
     *  and additional permissions
     */
    private void addGutatchterRights(){
        this.userPermissions.addAll(Arrays.asList(Permissions.values()));
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
    public boolean hasPermissionFor(Permissions right){
        return userPermissions.contains(right);
    }

    public void setUserRole(UserRole currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getCurrentUserRole() {
        return currentUserRole;
    }
}
