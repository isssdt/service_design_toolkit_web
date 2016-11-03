/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import journey.dto.ChannelDTO;
import journey.ejb.controller.JourneyController;
import journey.ejb.model.JourneyModel;
import journey.ejb.model.TouchPointListModel;
import journey.ejb.model.TouchPointModel;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;


/**
 *
 * @author Manish
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
    @Inject 
    private JourneyController journeyController;
    
    private String centerGeoMap = "1.3521, 103.8198";
    private Date currentDate = new Date();
    private Map<String, String> channelmap; 
    private MapModel geoModel;
    private String message;
    
    @PostConstruct
    public void init() {      
        geoModel = new DefaultMapModel();
        channelmap = new HashMap<>();
        List<ChannelDTO> channelListDTO = journeyController.getChannelList();
        for (ChannelDTO channelDTO : channelListDTO) {
            channelmap.put(channelDTO.getChannelName(), channelDTO.getChannelName());
        }
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

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

    public JourneyController getJourneyController() {
        return journeyController;
    }

    public void setJourneyController(JourneyController journeyController) {
        this.journeyController = journeyController;
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

    public Map<String, String> getChannelmap() {
        return channelmap;
    }

    public void setChannelmap(Map<String, String> channelmap) {
        this.channelmap = channelmap;
    }
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public TouchPointListModel pressOK() {
        touchPointListModel.getTouchPointListModel().add(touchPointModel.createCopy());
        return touchPointListModel;
        }
    
//    public void onGeocode(GeocodeEvent event) {
//        System.out.println("hi there");
//        List<GeocodeResult> results = event.getResults();
//         
//        if (results != null && !results.isEmpty()) {
//            LatLng center = results.get(0).getLatLng();
//            centerGeoMap = center.getLat() + "," + center.getLng();
//             
//            for (int i = 0; i < results.size(); i++) {
//                GeocodeResult result = results.get(i);
//                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
//            }
//        }
//    }
    
   public void onAddMakerByGeoCode(GeocodeEvent event) {
        List<GeocodeResult> geocodeResultList = event.getResults();
         
        if (geocodeResultList != null && !geocodeResultList.isEmpty()) {
             double lat = geocodeResultList.get(0).getLatLng().getLat();
             double lng = geocodeResultList.get(0).getLatLng().getLng();

            centerGeoMap = lat + "," + lng;
            touchPointModel.setTouchpointLatitude(lat);
            touchPointModel.setTouchpointLongitude(lng); 
             System.out.println("Latitude :"+touchPointModel.getTouchpointLatitude());
             System.out.println("Longitude :"+touchPointModel.getTouchpointLongitude());
            for (GeocodeResult geocodeResult : geocodeResultList) {                
                touchPointListModel.getGeoModel().addOverlay(new Marker(geocodeResult.getLatLng(), geocodeResult.getAddress()));                
            }
            touchPointListModel.setNo_of_touch_point(touchPointListModel.getGeoModel().getMarkers().size());            
        }   
    }
    
    public void onAddMaker() {
        Marker marker = new Marker(new LatLng(touchPointModel.getTouchpointLatitude(), touchPointModel.getTouchpointLongitude()), touchPointModel.getChannelDesc());
        touchPointListModel.getGeoModel().addOverlay(marker);
        touchPointListModel.setNo_of_touch_point(touchPointListModel.getGeoModel().getMarkers().size());
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", 
                "Lat:" + touchPointModel.getTouchpointLatitude() + ", Lng:" + touchPointModel.getTouchpointLongitude()));
    }
    
    public void createJourney(){
            try {
                Integer journeyId = journeyController.createJourney();
                System.out.println("Journey ID: "+journeyId);
                if(journeyId != null)
                   message = "Journey has been created!";
                else
                   message = "Journey cannot be created!";
                System.out.println("Message : "+message);
            } catch (AppException | CustomReasonPhraseException ex) {
                Logger.getLogger(JourneyCreateView.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
    
    public void goHome() {
         try {
            FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("http://localhost:9090/service_design_toolkit-web/templates/common/content.xhtml");
         } catch (IOException e) {
             Logger.getLogger(JourneyCreateView.class.getName()).log(Level.SEVERE, null,e);
         }  
    }
}