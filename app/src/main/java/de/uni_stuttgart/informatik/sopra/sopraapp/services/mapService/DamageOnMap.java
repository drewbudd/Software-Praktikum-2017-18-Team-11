package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public interface DamageOnMap {


    void createNewDamage(Damage damage);

    void saveNewDamage(Damage damage);

    void deleteDamage(Damage damage);

    void modifyDamage(Damage damage);

    void drawDamage(Damage damage);
}
