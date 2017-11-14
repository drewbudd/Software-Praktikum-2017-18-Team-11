package de.uni_stuttgart.informatik.sopra.sopraapp.controller;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.Setup;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Controller for the LoginScreen
 */

public class LoginController {


    /**
     * logges User in the app.
     * @param loggingInUser User
     */
    public void login(User loggingInUser){
        // TODO: implement go to nextPage
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
            login(loggingInUser);
            return true;
        }
        return  false;
    }



}
