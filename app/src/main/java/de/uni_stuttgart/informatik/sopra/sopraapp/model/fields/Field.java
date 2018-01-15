package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 * <p>
 * FieldModel
 */

public class Field extends MapObject {


    private String fieldType;
    private List<Damage> damages;
    private User owner;
    private User gutachter;

    public Field() {
        damages = new ArrayList<>();
        super.color = Color.GREEN;
        alphaValue = 0.25f;
    }

    @Override
    public void addMarkerPosition(LatLng point) {

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

    @Override
    public boolean addMarker(LatLng point) {

        if (!contains(point)) {
            markerPosition.add(point);

            checkAndReorder(point);

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
            return true;
        }
        return false;
    }



    @Override
    public void setField(Field fieldFromDamage) {

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
