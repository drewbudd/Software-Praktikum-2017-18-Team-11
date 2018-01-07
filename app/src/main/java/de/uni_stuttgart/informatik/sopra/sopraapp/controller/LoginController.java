package de.uni_stuttgart.informatik.sopra.sopraapp.controller;

import android.content.Intent;
import android.util.Log;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Controller for the LoginScreen
 */

public class LoginController {


    App parentActivity;
    private User currentLoggingUser;

    public LoginController(App parentActivity) {
        this.parentActivity = parentActivity;
    }

    /**
     * logges User in the app.
     * @param loggingInUser User
     */
    public void login() {

        App.userService.setCurrentUser(currentLoggingUser);
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
        if (App.userService.loginUser(loggingInUser)) {
            this.currentLoggingUser = App.userService.getCurrentUser();
            return true;
        }
        return  false;
    }



}
