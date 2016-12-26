/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.controller;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.utils.Utils;
import dashboard.ejb.model.DashboardModel;
import dashboard.ejb.view.DashboardView;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.ejb.business.TouchPointServiceLocal;
import user.dto.FieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController implements Serializable {

    @EJB
    private JourneyServiceLocal journeyService;
    @EJB
    private TouchPointServiceLocal touchPointService;

    @Inject
    DashboardView dashboardView;
    
    @Inject
    DashboardModel dashboardModel;
    
    private String journeyName;
    
    private Map<String, String> journeyNameMap;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
    }

    @PostConstruct
    public void init() {
        journeyNameMap = new HashMap<>();
        try {
            List<JourneyDTO> journeyDTOList = journeyService.getAllJourney();
            for (JourneyDTO journeyDTO : journeyDTOList) {
                journeyNameMap.put(journeyDTO.getJourneyName(), journeyDTO.getJourneyName());
            }
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
            Utils.postMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null, null);
        }
        
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

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public void setDashboardModel(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    public List<JourneyDTO> getActiveJourneyList() throws AppException, CustomReasonPhraseException {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setIsActive('Y');
        return journeyService.getAllJourney();
    }

    public List<FieldResearcherDTO> getRegisteredFieldResearchersByJourneyName(JourneyDTO journeyDTO) {
        return journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);
    }

    public List<TouchPointDTO> getTouchPointList(JourneyDTO journeyDTO) {
        return touchPointService.getTouchPointListJourney(journeyDTO);
    }

    public void onJourneyChange() {        
        JourneyDTO journeyDTO = new JourneyDTO();        
        journeyDTO.setJourneyName(getJourneyName());    
        
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);

        for (FieldResearcherDTO fieldResearcherDTO : fieldResearcherDTOList) {            
            Marker marker = new Marker(new LatLng(Double.parseDouble(fieldResearcherDTO.getCurrentLatitude()),
                    Double.parseDouble(fieldResearcherDTO.getCurrentLongitude())));
            dashboardView.getField_researcher_location_map().addOverlay(marker);
        }
        
        
        //integrate view
        createLineModels(journeyDTO);

        dashboardView.getLineModel1().setTitle("Integrated Map for "+journeyDTO.getJourneyName()+" journey");
        dashboardView.getLineModel1().setLegendPosition("ne");
        //integratedView.getLineModel1().setShowPointLabels(true);
        dashboardView.getLineModel1().getAxes().put(AxisType.X, new CategoryAxis("Touch Point"));
        Axis yAxis = dashboardView.getLineModel1().getAxis(AxisType.Y);
        yAxis.setLabel("Rating");
        yAxis.setMin(0);
        yAxis.setMax(5);
    }
    
     private void createLineModels(JourneyDTO journeyDTO) {
        System.out.println("inside integrated craete line model");
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);
        
        System.out.println("no of touch point filed researcher"+touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().size());
        System.out.println("no of filed researcher"+fieldResearcherDTOList.size());
        
        for (FieldResearcherDTO f : fieldResearcherDTOList) {
        ChartSeries tFRseries = new ChartSeries();
        tFRseries.setLabel(f.getSdtUserDTO().getUsername());
            for (TouchPointFieldResearcherDTO t : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
                
                if (f.getSdtUserDTO().getUsername().equals(t.getFieldResearcherDTO().getSdtUserDTO().getUsername()) && t.getStatus().equals("DONE")) {
                    initFrSeries(tFRseries,t,journeyDTO);
                   }

            }
             dashboardView.getLineModel1().addSeries(tFRseries);

        }
    }

    private ChartSeries initFrSeries(ChartSeries tFRseries,TouchPointFieldResearcherDTO tPfr,JourneyDTO journeyDTO) {

        System.out.println("initCategoryModel series"+tPfr.getFieldResearcherDTO().getSdtUserDTO().getUsername());
       if (null != tPfr.getRatingDTO().getValue() && !tPfr.getRatingDTO().getValue().isEmpty() && tPfr.getStatus().equals("DONE")) {
            System.out.println("initFrSeries"+tPfr.getRatingDTO().getValue()+"user"+tPfr.getFieldResearcherDTO().getSdtUserDTO().getUsername()+tPfr.getStatus());
           List<TouchPointDTO> touchPointList=touchPointService.getTouchPointListJourney(journeyDTO);
            for(TouchPointDTO t:touchPointList){
                if(t.getTouchPointDesc().equals(tPfr.getTouchpointDTO().getTouchPointDesc()))
            tFRseries.set(t.getTouchPointDesc(), Integer.parseInt(tPfr.getRatingDTO().getValue()));
            }
        }
        else { 
     tFRseries.set(0, 0);
}

        return tFRseries;
    }
}
