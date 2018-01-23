package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
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

    @Before
    public void init(){
        pause();
    }

    public void pause(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadSavedUser(){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        User user = new User("stefan","..");
        UserService.getInstance(context).saveUser(user);
        pause();
        UserService.getInstance(context).loadUsers();
        pause();
        assertTrue(UserService.getInstance(context).getUsers().contains(user));
    }

    @Test
    public void loadWrongSavedUser(){
        pause();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        User user = new User("stefan","..");
        User peter = new User("peter","..");
        UserService.getInstance(context).saveUser(user);
        pause();

        UserService.getInstance(context).loadUsers();
        pause();

        assertFalse(UserService.getInstance(context).getUsers().contains(peter));
    }

    @Test
    public void addUser(){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        User user = new User("stefan","..");
        pause();

        UserService.getInstance(context).setCurrentUser(user);
        pause();

        User currentUser = UserService.getInstance(context).getCurrentUser();
        pause();

        assertEquals(user.getName(),currentUser.getName());
        assertEquals(user.getPassword(),currentUser.getPassword());
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