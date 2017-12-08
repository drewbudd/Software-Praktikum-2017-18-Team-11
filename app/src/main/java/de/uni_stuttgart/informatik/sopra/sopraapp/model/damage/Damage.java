package de.uni_stuttgart.informatik.sopra.sopraapp.model.damage;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Represents a Damage.
 */

public class Damage implements OnMapElement{

    private DamageEventStatus currentStatus;
    private List<LatLng> markerPosition = new ArrayList<>();
    private User owner;
    private Field field;
    String damageType;

    public Damage(Field field) {
        this.currentStatus = DamageEventStatus.CREATED;
        this.field = field;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public User getOwner() {
        return owner;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void setMarkerPosition(List<LatLng> markerPosition) {
        this.markerPosition = markerPosition;
    }

    public Field getField() {
        return field;
    }

    public List<LatLng> getMarkerPosition() {
        return markerPosition;
    }

}
