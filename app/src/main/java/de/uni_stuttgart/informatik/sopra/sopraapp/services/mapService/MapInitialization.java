package de.uni_stuttgart.informatik.sopra.sopraapp.services.mapService;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import de.uni_stuttgart.informatik.sopra.sopraapp.model.damage.Damage;
import de.uni_stuttgart.informatik.sopra.sopraapp.model.fields.Field;
import de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapActivity;

/**
 * @author Stefan Zindl
 * @since 2017/12/10
 * <p>
 * Initilizes/ draws the map with the saved fields and damages
 */

public class MapInitialization {

    private MapboxMap mapboxMap;
    private MapView mapView;

    public void loadFields(de.uni_stuttgart.informatik.sopra.sopraapp.view.map.MapFragment mapFragment) {
        mapboxMap = mapFragment.getMapBox();
        mapView = mapFragment.getMapView();

        for (Field mapObject : MapActivity.dataService.getFields()) {
            mapObject.setContext(mapboxMap, mapView);
            mapObject.draw();
        }
        for (Damage mapObject : MapActivity.dataService.getDamages()) {
            mapObject.setContext(mapboxMap, mapView);
            mapObject.draw();
        }
    }
/*
    public static void loadOfflineMap(Context context){
        String pathSDCard = "/data/data/de.uni_stuttgart.informatik.sopra.sopraapp/files/";
        try{
            InputStream in = context.getResources().openRawResource(R.raw.mbgloffline);
            FileOutputStream out = null;
            out = new FileOutputStream(pathSDCard);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    */

}
