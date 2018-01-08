package de.uni_stuttgart.informatik.sopra.sopraapp.model.permissionSystem;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * Consists all permissions for the user.
 */

public enum Permissions {

    VIEW_OWN_FIELDS,
    VIEW_OWN_DAMAGEEVENTS,

    VIEW_OTHER_FIELDS,
    VIEW_OTHER_DAMAGEEVENTS,


    EDIT_OWN_FIELDS,
    EDIT_OTHER_FIELDS,

    CREATE_OWN_DAMAGEEVENTS,
    CREATE_FOREIGN_DAMAGEEVENTS,
    EDIT_OWN_DAMAGEEVENTS
}
