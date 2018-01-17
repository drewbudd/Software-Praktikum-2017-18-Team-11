package de.uni_stuttgart.informatik.sopra.sopraapp.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapEditingStatus;

/**
 * @author Stefan Zindl
 * @since 2017/12/11
 * interface for a object on the map
 * eg damage, field
 */

public interface IMapObject extends OnMapElement{


    void modify(OnMapElement updatedField);
    void draw();
    boolean addMarker(LatLng point, MapEditingStatus status);
    void drawMarker(LatLng point);
    public boolean isDrawReady();
}
