package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * FieldModel
 */

public class Field extends MapObject {


    private String fieldType;
    private User owner;
    private User gutachter;

    public Field() {
        super.color = Color.GREEN;
        alphaValue = 0.25f;
    }

    @Override
    public void addMarkerPosition(LatLng point) {

    }



    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }



    @Override
    public void setField(Field fieldFromDamage) {

    }

    @Override
    public void addFieldId(int fieldId) {

    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getGutachter() {
        return gutachter;
    }

    public void setGutachter(User gutachter) {
        this.gutachter = gutachter;
    }

}
