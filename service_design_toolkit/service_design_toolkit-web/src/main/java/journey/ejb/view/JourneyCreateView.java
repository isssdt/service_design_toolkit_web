/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import journey.ejb.model.JourneyModel;
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
	private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of JourneyCreateView
     */
    public JourneyCreateView() {
    }
    
    @Inject
    private TouchPointModel touchPointModel;
    @Inject
    private TouchPointListModel touchPointListModel;
    @Inject
    private JourneyModel journeyModel;
    
    private String centerGeoMap = "1.3521, 103.8198";
    private Date currentDate = new Date();

    public TouchPointModel getTouchPointModel() {
        return touchPointModel;
    }

    public void setTouchPointModel(TouchPointModel touchPointModel) {
        this.touchPointModel = touchPointModel;
    }

    public TouchPointListModel getTouchPointListModel() {
        return touchPointListModel;
    }

    public void setTouchPointListModel(TouchPointListModel touchPointListModel) {
        this.touchPointListModel = touchPointListModel;
    }

    public JourneyModel getJourneyModel() {
        return journeyModel;
    }

    public void setJourneyModel(JourneyModel journeyModel) {
        this.journeyModel = journeyModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
        
    public void onAddMakerByGeoCode(GeocodeEvent event) {
        List<GeocodeResult> geocodeResultList = event.getResults();
         
        if (geocodeResultList != null && !geocodeResultList.isEmpty()) {
            LatLng center = geocodeResultList.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();
             
            for (GeocodeResult geocodeResult : geocodeResultList) {                
                touchPointListModel.getGeoModel().addOverlay(new Marker(geocodeResult.getLatLng(), geocodeResult.getAddress()));                
            }
            touchPointListModel.setNo_of_touch_point(touchPointListModel.getGeoModel().getMarkers().size());            
        }   
    }
    
    public void onAddMaker() {
        Marker marker = new Marker(new LatLng(touchPointModel.getLatitude(), touchPointModel.getLongitude()), touchPointModel.getTouchPointDesc());
        touchPointListModel.getGeoModel().addOverlay(marker);
        touchPointListModel.setNo_of_touch_point(touchPointListModel.getGeoModel().getMarkers().size());
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", 
                "Lat:" + touchPointModel.getLatitude() + ", Lng:" + touchPointModel.getLongitude()));
    }
    
    public List<TouchPointModel> pressOK(ActionEvent actionEvent) {
        
        List<TouchPointModel> touchPointList = new ArrayList();   
        System.out.println(touchPointList);
        return touchPointList;
    }
}
