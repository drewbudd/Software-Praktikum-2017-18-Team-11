package de.uni_stuttgart.informatik.sopra.sopraapp.view;

import android.app.Activity;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.IDataService;
import timber.log.Timber;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2018/01/15
 */
public class DamageDetailActivityTest {

    @Rule
    public ActivityTestRule<DamageDetailActivity> damageDetailActivityActivityTestRule = new ActivityTestRule<DamageDetailActivity>(DamageDetailActivity.class);


    @Before
    public void init(){


    }


}