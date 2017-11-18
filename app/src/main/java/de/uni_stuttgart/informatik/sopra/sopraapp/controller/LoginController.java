package de.uni_stuttgart.informatik.sopra.sopraapp.controller;

import android.content.Intent;

import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MainActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Controller for the LoginScreen
 */

public class LoginController {


    LoginActivity parentActivity;
    private User currentLoggingUser;

    public LoginController(LoginActivity parentActivity){
        this.parentActivity = parentActivity;
    }

    /**
     * logges User in the app.
     * @param loggingInUser User
     */
    public void login() {
        Intent loggedInActivity = new Intent(parentActivity, MainActivity.class);
        parentActivity.startActivity(loggedInActivity);
    }

    /**
     * will be called from the UI
     * checks if the user can login.
     * will be checked via Dataservice
     * @param loggingInUser
     * @return
     */
    public boolean checkLoginData(User loggingInUser){
        if(Setup.dataService.loginUser(loggingInUser)){
            currentLoggingUser = loggingInUser;
            return true;
        }
        return  false;
    }



}
