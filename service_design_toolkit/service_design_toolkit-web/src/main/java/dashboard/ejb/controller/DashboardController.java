/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.controller;

import common.constant.ConstantValues;
import common.dto.ChannelDTO;
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
import journey.dto.JourneyDTO;
import touchpoint.dto.TouchPointDTO;
import user.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.ejb.business.TouchPointServiceLocal;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import static org.jboss.logging.MDC.getMap;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.HorizontalBarChartModel;

import org.primefaces.model.map.Polyline;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

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
    
    private Boolean isGeoJourney;
    
    private Boolean isNonGeoJourney;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
    }

    @PostConstruct
    public void init() {
        dashboardModel = new DashboardModel();
        dashboardView = new DashboardView();
        isGeoJourney = false;
        isNonGeoJourney = false;
        initDummyIntMapChart();
        initDummyIndExpMapChart();
        initsnakeModel();
        initDummyTimeGapDia();
        initDummyServiceGapDia();
        initPolylines();
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
    
    public Boolean getIsGeoJourney() {
        return isGeoJourney;
    }

    public void setIsGeoJourney(Boolean isGeoJourney) {
        this.isGeoJourney = isGeoJourney;
    }
    
    public Boolean getIsNonGeoJourney() {
        return isNonGeoJourney;
    }

    public void setIsNonGeoJourney(Boolean isNonGeoJourney) {
        this.isNonGeoJourney = isNonGeoJourney;
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
        dashboardView.getIndExpMapModel().clear();
        dashboardView.getTimeGapDiagrams().clear();
        dashboardView.getServiceGapDiagram().clear();
        initDummyIndExpMapChart();
        initDummyServiceGapDia();
        updateIntegrationMap(journeyDTO);
        updateSnakeMap(journeyDTO);
        updateFeildResearcherList(journeyDTO);
        updateCombineMap(journeyDTO);
        updateTimeGapDiagram(journeyDTO);
        //updateTouchPointLocationMap(journeyDTO);
         HashMap<String, String> frNameMap = new HashMap<>();
         List<FieldResearcherDTO> frDTOList = dashboardView.getFieldResearcherDTOList();
         for (FieldResearcherDTO frDTO : frDTOList) {
             frNameMap.put(frDTO.getSdtUserDTO().getUsername(),frDTO.getSdtUserDTO().getUsername());
         }
        System.out.println("size"+frNameMap.size());
        dashboardView.setFrMap(frNameMap);
    }
    
    public void onFieldResearcherChange() {
        JourneyDTO journeyDTO = new JourneyDTO();
        SdtUserDTO sdtUserDTO = new SdtUserDTO();
        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
        
        journeyDTO.setJourneyName(dashboardModel.getJourneyName());
        sdtUserDTO.setUsername(dashboardModel.getFieldResearcherName()); 
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
        
        updateIndExpMapModel(journeyDTO, fieldResearcherDTO);
        updateIndServiceGapModel(journeyDTO, fieldResearcherDTO);
        
    }
    
    public void updateIndExpMapModel(JourneyDTO journeyDTO, FieldResearcherDTO fieldResearcherDTO){
        createIndExpMapModelLines(journeyDTO, fieldResearcherDTO);
        dashboardView.getIndExpMapModel().setTitle(fieldResearcherDTO.getSdtUserDTO().getUsername());
        dashboardView.getIndExpMapModel().setLegendPosition("ne");
        dashboardView.getIndExpMapModel().getAxes().put(AxisType.X, new CategoryAxis(" "));
//        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setLabel(ConstantValues.CHART_INTEGRATION_Y_AXIS);
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setMin(1);
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setMax(5);
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setTickInterval("1"); 
        dashboardView.getIndExpMapModel().setShowDatatip(false);
        dashboardView.getIndExpMapModel().setMouseoverHighlight(true);
        dashboardView.getIndExpMapModel().setShowPointLabels(false);
        dashboardView.getIndExpMapModel().setExtender("extender");
    
    }
    
    public void updateIndServiceGapModel(JourneyDTO journeyDTO, FieldResearcherDTO fieldResearcherDTO){
        createIndServiceGapLines(journeyDTO, fieldResearcherDTO);
        dashboardView.getServiceGapDiagram().setTitle(fieldResearcherDTO.getSdtUserDTO().getUsername());
        dashboardView.getServiceGapDiagram().setLegendPosition("ne");
        dashboardView.getServiceGapDiagram().getAxes().put(AxisType.X, new CategoryAxis(" "));
//        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setLabel(ConstantValues.CHART_INTEGRATION_Y_AXIS);
        dashboardView.getServiceGapDiagram().getAxis(AxisType.Y).setMin(1);
        dashboardView.getServiceGapDiagram().getAxis(AxisType.Y).setMax(4);
        dashboardView.getServiceGapDiagram().getAxis(AxisType.Y).setTickInterval("1"); 
        dashboardView.getServiceGapDiagram().setShowDatatip(false);
        dashboardView.getServiceGapDiagram().setMouseoverHighlight(true);
        dashboardView.getServiceGapDiagram().setShowPointLabels(false);
        dashboardView.getServiceGapDiagram().setExtender("extender_ind_ser");
    }
    
    public void createIndExpMapModelLines(JourneyDTO journeyDTO, FieldResearcherDTO fieldResearcherDTO) {
        dashboardView.getIndExpMapModel().clear();
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListByJourneyNameAndUsername(journeyDTO, fieldResearcherDTO.getSdtUserDTO());
        if (null == touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()
                || touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().isEmpty()) {
            initDummyIndExpMapChart();
            return;
        }
        Map<String, ChartSeries> chartSeriesForFieldResearcher = new HashMap<>();
        for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
//            if ((dashboardModel.getFieldResearcherName()).equals(fieldResearcherDTO.getSdtUserDTO().getUsername())){
                ChartSeries chartSeries = chartSeriesForFieldResearcher.get(fieldResearcherDTO.getSdtUserDTO().getUsername());
                if (null == chartSeries) {   
                    chartSeries = new ChartSeries();
                    chartSeriesForFieldResearcher.put(fieldResearcherDTO.getSdtUserDTO().getUsername(), chartSeries);
                }
                chartSeries.setLabel(fieldResearcherDTO.getSdtUserDTO().getUsername());
                chartSeries.set(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc(),
                                Integer.parseInt(touchPointFieldResearcherDTO.getRatingDTO().getValue()));
//            }    
        }
        if (chartSeriesForFieldResearcher.isEmpty()) {
            System.out.println("is Empty");
            initDummyIndExpMapChart();
        }
        else{
            for (Map.Entry<String, ChartSeries> mapChartSeries : chartSeriesForFieldResearcher.entrySet()) {
                dashboardView.getIndExpMapModel().addSeries(mapChartSeries.getValue());
            }
        }
    }
    
    public void createIndServiceGapLines(JourneyDTO journeyDTO, FieldResearcherDTO fieldResearcherDTO) {
        dashboardView.getServiceGapDiagram().clear();
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListByJourneyNameAndUsername(journeyDTO, fieldResearcherDTO.getSdtUserDTO());
        List<TouchPointDTO> touchPointDTOList = touchPointService.getTouchPointListJourney(journeyDTO);
        ChartSeries cs, cs1;
        Map<String, ChartSeries> chartSeriesForFieldResearcher = new HashMap<>();
        cs = new ChartSeries();
        for (TouchPointDTO touchPointDTO : touchPointDTOList) {
                cs.set(touchPointDTO.getTouchPointDesc(),2);
                chartSeriesForFieldResearcher.put("expeted", cs);
                cs.setLabel("expected");
            }
        cs1 = new ChartSeries();
        for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
            if ((dashboardModel.getFieldResearcherName()).equals(fieldResearcherDTO.getSdtUserDTO().getUsername())){
                ChartSeries chartSeries = chartSeriesForFieldResearcher.get(fieldResearcherDTO.getSdtUserDTO().getUsername());
                if (null == chartSeries) {    
                    chartSeriesForFieldResearcher.put(fieldResearcherDTO.getSdtUserDTO().getUsername(), cs1);
                }
                cs1.setLabel(fieldResearcherDTO.getSdtUserDTO().getUsername());
                cs1.set(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc(),3);
            }    
        }
            for (Map.Entry<String, ChartSeries> mapChartSeries : chartSeriesForFieldResearcher.entrySet()) {
                dashboardView.getServiceGapDiagram().addSeries(mapChartSeries.getValue());
            }
    }
    
    private void updateCombineMap(JourneyDTO journeyDTO) {
        dashboardView.getCombine_map().getMarkers().clear();
        updateFieldResearcherLocationMap(journeyDTO);
        updateTouchPointLocationMap(journeyDTO);
    }
    
    private void updateFeildResearcherList(JourneyDTO journeyDTO) {
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);
        
        dashboardView.setFieldResearcherDTOList(fieldResearcherDTOList);
    }

    private void updateFieldResearcherLocationMap(JourneyDTO journeyDTO) {
        List<FieldResearcherDTO> fieldResearcherDTOList = journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);

        dashboardView.getField_researcher_location_map().getMarkers().clear();
        for (FieldResearcherDTO fieldResearcherDTO : fieldResearcherDTOList) {
            Marker marker = new Marker(new LatLng(Double.parseDouble(fieldResearcherDTO.getCurrentLatitude()),
                    Double.parseDouble(fieldResearcherDTO.getCurrentLongitude())), fieldResearcherDTO.getSdtUserDTO().getUsername(), null, 
                    ConstantValues.MARKER_ICON_FIELD_RESEARCHER);
            dashboardView.getCombine_map().addOverlay(marker);
        }
    }
    
    private void updateTouchPointLocationMap(JourneyDTO journeyDTO) {
        List<TouchPointDTO> touchPointDTOList = touchPointService.getTouchPointListJourney(journeyDTO);
        
        dashboardView.getTouch_point_location_map().getMarkers().clear();
        for (TouchPointDTO touchPointDTO : touchPointDTOList) {
            if ("NONE".equals(touchPointDTO.getLatitude())) {
                continue;
            }
            Marker marker = new Marker(new LatLng(Double.parseDouble(touchPointDTO.getLatitude()),
                    Double.parseDouble(touchPointDTO.getLongitude())), touchPointDTO.getTouchPointDesc(), null, 
                    ConstantValues.MARKER_ICON_TOUCH_POINT);
            dashboardView.getCombine_map().addOverlay(marker);
        }
    }

    private void updateIntegrationMap(JourneyDTO journeyDTO) {
        //integrate view
        createLineModels(journeyDTO);
        dashboardView.getIntegrationMapModel().setTitle(journeyDTO.getJourneyName());
        dashboardView.getIntegrationMapModel().setLegendPosition("ne");
        dashboardView.getIntegrationMapModel().getAxes().put(AxisType.X, new CategoryAxis(" "));
//        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setLabel(ConstantValues.CHART_INTEGRATION_Y_AXIS);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setMin(1);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setMax(5);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setTickInterval("1");  
        dashboardView.getIntegrationMapModel().setShowDatatip(false);
        dashboardView.getIntegrationMapModel().setMouseoverHighlight(true);
        dashboardView.getIntegrationMapModel().setShowPointLabels(false);
        dashboardView.getIntegrationMapModel().setExtender("extender");
    }
    
    private void updateTimeGapDiagram(JourneyDTO journeyDTO) {
        createTimeGapDiaLineModels(journeyDTO);
//        dashboardView.getTimeGapDiagrams().setStacked(true);
//        dashboardView.getTimeGapDiagrams().setTitle(journeyDTO.getJourneyName());
//        dashboardView.getTimeGapDiagrams().setLegendPosition("ne");
//        dashboardView.getTimeGapDiagrams().setShowDatatip(false);
//        dashboardView.getTimeGapDiagrams().setMouseoverHighlight(true);
//        dashboardView.getTimeGapDiagrams().setShowPointLabels(false);
    }
    
    private void createTimeGapDiaLineModels(JourneyDTO journeyDTO) {
        List<TouchPointDTO> tplist = getTouchPointList(journeyDTO);
        HorizontalBarChartModel tgdia;
        List<HorizontalBarChartModel> tgdiaList = new ArrayList<HorizontalBarChartModel>();
        ChartSeries cs;
        double b1=0;
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        for (int i=1;i<touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().size();i++){
            System.out.println("inside check" +touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(i).getTouchpointDTO().getTouchPointDesc());
            System.out.println("inside check" +touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(i).getDuration());
            System.out.println("inside check" +touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(i).getConvertedToExepectedDuration()); 
            System.out.println("inside check" +touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(i).getFieldResearcherDTO().getSdtUserDTO().getUsername());          
            
        }
        for (int i = 0; i<tplist.size(); i++){
            cs = new ChartSeries();
            tgdia = new HorizontalBarChartModel();
            cs.set("Expected",tplist.get(i).getDuration());
            tgdia.setBarWidth(10);
            tgdia.setShadow(false);
            tgdia.setTitle(tplist.get(i).getTouchPointDesc());
            for (int j=0; j<touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().size(); j++){
                if (touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getTouchpointDTO().getTouchPointDesc().equals(tplist.get(i).getTouchPointDesc())){   
                    cs.set(touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getFieldResearcherDTO().getSdtUserDTO().getUsername()
                            ,touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getConvertedToExepectedDuration());
                }
            }            
            tgdia.setShowDatatip(true);
            tgdia.getAxis(AxisType.X).setLabel(tplist.get(i).getMasterDataDTO().getDataValue());
            tgdia.setMouseoverHighlight(true);
            tgdia.addSeries(cs);
            tgdiaList.add(tgdia);
            }
        dashboardView.setTimeGapDiagrams(tgdiaList);
    }

    private void createLineModels(JourneyDTO journeyDTO) {
        dashboardView.getIntegrationMapModel().clear();
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);

        //if there is no research work, initialize the empty graph
        if (null == touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()
                || touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().isEmpty()) {
            initDummyIntMapChart();
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
    private void initDummyIntMapChart() {
        ChartSeries chartSeries = new ChartSeries();
        chartSeries.set(" ", 0);
        chartSeries.setLabel(" ");
        dashboardView.getIntegrationMapModel().addSeries(chartSeries);
//        dashboardView.getIndExpMapModel().addSeries(chartSeries);
    }
    
    private void initDummyIndExpMapChart() {
    ChartSeries chartSeries = new ChartSeries();
    chartSeries.set(" ", 0);
    chartSeries.setLabel(" ");
    dashboardView.getIndExpMapModel().setTitle(" ");
    dashboardView.getIndExpMapModel().addSeries(chartSeries);
    }
    
    private void initDummyServiceGapDia(){
        ChartSeries chartSeries = new ChartSeries();
        chartSeries.set(" ", 0);
        chartSeries.setLabel(" ");
        dashboardView.getServiceGapDiagram().setTitle(" ");
        dashboardView.getServiceGapDiagram().addSeries(chartSeries);
    }
    
    private void initDummyTimeGapDia() {
       ChartSeries chartSeries = new ChartSeries();
       chartSeries.set(" ", 0);
       chartSeries.setLabel(" ");
       List<HorizontalBarChartModel> timeGapDiagrams= new ArrayList<HorizontalBarChartModel>();
       HorizontalBarChartModel dummyModel =new HorizontalBarChartModel();
       dummyModel.addSeries(chartSeries);
       timeGapDiagrams.add(dummyModel);
       dashboardView.setTimeGapDiagrams(timeGapDiagrams);
    }
    
    private void initsnakeModel() {
       DefaultDiagramModel model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        Element start;
        TouchPointDTO startTouch=new TouchPointDTO();
        startTouch.setTouchPointDesc("Customer journey map");
        start = new Element(startTouch);
        start.setDraggable(false);
        start.setStyleClass("ui-diagram-crumbs");
        model.addElement(start);
        dashboardView.setSnakeModel(model);        
    }

    private void updateSnakeMap(JourneyDTO journeyDTO) {
        dashboardView.getSnakeModel().clear();
        List<TouchPointDTO> touchPointList=getTouchPointList(journeyDTO);
        for (TouchPointDTO touchPoint:touchPointList){
            addElement(touchPoint);
        }
    }
    
    public void addElement(TouchPointDTO touchPoint) {        
        Element touch = new Element(touchPoint);
        if(touchPoint.getNo_like() == null){
            touchPoint.setNo_like(0);
        }
        if(touchPoint.getNo_dislike() == null){
            touchPoint.setNo_dislike(0);
        }
        if(touchPoint.getNo_neutral()== null){
            touchPoint.setNo_neutral(0);
        }
        touch.setDraggable(false);
        touch.setStyleClass("ui-diagram-crumbs");
        dashboardView.getSnakeModel().addElement(touch);
    }
       
    public  void  onFRChange(){
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(dashboardModel.getJourneyName());
        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
        SdtUserDTO sdtUserDTO =new SdtUserDTO();
        sdtUserDTO.setUsername(dashboardModel.getFieldResearcherName());
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
        updatePolylines( journeyDTO,fieldResearcherDTO);
      }

    private void initPolylines() {          
        //Shared coordinates
        LatLng coord1 = new LatLng(36.879466, 30.667648);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);
      
        //Polyline
        Polyline polyline = new Polyline();
        polyline.getPaths().add(coord1);
        polyline.getPaths().add(coord2);
        polyline.getPaths().add(coord3);
        polyline.getPaths().add(coord4);
          
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
          
        dashboardView.getPolylineModel().addOverlay(polyline);
    }
    
     private void updatePolylines(JourneyDTO journeyDTO ,FieldResearcherDTO fieldResearcherDTO) {
        //journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        Polyline polyline = new Polyline();
        System.out.println("size"+getTouchPointList(journeyDTO).size());
        for(TouchPointDTO touchPointDTO:getTouchPointList(journeyDTO)){
            //Shared coordinates
            LatLng coord = new LatLng(Double.parseDouble(touchPointDTO.getLatitude()),Double.parseDouble(touchPointDTO.getLongitude()));

            //Polyline       
            polyline.getPaths().add(coord);         
            polyline.setStrokeWeight(10);
            polyline.setStrokeColor("#FF9900");
            polyline.setStrokeOpacity(0.7);
        }
        
        LatLng coord1 = new LatLng(36.879466, 30.667648);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);
      
        //Polyline
        Polyline polyline1 = new Polyline();
        polyline1.getPaths().add(coord1);
        polyline1.getPaths().add(coord2);
        polyline1.getPaths().add(coord3);
        polyline1.getPaths().add(coord4);  
        polyline1.setStrokeWeight(10);
        polyline1.setStrokeColor("#FD9900");
        polyline1.setStrokeOpacity(0.7);
        dashboardView.getPolylineModel().addOverlay(polyline);
        dashboardView.getPolylineModel().addOverlay(polyline1);
    }
     
    public void showDialogOnClickTouchPoint() {
        isGeoJourney = false;
            isNonGeoJourney = false;
          System.out.println("hellooo");
         TouchPointDTO touchPointDTOModel =new TouchPointDTO();
          String touchPointDesc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("touchPointDesc");
         
          touchPointDTOModel.setTouchPointDesc(touchPointDesc);
          System.out.println("desc model"+touchPointDTOModel.getTouchPointDesc());
          String latitude = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
         
         touchPointDTOModel.setLatitude(latitude);
          String longitude = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");
     
          touchPointDTOModel.setLongitude(longitude);
          
            String channelName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("channelName");
     ChannelDTO channelDTO=new ChannelDTO();
     channelDTO.setChannelName(channelName);
          touchPointDTOModel.setChannelDTO(channelDTO);
          
            String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("action");
     
          touchPointDTOModel.setAction(action);
         System.out.println("dash"+dashboardModel.getJourneyName());
     
          String jName=dashboardModel.getJourneyName();
          
          if(jName!=null){
         JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(jName);
         
       dashboardModel.setTouchPointDTO(touchPointDTOModel);
        List<TouchPointDTO> touchPointDTOList = touchPointService.getTouchPointListJourney(journeyDTO);
          System.out.println("size "+touchPointDTOList.size()+dashboardModel.getJourneyName());
          dashboardView.getTp_map().getMarkers().clear();
        
        for (TouchPointDTO touchPointDTO : touchPointDTOList) {
            if(!touchPointDTO.getLatitude().equals("NONE")){
               isGeoJourney = true;
               if(touchPointDTO.getTouchPointDesc().equals(dashboardModel.getTouchPointDTO().getTouchPointDesc())){
                
            Marker marker = new Marker(new LatLng(Double.parseDouble(touchPointDTO.getLatitude()),
                    Double.parseDouble(touchPointDTO.getLongitude())), touchPointDTO.getTouchPointDesc(), null, 
                 ConstantValues.MARKER_ICON_TOUCH_POINT_CURRENT);
            dashboardView.getTp_map().addOverlay(marker);
            }else{
                   Marker marker = new Marker(new LatLng(Double.parseDouble(touchPointDTO.getLatitude()),
                           Double.parseDouble(touchPointDTO.getLongitude())), touchPointDTO.getTouchPointDesc(), null,
                           ConstantValues.MARKER_ICON_TOUCH_POINT);
                   dashboardView.getTp_map().addOverlay(marker);
            }
           } 
           if(isGeoJourney!=true) {
               isNonGeoJourney = true;
           }
        }
          }
          
        
}
      public void itemSelect(ItemSelectEvent event) {

        ChartSeries current=dashboardView.getIndExpMapModel().getSeries().get(0);
        System.out.println("current"+current.getData());
        Set<Entry<Object, Number>> mapValues = current.getData().entrySet();
        Entry<Object,Number>[] test = new Entry[mapValues.size()];

        mapValues.toArray(test);
        System.out.println("Key"+test[event.getItemIndex()].getKey());
        System.out.println("Value"+test[event.getItemIndex()].getValue());

      
         JourneyDTO journeyDTO=new JourneyDTO();
         journeyDTO.setJourneyName(dashboardModel.getJourneyName());
        
         SdtUserDTO sdtUserDTO=new SdtUserDTO();
       sdtUserDTO.setUsername(dashboardView.getIndExpMapModel().getSeries().get(0).getLabel());
   
      TouchPointFieldResearcherListDTO list=   journeyService.getTouchPointFiedlResearcherListByJourneyNameAndUsername(journeyDTO, sdtUserDTO);  
          System.out.println("size"+list.getTouchPointFieldResearcherDTOList().size());
for (TouchPointFieldResearcherDTO l:list.getTouchPointFieldResearcherDTOList())
    {
         if( l.getTouchpointDTO().getTouchPointDesc().equals(test[event.getItemIndex()].getKey()))
         {
             System.out.println("ama inside"+l.getRatingDTO().getValue());
             dashboardModel.setTouchPointFieldResearcherDTO(l);
         }
          
    }
          
}
      public void showChartDialog()
      {
          
          System.out.println("showChartDialog");
      }
}
