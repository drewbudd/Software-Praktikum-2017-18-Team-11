package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent.DamageEvent;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * FieldModel
 */

public class Field implements OnMapElement {


    private FieldType fieldType;
    private List<DamageEvent> damages;
    private List<LatLng> markerPosition = new ArrayList<>();
    private Color color;

    public Field(FieldType fieldType) {
        damages = new ArrayList<>();
        this.fieldType = fieldType;
    }

    /**
     * @param damage
     */
    public void addDamage(DamageEvent damage) {
        this.damages.add(damage);
    }


    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public List<DamageEvent> getDamages() {
        return damages;
    }

    public void setDamages(List<DamageEvent> damages) {
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
