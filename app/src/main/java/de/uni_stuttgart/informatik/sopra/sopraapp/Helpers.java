package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public class Helpers {

    static Gson gsonHandler = new Gson();


    /**
     * load users
     * @return
     */
    public static String loadUsersFromStorage(){
        Gson gsonHandler = new Gson();
        java.lang.String fieldsAsJSon = null;
        try {
            SharedPreferences sharedPreferences = LoginActivity.getCurrentContext().getSharedPreferences("App_STORAGE", Context.MODE_PRIVATE);
            fieldsAsJSon = sharedPreferences.getString("users", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldsAsJSon;
    }



    /**
     * saves all Users using SharedPreferences
     */
    public static void saveUsers(Context context, List<User> users) {


        AsyncTask.execute(() -> {

            SharedPreferences saveFields = null;
            String allFieldsAsJSon = "";
            try {
                allFieldsAsJSon = gsonHandler.toJson(users);
                saveFields = context.getSharedPreferences("App_STORAGE", context.getApplicationContext().MODE_PRIVATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = saveFields.edit();
            editor.putString("users", allFieldsAsJSon);
            editor.apply();
        });


    }


    /**
     * loads all Fields from the Storage
     * @return
     */
    public static String loadFieldsFromStorage() {

        Gson gsonHandler = new Gson();
        java.lang.String fieldsAsJSon = null;
        try {
            SharedPreferences sharedPreferences = LoginActivity.getCurrentContext().getSharedPreferences("App_STORAGE", Context.MODE_PRIVATE);
            fieldsAsJSon = sharedPreferences.getString("allFields", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldsAsJSon;
    }



    /**
     * saves all Fields using SharedPreferences
     */
    public static void saveAllFieldsToStorage(Context context, List<Field> allFields) {


        AsyncTask.execute(() -> {

            SharedPreferences saveFields = null;
            String allFieldsAsJSon = "";
            try {
                allFieldsAsJSon = gsonHandler.toJson(allFields);
                saveFields = context.getSharedPreferences("App_STORAGE", context.getApplicationContext().MODE_PRIVATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = saveFields.edit();
            editor.putString("allFields", allFieldsAsJSon);
            editor.apply();
        });


    }


    public static void saveDamages(Context context, List<Damage> damages) {
    }

    public static String loadDamagesFromStorage() {

        return "";
    }
}
