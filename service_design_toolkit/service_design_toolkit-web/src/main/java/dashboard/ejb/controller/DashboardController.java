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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
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
@ViewScoped
public class DashboardController implements Serializable {

    @EJB
    private JourneyServiceLocal journeyService;
    @EJB
    private TouchPointServiceLocal touchPointService;

    DashboardView dashboardView;

    DashboardModel dashboardModel;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
    }

    @PostConstruct
    public void init() {
        dashboardModel = new DashboardModel();
        dashboardView = new DashboardView();        
        dashboardView.setLineModel(initLinearModel());
        HashMap<String, String> journeyNameMap = new HashMap<>();
        try {
            List<JourneyDTO> journeyDTOList = journeyService.getAllJourney();            
            for (JourneyDTO journeyDTO : journeyDTOList) {
                journeyNameMap.put(journeyDTO.getJourneyName(), journeyDTO.getJourneyName());
            }
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
            Utils.postMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null, null);
        }
        dashboardView.setJourneyNameMap(journeyNameMap);        
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
        journeyDTO.setJourneyName(dashboardModel.getJourneyName());

        updateFieldResearcherLocationMap(journeyDTO);
        updateIntegrationMap(journeyDTO);
    }
    
    private void updateFieldResearcherLocationMap(JourneyDTO journeyDTO) {
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);

        dashboardView.getField_researcher_location_map().getMarkers().clear();
        for (FieldResearcherDTO fieldResearcherDTO : fieldResearcherDTOList) {
            Marker marker = new Marker(new LatLng(Double.parseDouble(fieldResearcherDTO.getCurrentLatitude()),
                    Double.parseDouble(fieldResearcherDTO.getCurrentLongitude())));
            dashboardView.getField_researcher_location_map().addOverlay(marker);
        }
    }
    
    private void updateIntegrationMap(JourneyDTO journeyDTO) {
        //integrate view
        LineChartModel model = new LineChartModel();
        createLineModels(journeyDTO, model);

        model.setTitle("Integrated Map for " + journeyDTO.getJourneyName() + " journey");
        model.setLegendPosition("ne");
        model.getAxes().put(AxisType.X, new CategoryAxis("Touch Point"));
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Rating");
        yAxis.setMin(0);
        yAxis.setMax(5); 
        yAxis.setTickInterval("1");
     
        dashboardView.setLineModel(model);
    }

    private void createLineModels(JourneyDTO journeyDTO, LineChartModel model) {        
        System.out.println(journeyDTO.getJourneyName());
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        System.out.println(touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().size());
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);       

        for (FieldResearcherDTO f : fieldResearcherDTOList) {
            ChartSeries tFRseries = new ChartSeries();
            tFRseries.setLabel(f.getSdtUserDTO().getUsername());
            for (TouchPointFieldResearcherDTO t : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
                if (f.getSdtUserDTO().getUsername().equals(t.getFieldResearcherDTO().getSdtUserDTO().getUsername()) && t.getStatus().equals("DONE")) {
                    initFrSeries(tFRseries, t, journeyDTO);
                }
            }
            model.addSeries(tFRseries);

        }
    }
    
    

    private ChartSeries initFrSeries(ChartSeries tFRseries, TouchPointFieldResearcherDTO tPfr, JourneyDTO journeyDTO) {

        System.out.println("initCategoryModel series" + tPfr.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        if (null != tPfr.getRatingDTO().getValue() && !tPfr.getRatingDTO().getValue().isEmpty() && tPfr.getStatus().equals("DONE")) {
            System.out.println("initFrSeries" + tPfr.getRatingDTO().getValue() + "user" + tPfr.getFieldResearcherDTO().getSdtUserDTO().getUsername() + tPfr.getStatus());
            List<TouchPointDTO> touchPointList = touchPointService.getTouchPointListJourney(journeyDTO);
            for (TouchPointDTO t : touchPointList) {
                if (t.getTouchPointDesc().equals(tPfr.getTouchpointDTO().getTouchPointDesc())) {
                    tFRseries.set(t.getTouchPointDesc(), Integer.parseInt(tPfr.getRatingDTO().getValue()));
                }
            }
        } else {
            tFRseries.set(0, 0);
        }

        return tFRseries;
    }
    
    //dummy method - need to be removed
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    }
     
}
