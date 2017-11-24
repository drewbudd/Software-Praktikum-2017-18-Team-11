package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;

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

}
