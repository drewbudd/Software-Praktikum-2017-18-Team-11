package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.LoginActivity;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public class Helpers {

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
        //ArrayList<Field> allFields = gsonHandler.fromJson(fieldsAsJSon, new TypeToken<ArrayList<Field>>() {

        return fieldsAsJSon;
    }


    static Gson gsonHandler = new Gson();



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


}
