/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
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
import javax.faces.view.ViewScoped;
import journey.dto.JourneyDTO;
import touchpoint.dto.TouchPointDTO;
import user.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import touchpoint.ejb.business.TouchPointServiceLocal;
import common.visualization.NetworkElement;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
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
    

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    
    
    @PostConstruct
    public void init() {
        dashboardModel = new DashboardModel();
        dashboardView = new DashboardView();
        initDummyIntMapChart();
        initDummyIndExpMapChart();
        initsnakeModel();
        initDummyTimeGapDia();
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
        initDummyIndExpMapChart();
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
    }
    
    public void updateIndExpMapModel(JourneyDTO journeyDTO, FieldResearcherDTO fieldResearcherDTO){
        createIndExpMapModelLines(journeyDTO, fieldResearcherDTO);
        dashboardView.getIndExpMapModel().setTitle(fieldResearcherDTO.getSdtUserDTO().getUsername());
        dashboardView.getIndExpMapModel().setLegendPosition("ne");
        dashboardView.getIndExpMapModel().getAxes().put(AxisType.X, new CategoryAxis(ConstantValues.CHART_INTEGRATION_X_AXIS));
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setLabel(ConstantValues.CHART_INTEGRATION_Y_AXIS);
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setMin(0);
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setMax(5);
        dashboardView.getIndExpMapModel().getAxis(AxisType.Y).setTickInterval("1"); 
        dashboardView.getIndExpMapModel().setShowDatatip(false);
        dashboardView.getIndExpMapModel().setMouseoverHighlight(true);
        dashboardView.getIndExpMapModel().setShowPointLabels(false);
    
    }
    
    public void createIndExpMapModelLines(JourneyDTO journeyDTO, FieldResearcherDTO fieldResearcherDTO) {
        dashboardView.getIndExpMapModel().clear();
        TouchPointFieldResearcherListDTO touchPointFieldResearcherDTOList = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);

        //if there is no research work, initialize the empty graph
        if (null == touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()
                || touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().isEmpty()) {
            initDummyIndExpMapChart();
            return;
        }
        Map<String, ChartSeries> chartSeriesForFieldResearcher = new HashMap<>();

        for (TouchPointFieldResearcherDTO touchPointFieldResearcherDTO : touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList()) {
            if ((dashboardModel.getFieldResearcherName()).equals(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername())){
                ChartSeries chartSeries = chartSeriesForFieldResearcher.get(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
                if (null == chartSeries) {
                    chartSeries = new ChartSeries();
                    chartSeriesForFieldResearcher.put(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername(), chartSeries);
                }
                chartSeries.setLabel(touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
                chartSeries.set(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc(),
                    Integer.parseInt(touchPointFieldResearcherDTO.getRatingDTO().getValue()));
            }    
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
        dashboardView.getIntegrationMapModel().getAxes().put(AxisType.X, new CategoryAxis(ConstantValues.CHART_INTEGRATION_X_AXIS));
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setLabel(ConstantValues.CHART_INTEGRATION_Y_AXIS);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setMin(0);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setMax(5);
        dashboardView.getIntegrationMapModel().getAxis(AxisType.Y).setTickInterval("1");  
        dashboardView.getIntegrationMapModel().setShowDatatip(false);
        dashboardView.getIntegrationMapModel().setMouseoverHighlight(true);
        dashboardView.getIntegrationMapModel().setShowPointLabels(false);
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
        for (int i = 0; i<tplist.size(); i++){
            cs = new ChartSeries();
            tgdia = new HorizontalBarChartModel();
            cs.set("Expected",tplist.get(i).getDuration());
            tgdia.setTitle(tplist.get(i).getTouchPointDesc());
            for (int j=0; j<touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().size(); j++){
                if (touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getTouchpointDTO().getTouchPointDesc().equals(tplist.get(i).getTouchPointDesc())){   
                    if (touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getDuration()==null){
                        System.out.println("here");
                        b1=0;  
                    }else {
                        b1 = touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getConvertedToExepectedDuration();
                    }
                }
                cs.set(touchPointFieldResearcherDTOList.getTouchPointFieldResearcherDTOList().get(j).getFieldResearcherDTO().getSdtUserDTO().getUsername()
                        ,b1);
                }            
            tgdia.setShowDatatip(true);
            tgdia.getAxis(AxisType.X).setLabel(tplist.get(i).getMasterDataDTO().getDataValue());
            tgdia.setMouseoverHighlight(true);
            tgdia.addSeries(cs);
            tgdiaList.add(tgdia);
            }
        dashboardView.setTimeGapDiagrams(tgdiaList);
        for(int k=0; k<dashboardView.getTimeGapDiagrams().size(); k++){
            System.out.println("hello" +dashboardView.getTimeGapDiagrams().get(k).getTitle());
        }
        
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
        chartSeries.set(ConstantValues.CHART_DUMMY_NAME, 0);
        chartSeries.setLabel(ConstantValues.CHART_DUMMY_NAME);
        dashboardView.getIntegrationMapModel().addSeries(chartSeries);
        dashboardView.getIndExpMapModel().addSeries(chartSeries);
    }
    
    private void initDummyIndExpMapChart() {
    ChartSeries chartSeries = new ChartSeries();
    chartSeries.set(ConstantValues.CHART_DUMMY_NAME, 0);
    chartSeries.setLabel(ConstantValues.CHART_DUMMY_NAME);
    dashboardView.getIndExpMapModel().addSeries(chartSeries);
    }
    
    private void initDummyTimeGapDia() {
       ChartSeries chartSeries = new ChartSeries();
        chartSeries.set(ConstantValues.CHART_DUMMY_NAME, 0);
       chartSeries.setLabel(ConstantValues.CHART_DUMMY_NAME);
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
        System.out.println("inside updateSnakeMap ");
        dashboardView.getSnakeModel().clear();
        List<TouchPointDTO> touchPointList=getTouchPointList(journeyDTO);
        System.out.println("touchpointlist size "+touchPointList.size()+"for journey"+journeyDTO.getJourneyName());
    // initsnakeModel();
      for (TouchPointDTO touchPoint:touchPointList)
      {
          System.out.println("touchpoint"+touchPoint.getTouchPointDesc());
          addElement(touchPoint);
      }
    }
    
    
     public void addElement(TouchPointDTO touchPoint) {        
        Element touch = new Element(touchPoint);
        if(touchPoint.getNo_like() == null)
                {
                    touchPoint.setNo_like(0);
                }
        if(touchPoint.getNo_dislike() == null)
                {
                    touchPoint.setNo_dislike(0);
                }
        if(touchPoint.getNo_neutral()== null)
                {
                    touchPoint.setNo_neutral(0);
                }
        touch.setDraggable(false);
        touch.setStyleClass("ui-diagram-crumbs");
        dashboardView.getSnakeModel().addElement(touch);
        System.out.println("size "+dashboardView.getSnakeModel().getElements().size());
     }
       public  void  onFRChange()
      {
          JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(dashboardModel.getJourneyName());
           FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
           SdtUserDTO sdtUserDTO =new SdtUserDTO();
           sdtUserDTO.setUsername(dashboardModel.getFieldResearcherName());
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
          System.out.println("journey"+journeyDTO.getJourneyName());
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
       
         System.out.println("updatePolylines");
          //journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
          Polyline polyline = new Polyline();
          System.out.println("size"+getTouchPointList(journeyDTO).size());
          for(TouchPointDTO touchPointDTO:getTouchPointList(journeyDTO)){
        //Shared coordinates
              System.out.println("location "+touchPointDTO.getLatitude()+touchPointDTO.getLongitude());
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
