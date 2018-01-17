package de.uni_stuttgart.informatik.sopra.sopraapp.model.damage;

import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem.UserRole;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Represents a Damage.
 */

public class Damage extends MapObject{

    private DamageEventStatus currentStatus = DamageEventStatus.CREATED;
    private User owner;
    private transient Field field;
    private List<Integer> fieldIds = new ArrayList<>();
    String damageType ="";

    public Damage() {
        this.currentStatus = DamageEventStatus.CREATED;
        super.color = Color.RED;
        owner = new User(UserRole.GUTACHTER);
        alphaValue = 0.5f;
    }

    @Override
    public void setField(Field field){
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

    public Field getField() {
        return field;
    }

    /**
     * adds a LatLng to this damage for the polygon
     * @param point
     */
    @Override
    public void addMarkerPosition(LatLng point) {

    }


    public List<Integer> getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(List<Integer> fieldIds) {
        this.fieldIds = fieldIds;
    }

    public void addFieldId(int id){
        this.fieldIds.add(id);
    }
}
