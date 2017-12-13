package de.uni_stuttgart.informatik.sopra.sopraapp.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/12/11
 */

public interface IMapObject extends OnMapElement{


    void modify(OnMapElement updatedField);
    void draw();
    boolean addMarker(LatLng point);
    void drawMarker(LatLng point);
    public boolean isDrawReady();
}
