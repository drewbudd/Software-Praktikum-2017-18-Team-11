package de.uni_stuttgart.informatik.sopra.sopraapp.model;

import android.graphics.Color;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/12/11
 */

public abstract class MapObject implements IMapObject{


    protected List<LatLng> markerPosition = new ArrayList<>();
    protected int color;
    protected double minLat;
    protected double maxLat;
    protected double minLng;
    protected double maxLng;
    protected float alphaValue = 0.1f;

    protected transient MapboxMap mapboxMap;
    protected transient MapView mapView;
    protected int minMarkers = 3;

    public MapObject(){

        minLat = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
        minLng = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
    }

    public void setContext(MapboxMap map, MapView mapView){
        mapboxMap = map;
        this.mapView = mapView;
    }

    public abstract void addMarkerPosition(LatLng point);

    public List<LatLng> getMarkerPositions(){
        return markerPosition;
    }

    /**
     * checks of the point is within this object
     * @param point
     * @return
     */
    public boolean contains(LatLng point) {
        int delta = 1000;
        double lat = point.getLatitude();
        double lng = point.getLongitude();

        // a quick check to see if a point lies within a rectangle containing the polygon allowing most inquiries to be quickly answered
        if (lat < minLat || lat > maxLat || lng < minLng || lng > maxLng) {
            return false;
        }

        // IF THIS METHOD DOESN'T WORK, COMMENT OUT FROM THIS POINT UNTIL return true;

/*
        // prepare points
        long roundLat = Math.round(lat*delta);
        long roundLng = Math.round(lng*delta);

        int latCrosses = 0;
        int lngCrosses = 0;


        // Check to see how many edges get crossed on the latitude axis
        for (long i = (Math.round(minLat * delta)); i <= roundLat; i++) {
            for (int j = 0; j < markerPosition.size()-1; j++) {
                LatLng point1 = markerPosition.get(j);
                LatLng point2 = markerPosition.get(j+1);
                long point1Lat = Math.round(point1.getLatitude()*delta);
                long point1Lng = Math.round(point1.getLongitude()*delta);
                long point2Lat = Math.round(point2.getLatitude()*delta);
                long point2Lng = Math.round(point2.getLongitude()*delta);

                long dLat1 = i - point1Lat;
                long dLng1 = roundLng - point1Lng;

                long dLat2 = point2Lat - point1Lat;
                long dLng2 = point2Lng - point1Lng;

                long cross = dLat1 * dLng2 - dLng1 * dLat2;
                if (cross == 0) {
                    if (Math.abs(dLat2) > Math.abs(dLng2)){
                        if (dLat2 > 0 && point1Lat <= i && i <= point2Lat) {
                            latCrosses++;
                            break;
                        } else if (dLat2 <= 0 && point2Lat <= i && i <= point1Lat) {
                            latCrosses++;
                            break;
                        }
                    } else {
                        if (dLng2 > 0 && point1Lng <= roundLng && roundLng <= point2Lng) {
                            latCrosses++;
                            break;
                        } else if (dLng2 <= 0 && point2Lng <= roundLng && roundLng <= point1Lng) {
                            latCrosses++;
                            break;
                        }
                    }
                }
            }
        }

        // in the case that an uneven number of lines are crossed, the point isn't within the polygon
        if (latCrosses % 2 == 0) {
            return false;
        }

        // check how many edges get crossed on the longitude axis
        for (long i = (Math.round(minLng * delta)); i <= roundLng; i++) {
            for (int j = 0; j < markerPosition.size()-1; j++) {
                LatLng point1 = markerPosition.get(j);
                LatLng point2 = markerPosition.get(j+1);
                long point1Lat = Math.round(point1.getLatitude()*delta);
                long point1Lng = Math.round(point1.getLongitude()*delta);
                long point2Lat = Math.round(point2.getLatitude()*delta);
                long point2Lng = Math.round(point2.getLongitude()*delta);

                long dLat1 = roundLat - point1Lat;
                long dLng1 = i - point1Lng;

                long dLat2 = point2Lat - point1Lat;
                long dLng2 = point2Lng - point1Lng;

                long cross = dLat1 * dLng2 - dLng1 * dLat2;
                if (cross == 0) {
                    if (Math.abs(dLat2) > Math.abs(dLng2)){
                        if (dLat2 > 0 && point1Lat <= roundLat && roundLat <= point2Lat) {
                            lngCrosses++;
                            break;
                        } else if (dLat2 <= 0 && point2Lat <= roundLat && roundLat <= point1Lat) {
                            lngCrosses++;
                            break;
                        }
                    } else {
                        if (dLng2 > 0 && point1Lng <= i && i <= point2Lng) {
                            lngCrosses++;
                            break;
                        } else if (dLng2 <= 0 && point2Lng <= i && i <= point1Lng) {
                            lngCrosses++;
                            break;
                        }
                    }
                }
            }
        }

        // in the case that an uneven number of lines are crossed, the point isn't within the polygon
        if (lngCrosses % 2 == 0) {
            return false;
        }
*/
        // all checks passed, the point lies within the polygon
        return true;
    }


