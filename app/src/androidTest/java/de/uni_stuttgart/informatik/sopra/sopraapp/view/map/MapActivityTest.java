package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.manage.ManageServiceFragment;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Stefan Zindl
 * @since 2017/12/14
 */
@RunWith(AndroidJUnit4.class)
public class MapActivityTest {

    @Rule
    public ActivityTestRule<App> appRule = new ActivityTestRule<App>(App.class);

    @Rule
    public ActivityTestRule<MapActivity> rule = new ActivityTestRule<MapActivity>(MapActivity.class);

    MapActivity mapActivity;

    @Before
    public void init() {
    }

    @Test
    public void onCreate() throws Exception {
        MapActivity mapActivity = mapActivity = rule.getActivity();
        MapFragment mapFragment = (MapFragment) mapActivity.getFragmentManager().findFragmentById(R.id.map_fragment);
        ManageServiceFragment manageServiceFragment = (ManageServiceFragment) mapActivity.getFragmentManager().findFragmentById(R.id.manageServiceFragment);

        assertThat(mapFragment, notNullValue());
        assertThat(manageServiceFragment, notNullValue());
    }

    @Test
    public void onFragmentInteraction() throws Exception {
    }

    @Test
    public void onRequestPermissionsResult() throws Exception {
    }

    @Test
    public void saveNewField() throws Exception {
    }

    @Test
    public void saveNewDamage() throws Exception {
    }

}