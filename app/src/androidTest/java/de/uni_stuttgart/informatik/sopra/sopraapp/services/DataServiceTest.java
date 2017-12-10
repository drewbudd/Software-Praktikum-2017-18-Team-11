package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */
public class DataServiceTest {

    private DataService dataService;

    @Rule
    public ActivityTestRule<App> rule = new ActivityTestRule<App>(App.class);
    List<Field> fields = new ArrayList<>();
    @Before
    public void init(){
        dataService = DataService.getInstance(rule.getActivity());

        fields.add(new Field());
        fields.add(new Field());
        fields.add(new Field());
        fields.add(new Field());

        fields.get(0).setFieldType("Name");
        fields.get(1).setFieldType("Name");

        for(Field f : fields){
            dataService.addField(f);
        }

    }

    @Test
    public void loadFields() throws Exception {



        dataService.saveFields();

        dataService.loadFields();
        List<Field> allFields = new ArrayList<>();
        allFields = dataService.getFields();
        assertEquals("Name",allFields.get(0).getFieldType());
        assertEquals("Name",allFields.get(1).getFieldType());
    }

}