    /**
     * updates this element
     * @param updatedField
     */
    @Override
    public void modify(OnMapElement updatedField) {

    }

    /**
     * draws the polygon
     */
    @Override
    public void draw() {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.addAll(markerPosition);
        Polygon newPolygon = mapboxMap.addPolygon(polygonOptions);
        newPolygon.setFillColor(color);
        newPolygon.setAlpha(alphaValue);
        mapView.refreshDrawableState();
    }

    @Override
    public void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setPosition(point);
        mapboxMap.addMarker(markerOptions);
    }

    @Override
    public abstract boolean addMarker(LatLng point);

    // checks to see if point q is co-linear to p and r, helper function for addMarker
    private boolean onSegment(LatLng p, LatLng q, LatLng r) {
        if ((q.getLatitude() <= Math.max(p.getLatitude(), r.getLatitude())) && (q.getLatitude() <= Math.min(p.getLatitude(), r.getLatitude())) &&
                (q.getLongitude() <= Math.max(p.getLongitude(), r.getLongitude())) && (q.getLongitude() <= Math.min(p.getLongitude(), r.getLongitude()))) {
            return true;
        }
        return false;
    }

    // checks the orientation of three points, helper function for addMarker
    private int orientation(LatLng p, LatLng q, LatLng r) {
        double val = ((q.getLongitude() - p.getLongitude()) * (r.getLatitude() - q.getLatitude())) -
                     ((q.getLatitude() - p.getLatitude()) * (r.getLongitude() - q.getLongitude()));

        /*if (Math.round(val * 1000) == 0) {
            return 0; // co-linear
        }*/

        if (val > 0) {
            return 1; // clockwise
        } else {
            return 2; // counter-clockwise
        }
    }

    private boolean checkIntersection(LatLng p1, LatLng q1, LatLng p2, LatLng q2) {
        // get orientations
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // check simple case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // check special cases (co-linear)
        // p1, q1 and p2 are co-linear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) { return true; }

        // p1, q1 and q2 are co-linear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) { return true; }

        // p2, q2 and p1 are co-linear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) { return true; }

        // p2, q2 and q1 are co-linear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) { return true; }

        // no intersection
        return false;
    }

    protected void checkAndReorder(LatLng point) {
        for (int i = 1; i < markerPosition.size() - 1; i++) {
            if (checkIntersection(markerPosition.get(0), point, markerPosition.get(i-1), markerPosition.get(i))) {
                //markerPosition.remove(point);
                LatLng temp = point;
                for (int j = i; j < markerPosition.size(); j++) {
                    temp = markerPosition.set(j, temp);
                }
                //markerPosition.add(temp);
                break;
            }

            if (checkIntersection(markerPosition.get(markerPosition.size() - 2), point, markerPosition.get(i-1), markerPosition.get(i))) {
                //markerPosition.remove(point);
                LatLng temp = point;
                for (int j = i; j < markerPosition.size(); j++) {
                    temp = markerPosition.set(j, temp);
                }
                //markerPosition.add(temp);
                break;
            }
        }
    }

    @Override
    public boolean isDrawReady(){
        return markerPosition.size()>= this.minMarkers;
    }


    public abstract void setField(Field fieldFromDamage);
}
