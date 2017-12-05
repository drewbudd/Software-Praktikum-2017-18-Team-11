package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * FieldModel
 */

public class Field implements OnMapElement {


    private String fieldType;
    private List<Damage> damages;
    private List<LatLng> markerPosition = new ArrayList<>();
    private Color color;

    public Field() {
        damages = new ArrayList<>();
    }

    /**
     * @param damage
     */
    public void addDamage(Damage damage) {
        this.damages.add(damage);
    }


    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public List<Damage> getDamages() {
        return damages;
    }

    public void setDamages(List<Damage> damages) {
        this.damages = damages;
    }

    public List<LatLng> getMarkerPosition() {
        return markerPosition;
    }

    public void setMarkerPosition(List<LatLng> markerPosition) {
        this.markerPosition = markerPosition;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
