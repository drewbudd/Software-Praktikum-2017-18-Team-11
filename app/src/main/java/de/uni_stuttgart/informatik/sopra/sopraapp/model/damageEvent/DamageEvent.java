package de.uni_stuttgart.informatik.sopra.sopraapp.model.damageEvent;

import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 */

public class DamageEvent implements OnMapElement{

    private DamageEventArt art;
    private DamageEventStatus currentStatus;

    public DamageEvent(DamageEventArt art){
        this.currentStatus = DamageEventStatus.CREATED;
        this.art = art;
    }
}
