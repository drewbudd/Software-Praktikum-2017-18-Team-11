package de.uni_stuttgart.informatik.sopra.sopraapp.view.map;

/**
 * @author Stefan Zindl
 * @since 2017/11/25
 */

public enum MapEditingStatus {

    START_CREATE_FIELD_COORDINATES,
    END_CREATE_FIELD_COORDINATES,
    CREATE_FIELD_DONE,
    START_CREATE_DAMAGE_COORDINATES,
    CREATED_DAMAGE_DONE,
    CREATED_FIELD_DONE,
    CREATE_DAMAGE,
    MODIFY_FIELD,
    END_CREATE_DAMAGE_COORDINATES, DEFAULT
}
