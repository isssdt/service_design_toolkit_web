/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.controller;

import common.constant.ConstantValues;
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
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
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
        initDummyChart();
        
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
                    Double.parseDouble(fieldResearcherDTO.getCurrentLongitude())), fieldResearcherDTO.getSdtUserDTO().getUsername(), null, 
                    ConstantValues.MARKER_ICON_FIELD_RESEARCHER);
            dashboardView.getField_researcher_location_map().addOverlay(marker);
        }
    }

    private void updateIntegrationMap(JourneyDTO journeyDTO) {
        //integrate view
        createLineModels(journeyDTO);

        dashboardView.getIntegrationMapModel().setTitle(journeyDTO.getJourneyName());
        dashboardView.getIntegrationMapModel().setLegendPosition("ne");
        dashboardView.getIntegrationMapModel().getAxes().put(AxisType.X, new CategoryAxis(ConstantValues.CHART_INTEGRATION_X_AXIS));
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setLabel(ConstantValues.CHART_INTEGRATION_Y_AXIS);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setMin(0);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setMax(5);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setTickInterval("1");        
    }

    private void createLineModels(JourneyDTO journeyDTO) {
        dashboardView.getIntegrationMapModel().clear();
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);

        //if there is no research work, initialize the empty graph
        if (null == touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()
                || touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().isEmpty()) {
            initDummyChart();
            return;
        }
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);

        Map<String, ChartSeries> chartSeriesForFieldResearcher = new HashMap<>();

        for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
            ChartSeries chartSeries = chartSeriesForFieldResearcher.get(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
            if (null == chartSeries) {
                chartSeries = new ChartSeries();
                chartSeriesForFieldResearcher.put(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername(), chartSeries);
            }
            chartSeries.setLabel(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
            chartSeries.set(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc(),
                    Integer.parseInt(touchPointFieldResearcherDTO.getRatingDTO().getValue()));
        }
        for (Map.Entry<String, ChartSeries> mapChartSeries : chartSeriesForFieldResearcher.entrySet()) {
            dashboardView.getIntegrationMapModel().addSeries(mapChartSeries.getValue());
        }
    }

    //initialize dummy chart when there is no data
    private void initDummyChart() {
        ChartSeries chartSeries = new ChartSeries();
        chartSeries.set(ConstantValues.CHART_DUMMY_NAME, 0);
        chartSeries.setLabel(ConstantValues.CHART_DUMMY_NAME);
        dashboardView.getIntegrationMapModel().addSeries(chartSeries);
    }

}
