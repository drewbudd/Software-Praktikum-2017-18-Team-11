package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.App;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * Defines how the data will be called and
 * saved.
 */

public class DataStorageService {

    public static DataStorageService instance = null;

    private List<User> stubUser = new ArrayList<>();
    private List<Field> allFields = new ArrayList<>();

    private DataStorageService() {
        addStubUsers();
        addStubFields();
    }

    /**
     * creates an Singelton Pattern from DataStorageService
     *
     * @param applicationContext
     * @return
     */
    public static DataStorageService getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new DataStorageService();

        }
        return instance;
    }

    /**
     * get the current ApplicationContext
     *
     * @return
     * @throws Exception
     */
    public static Application getContext() throws Exception {
        return (Application) Class.forName("android.app.AppGlobals")
                .getMethod("getInitialApplication").invoke(null, (Object[]) null);
    }

    /**
     * Adds stub fields
     */
    private void addStubFields() {
    }

    private void addStubUsers() {
        this.stubUser.add(new User("aa", "a"));
        this.stubUser.add(new User("admin2", "admin"));

        this.stubUser.get(0).setUserRole(UserRole.LANDWIRT);
        this.stubUser.get(1).setUserRole(UserRole.GUTACHTER);
    }

    /**
     * Stubusers
     *
     * @return
     */
    public List<User> getStubUsers() {
        return stubUser;
    }

    /**
     * load all saved Fields form Storage
     * using SharedPreferences
     *
     * @return
     */
    public List<Field> getAllFieldsFromEveryUser() {

        Gson gsonHandler = new Gson();
        java.lang.String fieldsAsJSon = null;
        try {
            SharedPreferences sharedPreferences = App.getCurrentContext().getSharedPreferences("App_STORAGE", Context.MODE_PRIVATE);
            fieldsAsJSon = sharedPreferences.getString("allFields", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Field> allFields = gsonHandler.fromJson(fieldsAsJSon, new TypeToken<ArrayList<Field>>() {
        }.getType());
        return allFields;
    }

    /**
     * saves all Fields using SharedPreferences
     *
     */
    public void saveAllFields() {
        Gson gsonHandler = new Gson();
        List<Field> toSave = getAllFieldsFromEveryUser();

        if (toSave != null) {
            this.allFields.addAll(getAllFieldsFromEveryUser());
        }

        java.lang.String allFieldsAsJSon = gsonHandler.toJson(this.allFields);

        SharedPreferences saveFields = null;
        try {
            saveFields = getContext().getSharedPreferences("App_STORAGE", getContext().getApplicationContext().MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = saveFields.edit();
        editor.putString("allFields", allFieldsAsJSon);
        editor.commit();
    }

    public void saveNewField(Field field) {
        this.allFields.add(field);
        saveAllFields();
    }

    public List<Damage> getAllDamages() {
        List<Damage> allDamages = new ArrayList<>();
        if (allFields != null) {
            for (Field field : allFields) {
                allDamages.addAll(field.getDamages());
            }
        }
        if (Debug.isDebuggerConnected()) {
            Damage newDamage = new Damage(new Field());
            User user = new User("", "");
            user.setName("hallo");
            newDamage.setOwner(user);
            allDamages.add(newDamage);
        }
        return allDamages;
    }

    /**
     *
     */
    public void loadFields() {
        this.allFields.clear();
        this.allFields = getAllFieldsFromEveryUser();
    }

    public List<Field> getAllFields() {
        return allFields;
    }
}