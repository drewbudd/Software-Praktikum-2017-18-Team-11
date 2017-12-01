package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.FieldType;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/30
 */
public class DataStorageServiceTest {


    private List<Field> allFields = new ArrayList<>();
    private DataStorageService dataStorageService;

    @Before
    public void init(){
        dataStorageService = App.dataStorageService;

        allFields.add(new Field(FieldType.CORN));
        allFields.add(new Field(FieldType.CORN));
        allFields.add(new Field(FieldType.CORN));
    }

    @Test
    public void loadAllFieldsSuccessful(){
        dataStorageService.saveAllFields(allFields);

        List<Field> allFields = dataStorageService.getAllFieldsFromEveryUser();
        for(Field field : allFields){
            System.out.println(field);
        }

    }



    @Before
    public void setUp() throws Exception {
    }

    }

}