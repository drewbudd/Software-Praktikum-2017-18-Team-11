package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.Helpers;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.Contract;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapService;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Dataservice saves Dataservice
 */

public class DataService implements IDataService{

    public static DataService instance = null;
    private List<Field> allFields = new ArrayList<>();
    private List<Damage> damages = new ArrayList<>();
    private Context context;

    private DataService(Context context) {
       this.context = context.getApplicationContext();

    }

    public static DataService getInstance(App activity) {
        if (instance == null) {
            instance = new DataService(activity.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void addField(Field field) {
        this.allFields.add(field);
    }

    @Override
    public void loadFields(){

        Gson gson = new Gson();
        List<Field> fields =  gson.fromJson(Helpers.loadFieldsFromStorage(), new TypeToken<List<Field>>(){}.getType());
        if(fields != null) {
            this.allFields = fields;
        }
    }

    @Override
    public void saveFields(){
        List<Field> fields = allFields;
        Helpers.saveAllFieldsToStorage(context,fields);
    }

    @Override
    public List<Field> getFields(){
        return allFields;
    }

    public List<Damage> getDamages() {
        return damages;
    }

    public void loadDamages(){
        this.damages.clear();
        for(Field field : allFields){
            this.damages.addAll(field.getDamages());
        }
    }

    @Override
    public void deleteFieldById(int itemId) {
        allFields.remove(itemId);
        MapService.getInstance().deleteFieldById(itemId);
        saveFields();
    }
}
