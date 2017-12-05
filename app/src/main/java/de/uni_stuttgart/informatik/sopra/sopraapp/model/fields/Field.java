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


    private FieldType fieldType;
    private List<Damage> damages;
    private List<LatLng> markerPosition = new ArrayList<>();
    private Color color;
    private double minLat;
    private double maxLat;
    private double minLng;
    private double maxLng;

    public Field(FieldType fieldType) {
        damages = new ArrayList<>();
        this.fieldType = fieldType;
        minLat = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
        minLng = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
    }

    /**
     * @param damage
     */
    public void addDamage(Damage damage) {
        this.damages.add(damage);
    }


    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
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

        for (LatLng latLng : markerPosition) {
            double lat = latLng.getLatitude();
            double lng = latLng.getLongitude();

            if (lat < minLat) {
                minLat = lat;
            }

            if (lat > maxLat) {
                maxLat = lat;
            }

            if (lng < minLng) {
                minLng = lng;
            }

            if (lng > maxLng) {
                maxLng = lng;
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean contains(LatLng point) {
        double lat = point.getLatitude();
        double lng = point.getLongitude();

        if (lat < minLat || lat > maxLat || lng < minLng || lng > maxLng) {
            return false;
        }

        return true;
    }
}
