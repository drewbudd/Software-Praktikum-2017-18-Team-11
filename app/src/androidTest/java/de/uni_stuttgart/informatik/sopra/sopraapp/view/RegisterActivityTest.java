package de.uni_stuttgart.informatik.sopra.sopraapp.view;


import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.Espresso.*;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2018/01/15
 */
public class RegisterActivityTest {


    @Rule
    public ActivityTestRule<RegisterActivity> mRegisterAcitivityRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    @Before
    public void grantPhonePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.CALL_PHONE");
        } */
    }

    @Test
    public void onCreate() throws Exception {
        onView(withId(R.id.Registerusername));
        onView(withId(R.id.password));
        onView(withId(R.id.userRole));
        onView(withId(R.id.fab));

    }

    @Test
    public void saveUser() throws Exception {
        /*
        onView(withId(R.id.Registerusername)).perform(clearText(), typeText("Testuser"));
        onView(withId(R.id.password)).perform(clearText(), typeText("....."));



        onView(withId(R.id.Registerusername)).check(matches(withText("Testuser")));
        onView(withId(R.id.password)).check(matches(withText(".....")));

        onView(withId(R.id.fab)).perform(click());
        User user = new User("Testuser",".....");
        assertTrue(UserService.getInstance(mRegisterAcitivityRule.getActivity()).getUsers().contains(user));
        */
    }

}