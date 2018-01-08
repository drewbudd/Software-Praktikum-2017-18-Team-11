package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;

import static org.junit.Assert.*;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */
public class DataServiceTest {

    private DataService dataService;

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
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