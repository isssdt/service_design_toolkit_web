/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.action.ActionHandler;
import common.view.AbstractView;
import java.util.List;
import javax.faces.event.FacesEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import touchpoint.ejb.view.GeoMapView;

/**
 *
 * @author longnguyen
 */
public class ACTION_GMAP_TOUCH_POINT_LOCATION_AJAX implements ActionHandler {

    @Override
    public void execute(AbstractView view, FacesEvent event) {
        GeoMapView geoMapView = (GeoMapView)view;
        List<GeocodeResult> geocodeResultList = ((GeocodeEvent)event).getResults();
         
        if (geocodeResultList != null && !geocodeResultList.isEmpty()) {
            LatLng center = geocodeResultList.get(0).getLatLng();
            geoMapView.setCenterGeoMap(center.getLat() + "," + center.getLng());            
             
            for (GeocodeResult geocodeResult : geocodeResultList) {                
                geoMapView.getTouchPointLocationModel().addOverlay(new Marker(geocodeResult.getLatLng(), geocodeResult.getAddress()));
            }
        }
    }
    
}
