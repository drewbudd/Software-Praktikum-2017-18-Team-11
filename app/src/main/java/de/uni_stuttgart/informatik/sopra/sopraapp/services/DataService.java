package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.Helpers;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
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
    private List<Field> fields = new ArrayList<>();
    private List<Damage> damages = new ArrayList<>();
    private Context context;
    private Damage detailDamage;

    private DataService(Context context) {
        this.context = context.getApplicationContext();
    }

    public static DataService getInstance(Context activity) {
        if (instance == null) {
            instance = new DataService(activity.getApplicationContext());


        }
        return instance;
    }

    @Override
    public void addField(Field field) {
        this.fields.add(field);
    }

    @Override
    public void addDamage(Damage damage) {
        this.damages.add(damage);
    }

    @Override
    public void loadFields() {
        Gson gson = new Gson();
        List<Field> fields = gson.fromJson(Helpers.loadFieldsFromStorage(), new TypeToken<List<Field>>() {
        }.getType());

        if(fields != null) {
            this.fields = fields;
        }
    }

    @Override
    public void loadDamages() {

        Gson gson = new Gson();
        List<Damage> damages = gson.fromJson(Helpers.loadDamagesFromStorage(), new TypeToken<List<Damage>>() {
        }.getType());

        if(damages != null){
            this.damages = damages;
        }

        Damage damage = new Damage();
        damage.setDamageType("TestType");
        damage.setOwner(new User("bla", "blaaa"));
        this.damages.add(damage);
    }


    @Override
    public void saveFields() {
        Helpers.saveAllFieldsToStorage(context, fields);
    }

    @Override
    public void saveDamages() {
        Helpers.saveDamages(context, damages);
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public List<Damage> getDamages() {
        return damages;
    }

    @Override
    public void deleteField(int fieldId) {
        for (Damage damage : this.damages) {
            damage.getFieldIds().remove(fieldId);
        }
        MapService.getInstance().deletePolygonById(fields.get(fieldId));
        fields.remove(fieldId);
        saveFields();
    }

    @Override
    public void deleteDamage(int damageId) {
        MapService.getInstance().deletePolygonById(damages.get(damageId));
        this.damages.remove(damageId);
        saveDamages();
    }

    @Override
    public Damage getDetailDamage() {
        return detailDamage;
    }

    @Override
    public void setDetailDamage(Damage damage) {
        if (damage != null) {
            this.detailDamage = damage;
        }
    }
}
