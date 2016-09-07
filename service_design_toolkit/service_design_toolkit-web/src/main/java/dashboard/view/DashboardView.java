/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.view;

import dashboard.controller.DashboardController;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import journey.dto.JourneyDTO;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import user.dto.FieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "dashboardView")
@RequestScoped
public class DashboardView implements Serializable {

    @Inject
    private DashboardController dashboardController;

    private Map<String, String> journeyNameMap;   
    private List<FieldResearcherDTO> fieldResearcherDTOList;
    private String journeyName;
    private MapModel geoModel;
    private String centerGeoMap = "1.3521, 103.8198";

    /**
     * Creates a new instance of DashboardView
     */
    public DashboardView() {
    }

    @PostConstruct
    public void init() {
        journeyNameMap = new HashMap<>();
        List<JourneyDTO> journeyDTOList = dashboardController.getActiveJourneyList();
        for (JourneyDTO journeyDTO : journeyDTOList) {
            journeyNameMap.put(journeyDTO.getJourneyName(), journeyDTO.getJourneyName());
        }
        geoModel = new DefaultMapModel();
    }

    public void onJourneyChange() {                        
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(journeyName);
        fieldResearcherDTOList = dashboardController.getRegisteredFieldResearchersByJourneyName(journeyDTO);

        for (FieldResearcherDTO fieldResearcherDTO : fieldResearcherDTOList) {
            Marker marker = new Marker(new LatLng(Double.parseDouble(fieldResearcherDTO.getCurrentLatitude()), 
                    Double.parseDouble(fieldResearcherDTO.getCurrentLongitude())), fieldResearcherDTO.getSdtUserDTO().getUsername());
            getGeoModel().addOverlay(marker);
            setCenterGeoMap(fieldResearcherDTO.getCurrentLatitude() + "," + fieldResearcherDTO.getCurrentLongitude());
        }
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }    

    public Map<String, String> getJourneyNameMap() {
        return journeyNameMap;
    }

    public void setJourneyNameMap(Map<String, String> journeyNameMap) {
        this.journeyNameMap = journeyNameMap;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public List<FieldResearcherDTO> getFieldResearcherDTOList() {
        return fieldResearcherDTOList;
    }

    public void setFieldResearcherDTOList(List<FieldResearcherDTO> fieldResearcherDTOList) {
        this.fieldResearcherDTOList = fieldResearcherDTOList;
    }
}
