package de.uni_stuttgart.informatik.sopra.sopraapp;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.controller.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginControllerUnitTest {

    private LoginController loginController;

    @Before
    public void init() {
        loginController = new LoginController(new LoginActivity());
    }

    @Test
    public void landwirtCanLogin() {
        assertTrue(loginController.checkLoginData(new User("admin", "admin")));
    }

    @Test
    public void landwirtCanNotLogin() {
        assertTrue(!loginController.checkLoginData(new User("admin123", "admin")));
    }

}