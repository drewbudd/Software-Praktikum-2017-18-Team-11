package de.uni_stuttgart.informatik.sopra.sopraapp.services;

import java.io.IOException;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public interface IDataService {

    void addField(Field damage);
    void loadFields();

    void saveFields () throws IOException;
    List<Field> getFields();
}
