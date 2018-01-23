package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2018/01/23
 */
public class UserServiceTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTest = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void saveUser(){

        User user = new User("stefan","..");
        UserService.getInstance(mLoginActivityTest.getActivity()).saveUser(user);

       UserService.getInstance(mLoginActivityTest.getActivity()).loadUsers();

      assertTrue(UserService.getInstance(mLoginActivityTest.getActivity()).getUsers().contains(user));
    }

    /*
    @Test
    public void getInstance() throws Exception {
    }

    @Test
    public void loadStubUser() throws Exception {
    }

    @Test
    public void saveUser() throws Exception {
    }

    @Test
    public void loadUsers() throws Exception {
    }

    @Test
    public void saveUsers() throws Exception {
    }

    @Test
    public void getUsers() throws Exception {
    }

    @Test
    public void loginUser() throws Exception {
    }

    @Test
    public void setCurrentUser() throws Exception {
    }

    @Test
    public void getCurrentUser() throws Exception {
    } */

}