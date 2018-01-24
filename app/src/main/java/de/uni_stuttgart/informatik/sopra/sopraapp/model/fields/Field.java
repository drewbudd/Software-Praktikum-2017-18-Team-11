package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * FieldModel
 */

public class Field extends MapObject {


    private String fieldType;
    private User owner;
    private User agent;

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

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

}
