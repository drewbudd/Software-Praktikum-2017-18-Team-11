package de.uni_stuttgart.informatik.sopra.sopraapp.model.fields;


import android.graphics.Color;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService.OnMapElement;

/**
 * @author Stefan Zindl
 * @since 2017/11/14
 *
 * FieldModel
 */

public class Field implements OnMapElement {


    private String fieldType;
    private List<Damage> damages;
    private List<LatLng> markerPosition = new ArrayList<>();
    private Color color;
    private double minLat;
    private double maxLat;
    private double minLng;
    private double maxLng;
    private User owner;

    public Field() {
        damages = new ArrayList<>();
        minLat = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
        minLng = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
    }

    /**
     * @param damage
     */
    public void addDamage(Damage damage) {
        this.damages.add(damage);
    }


    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public List<Damage> getDamages() {
        return damages;
    }

    public void setDamages(List<Damage> damages) {
        this.damages = damages;
    }

    public List<LatLng> getMarkerPosition() {
        return markerPosition;
    }

    public void setMarkerPosition(List<LatLng> markerPosition) {
        this.markerPosition = markerPosition;

        for (LatLng latLng : markerPosition) {
            double lat = latLng.getLatitude();
            double lng = latLng.getLongitude();

            if (lat < minLat) {
                minLat = lat;
            }

            if (lat > maxLat) {
                maxLat = lat;
            }

            if (lng < minLng) {
                minLng = lng;
            }

            if (lng > maxLng) {
                maxLng = lng;
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

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
}
