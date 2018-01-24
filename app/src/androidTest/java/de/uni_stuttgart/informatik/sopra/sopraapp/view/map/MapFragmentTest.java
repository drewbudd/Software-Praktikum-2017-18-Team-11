package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.mapbox.mapboxsdk.geometry.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.DataService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.UserService;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.dialogs.AddFieldDialog;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Stefan Zindl
 * @since 2018/01/18
 */
public class MapFragmentTest {

    @Rule
    public ActivityTestRule<MapActivity> mapActivityActivityTestRule = new ActivityTestRule<>(MapActivity.class);
    Context context = null;
    @Before
    public void init(){

        context = InstrumentationRegistry.getInstrumentation().getContext();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void newInstance() throws Exception {
    }

    @Test
    public void getCurrentMapEditingStatus() throws Exception {
    }

    @Test
    public void setCurrentMapEditingStatus() throws Exception {
    }

    */

    @Test
    public void onCreate() throws Exception {
        User user = new User("","");

        UserService.getInstance(context).setCurrentUser(user);
        onView(withId(R.id.fab));
        onView(withId(R.id.networkStatus));

    }

    @Test
    public void settingButtonPressed() {
        onView(withId(R.id.fab));
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.add_field_dialog_finish_button));
        onView(withId(R.id.add_damage_dialog_finish_button));
        onView(withId(R.id.fab));
        onView(withId(R.id.settings_button));
        onView(withId(R.id.field_button_label));
        onView(withId(R.id.damages_button_label));
        onView(withId(R.id.settings_button_label));
    }

    @Test
    public void createField(){
        List<LatLng> fieldPoints = new ArrayList<>();
        fieldPoints.add(new LatLng(48.0,9.0));
        fieldPoints.add(new LatLng(48.0,9.0));
        fieldPoints.add(new LatLng(50.0,9.0));
        fieldPoints.add(new LatLng(50.0,11.0));

        MapObject mapObject = new Field();
        for(LatLng latLng : fieldPoints) {
            mapObject.addMarker(latLng,MapEditingStatus.START_CREATE_FIELD_COORDINATES);
        }
        mapObject.calculateArea();
        MapService.getInstance().drawAllFields();

    }


    @Test
    public void createDamage(){
        List<LatLng> fieldPoints = new ArrayList<>();
        fieldPoints.add(new LatLng(48.0,9.0));
        fieldPoints.add(new LatLng(48.0,9.0));
        fieldPoints.add(new LatLng(50.0,9.0));
        fieldPoints.add(new LatLng(50.0,11.0));


        List<LatLng> damagePoints = new ArrayList<>();
        damagePoints.add(new LatLng(48.1,8.8));
        damagePoints.add(new LatLng(48.0,8.8));
        damagePoints.add(new LatLng(48.0,9.8));
        damagePoints.add(new LatLng(89.0,10.8));
        damagePoints.add(new LatLng(90,12.1));



        MapObject mapObject = new Field();
        for(LatLng latLng : fieldPoints) {
            mapObject.addMarker(latLng,MapEditingStatus.START_CREATE_FIELD_COORDINATES);
        }
        mapObject.calculateArea();

        MapObject damage = new Damage();
        for(LatLng latLng : damagePoints) {
           if(mapObject.contains(latLng)){
                damage.addMarker(latLng, MapEditingStatus.START_CREATE_FIELD_COORDINATES);
            }
        }

        DataService.getInstance(context).addDamage((Damage)damage);
        DataService.getInstance(context).getDamages().clear();
        DataService.getInstance(context).loadDamages();


        MapService.getInstance().drawAllFields();

    }

    public void addFieldCoordinatesSuccessfull(){
         LatLng latLng =  new LatLng(90,12.1);
         Field field = new Field();
        this.mapActivityActivityTestRule.getActivity().mapFragment().setCurrentMapEditingStatus(MapEditingStatus.START_CREATE_FIELD_COORDINATES);
        this.mapActivityActivityTestRule.getActivity().mapFragment().setNewMapObject(field);


         this.mapActivityActivityTestRule.getActivity().mapFragment().addFieldCoordinate(latLng);

         assertEquals(latLng.getLatitude(), this.mapActivityActivityTestRule.getActivity().
                 mapFragment().getDisplayingMarkerOptions().get(0).getPosition().getLatitude());

    }
    /*    onView(withId(R.id.networkStatus)).check(matches(withText(
                mapActivityActivityTestRule.getActivity().getResources().getString(R.id.offlineStatusText)
        )));
    }

/*
    @Test
    public void saveField() {
        Field field = new Field();
        mapActivityActivityTestRule.getActivity().mapFragment().saveFieldToStorage(field);
        assertTrue(DataService.getInstance(mapActivityActivityTestRule.getActivity()).getFields().contains(field));
    }

    @Test
    public void saveDamage(){
        Damage damage = new Damage();
        mapActivityActivityTestRule.getActivity().mapFragment().saveDamageToStorage(damage);
        assertTrue(DataService.getInstance(mapActivityActivityTestRule.getActivity()).getDamages().contains(damage));
    }

    */

    /*
    @Test
    public void onCreateView() throws Exception {
    }

    @Test
    public void createDamage() throws Exception {
    }

    @Test
    public void saveField() throws Exception {
    }

    @Test
    public void saveDamage() throws Exception {
    }

    @Test
    public void onButtonPressed() throws Exception {
    }

    @Test
    public void onAttach() throws Exception {
    }

    @Test
    public void onDetach() throws Exception {
    }

    @Test
    public void onMapReady() throws Exception {
    }

    @Test
    public void onMapChanged() throws Exception {
    }

    @Test
    public void onMapClick() throws Exception {
    }

    @Test
    public void onLocationChanged() throws Exception {
    }

    @Test
    public void onStatusChanged() throws Exception {
    }

    @Test
    public void onProviderEnabled() throws Exception {
    }

    @Test
    public void onProviderDisabled() throws Exception {
    }

    @Test
    public void onViewCreated() throws Exception {
    }

    @Test
    public void getMapBox() throws Exception {
    }

    @Test
    public void getMapView() throws Exception {
    }

    @Test
    public void getAddDamageDialog() throws Exception {
    }

    @Test
    public void getAddFieldDialog() throws Exception {
    }

    @Test
    public void onStart() throws Exception {
    }

    @Test
    public void onResume() throws Exception {
    }

    @Test
    public void onPause() throws Exception {
    }

    @Test
    public void onStop() throws Exception {
    }

    @Test
    public void onNetworkConnectionChanged() throws Exception {
    }

    */

}