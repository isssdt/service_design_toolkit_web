/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import java.awt.event.ActionEvent;
import javax.inject.Named;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Inject 
    private JourneyController journeyController;
    
    private String centerGeoMap = "1.3521, 103.8198";
    private Date currentDate = new Date();
    private Map<String, String> channelmap; 

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
    
    @PostConstruct
    public void init() {        
        channelmap = new HashMap<>();
        List<ChannelDTO> channelListDTO = journeyController.getChannelList();
        for (ChannelDTO channelDTO : channelListDTO) {
            channelmap.put(channelDTO.getChannelName(), channelDTO.getChannelName());
        }
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
        Marker marker = new Marker(new LatLng(touchPointModel.getTouchpointLatitude(), touchPointModel.getTouchpointLongitude()), touchPointModel.getTouchPointDesc());
        touchPointListModel.getGeoModel().addOverlay(marker);
        touchPointListModel.setNo_of_touch_point(touchPointListModel.getGeoModel().getMarkers().size());
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", 
                "Lat:" + touchPointModel.getTouchpointLatitude() + ", Lng:" + touchPointModel.getTouchpointLongitude()));
    }
   
    public TouchPointListModel pressOK(ActionEvent pressOK) {
        touchPointListModel.getTouchPointListModel().add(touchPointModel.createCopy());
        for (int i = 0; i <touchPointListModel.getTouchPointListModel().size(); i++) {
            System.out.println(touchPointListModel.getTouchPointListModel().get(i).getTouchPointChannel());
        }
        return touchPointListModel;
        }
    
    
}