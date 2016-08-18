/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import journey.ejb.model.TouchPointListModel;
import journey.ejb.model.TouchPointModel;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

/**
 *
 * @author longnguyen
 */
@Named(value = "journeyCreateView")
@SessionScoped
public class JourneyCreateView implements Serializable {

    /**
     * Creates a new instance of JourneyCreateView
     */
    public JourneyCreateView() {
    }
    
    @Inject
    private TouchPointModel touchPointModel;
    
    @Inject
    private TouchPointListModel touchPointListModel;
    
    private String centerGeoMap = "1.3521, 103.8198";

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }
    
    public void onAddMakerByGeoCode(GeocodeEvent event) {
        List<GeocodeResult> geocodeResultList = event.getResults();
         
        if (geocodeResultList != null && !geocodeResultList.isEmpty()) {
            LatLng center = geocodeResultList.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();
             
            for (GeocodeResult geocodeResult : geocodeResultList) {                
                touchPointListModel.getGeoModel().addOverlay(new Marker(geocodeResult.getLatLng(), geocodeResult.getAddress()));
            }
        }
    }
    
    public void onAddMaker() {
        Marker marker = new Marker(new LatLng(touchPointModel.getLatitude(), touchPointModel.getLongitude()), touchPointModel.getTouchPointDesc());
        touchPointListModel.getGeoModel().addOverlay(marker);
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", 
                "Lat:" + touchPointModel.getLatitude() + ", Lng:" + touchPointModel.getLongitude()));
    }
    
}
