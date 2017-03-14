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
import org.primefaces.model.map.Circle;
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
        GeoMapView geoMapView = (GeoMapView) view;
        List<GeocodeResult> geocodeResultList = ((GeocodeEvent) event).getResults();
        String radius = geoMapView.getRadius();
        
        Circle circle = new Circle(geocodeResultList.get(0).getLatLng(), Double.parseDouble(radius));
        circle.setStrokeColor("#d93c3c");
        circle.setFillColor("#d93c3c");
        circle.setFillOpacity(0.5);
        LatLng center = geocodeResultList.get(0).getLatLng();
        geoMapView.setCenterGeoMap(center.getLat() + "," + center.getLng());
        
        geoMapView.getTouchPointLocationModel().getMarkers().clear();
        geoMapView.getTouchPointLocationModel().addOverlay(new Marker(center, geocodeResultList.get(0).getAddress()));
        geoMapView.getTouchPointLocationModel().addOverlay(circle);
        
        geoMapView.getTouchPointDTO().setLatitude(String.valueOf(center.getLat()));
        geoMapView.getTouchPointDTO().setLongitude(String.valueOf(center.getLng()));
        geoMapView.getTouchPointDTO().setChannelDescription(geocodeResultList.get(0).getAddress());
    }

}
