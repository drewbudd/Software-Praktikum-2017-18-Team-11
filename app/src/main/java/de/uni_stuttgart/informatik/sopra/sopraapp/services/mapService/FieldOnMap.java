package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import com.mapbox.mapboxsdk.geometry.LatLng;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 */

public interface FieldOnMap {

    void createNewField(Field field);

    void saveNewField();

    void deleteField();

    void modifyField(Field updatedField);

    void drawFieldPolygon();
    boolean addMarkerPosition(LatLng point);

    void drawMarker(LatLng point);
}
