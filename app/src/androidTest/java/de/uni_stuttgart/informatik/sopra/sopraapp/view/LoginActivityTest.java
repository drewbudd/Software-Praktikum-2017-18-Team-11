package de.uni_stuttgart.informatik.sopra.sopraapp.view;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.controller.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2018/01/23
 */
public class LoginActivityTest {



    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivtyTest = new ActivityTestRule<>(LoginActivity.class);

    private LoginController loginController;
    @Before
    public void init(){
        loginController =new LoginController(mLoginActivtyTest.getActivity());
    }

    @Test
    public void loginSuccessFul(){

       assertTrue(loginController.checkLoginData(new User("Gutachter",".")));

    }

    @Test
    public void loginNotSuccessFul(){
        assertFalse(loginController.checkLoginData(new User("Gutachter","..")));

    }

}