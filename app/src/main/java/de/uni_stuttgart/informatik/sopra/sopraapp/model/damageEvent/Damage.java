package de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
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
    String damageType;

    public Damage() {
        this.currentStatus = DamageEventStatus.CREATED;
    }

    public void setOwner(User user) {
        this.owner = owner;
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
}
