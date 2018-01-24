package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.RegisterActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Stefan Zindl
 * @since 2018/01/16
 */
@RunWith(AndroidJUnit4.class)
public class DataServiceTest {

    IDataService dataService = null;

    @Rule
    public ActivityTestRule<RegisterActivity> mLoginActivity = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);


    @Before
    public void init(){
        dataService = DataService.getInstance(mLoginActivity.getActivity());
    }


    @Test
    public void getInstanceNotNull() throws Exception {
        dataService = null;
        dataService = DataService.getInstance(mLoginActivity.getActivity());

        assertNotNull(dataService);
    }

    @Test
    public void getInstanceNull() throws Exception {
        dataService = null;
        assertNull(dataService);
    }

    @Test
    public void addField() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        dataService = DataService.getInstance(context);

        Field field = new Field();
        field.setFieldType("Mais");
        dataService.addField(field);

        assertEquals(1,dataService.getFields().size());

    }

    @Test
    public void loadSavedField() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        dataService = DataService.getInstance(context);

        Field field = new Field();
        field.setFieldType("Mais");
        dataService.addField(field);
        dataService.getFields().clear();
        dataService.loadFields();
       // assertTrue(dataService.getFields().size()==1);

        int size = dataService.getFields().size();

        assertEquals(0,size);

    }


   // @Test(expected = NullPointerException.class)
    @Test
    public void loadFieldsNullExcetion() throws Exception {
       dataService.loadFields();
       dataService.setFields(new ArrayList<>());
       assertEquals(0,dataService.getFields().size());
    }

    /**
    @Test
    public void saveFields() throws Exception {



    }

    @Test
    public void getFields() throws Exception {
    }

    @Test
    public void getDamages() throws Exception {
    }

    @Test
    public void deleteFieldByIdWithDamages() throws Exception {
    }

    @Test
    public void allElements() throws Exception {
    }

    @Test
    public void deleteDamage() throws Exception {
    }

    @Test
    public void setDetailDamage() throws Exception {
    }

    @Test
    public void getDetailDamage() throws Exception {
    }
    **/

}