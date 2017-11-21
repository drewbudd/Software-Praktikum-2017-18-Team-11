package de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 */

public class DamageEvent implements OnMapElement{

    private DamageEventArt art;
    private DamageEventStatus currentStatus;
    private User owner;

    public DamageEvent(DamageEventArt art){
        this.currentStatus = DamageEventStatus.CREATED;
        this.art = art;
    }

    public void setOwner(User user) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
