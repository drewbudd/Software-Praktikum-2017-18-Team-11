package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.Helpers;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.MapService;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Dataservice saves Dataservice
 */

public class DataService implements IDataService {

    public static DataService instance = null;
    private List<Field> allFields = new ArrayList<>();
    private List<Damage> damages = new ArrayList<>();
    private Context context;
    private List<MapObject> allElements = new ArrayList<>();

    private DataService(Context context) {
        this.context = context.getApplicationContext();

    }

    public static DataService getInstance(Activity activity) {
        if (instance == null) {
            instance = new DataService(activity.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void addField(Field field) {
        this.allElements.add(field);
    }

    @Override
    public void loadFields() {

        Gson gson = new Gson();
        List<Field> fields = gson.fromJson(Helpers.loadFieldsFromStorage(), new TypeToken<List<Field>>() {
        }.getType());
        if (fields != null) {
            for(Field field : fields){
                allElements.add(field);
                for(Damage damage : field.getDamages()){
                    allElements.add(damage);
                }
            }
        }

       updateFieldDamage();
    }

    private void updateFieldDamage(){
        allFields.clear();
        damages.clear();
        for(MapObject mapObject: allElements){
            if(mapObject instanceof Field){
                allFields.add((Field)mapObject);
            }
            else{
                damages.add((Damage)mapObject);
            }
        }
    }

    @Override
    public void saveFields() {
        updateFieldDamage();
        int size = allFields.size();
        Helpers.saveAllFieldsToStorage(context, allFields);
    }

    @Override
    public List<Field> getFields() {
        return allFields;
    }

    public List<Damage> getDamages() {
        return damages;
    }

    @Override
    public void deleteFieldByIdWithDamages(int itemId) {
        Field field =(Field) allElements.get(itemId);
        for(Damage damage : field.getDamages()){
            MapService.getInstance().deletePolygonById(allElements.indexOf(damage));
            allElements.remove(damage);
        }
        allElements.remove(itemId);
        MapService.getInstance().deletePolygonById(itemId);
        updateFieldDamage();
        saveFields();
    }

    @Override
    public List<MapObject> allElements() {
        return allElements;
    }
}
