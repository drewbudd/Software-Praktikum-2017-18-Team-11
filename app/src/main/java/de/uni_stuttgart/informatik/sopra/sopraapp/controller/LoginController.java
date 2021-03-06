package de.uni_stuttgart.informatik.sopra.sopraapp.controller;

import android.content.Intent;
import android.util.Log;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Controller for the LoginScreen
 */

public class LoginController {


    LoginActivity parentActivity;
    private User currentLoggingUser;

    public LoginController(LoginActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    /**
     * logges User in the app.
     */
    public void login() {

        UserService.getInstance(LoginActivity.getCurrentContext()).setCurrentUser(currentLoggingUser);
        Intent loggedInActivity = new Intent(parentActivity, MapActivity.class);
        parentActivity.startActivity(loggedInActivity);

        Log.v("change positon", "");

    }

    /**
     * will be called from the UI
     * checks if the user can login.
     * will be checked via Dataservice
     * @param loggingInUser
     * @return
     */
    public boolean checkLoginData(User loggingInUser){
        if (UserService.getInstance(LoginActivity.getCurrentContext()).loginUser(loggingInUser)) {
            this.currentLoggingUser = UserService.getInstance(LoginActivity.getCurrentContext()).getCurrentUser();
            return true;
        }
        return  false;
    }



}
