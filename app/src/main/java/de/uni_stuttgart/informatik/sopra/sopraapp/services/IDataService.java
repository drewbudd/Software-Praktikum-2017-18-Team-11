package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.io.IOException;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.MapObject;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public interface IDataService {

    /**
     * adds a new field
     *
     * @param field
     */
    void addField(Field field);


    void addDamage(Damage damage);

    /**
     * loads all fields from the storage
     */
    void loadFields();


    void loadDamages();

    /**
     * saves all Fields to the storage
     */
    void saveFields();

    void saveDamages();

    /**
     * returns all loaded field
     *
     * @return
     */
    List<Field> getFields();

    /**
     * returns all Damages
     * not used at the moment
     *
     * @return
     */
    List<Damage> getDamages();

    void deleteField(int fieldId);

    void deleteDamage(int itemId);

    void setDetailDamage(Damage damage);

    Damage getDetailDamage();

    void setFields(List<Field> fields);
}