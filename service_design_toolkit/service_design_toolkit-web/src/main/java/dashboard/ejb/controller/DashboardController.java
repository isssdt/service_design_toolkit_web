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

    @PostConstruct
    public void init() {
        dashboardModel = new DashboardModel();
        dashboardView = new DashboardView();
        initDummyChart();
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

        //updateFieldResearcherLocationMap(journeyDTO);
        updateIntegrationMap(journeyDTO);
        updateSnakeMap(journeyDTO);
        updateFeildResearcherList(journeyDTO);
        updateCombineMap(journeyDTO);
        //updateTouchPointLocationMap(journeyDTO);
         HashMap<String, String> frNameMap = new HashMap<>();
         List<FieldResearcherDTO> frDTOList = dashboardView.getFieldResearcherDTOList();
         for (FieldResearcherDTO frDTO : frDTOList) {
             frNameMap.put(frDTO.getSdtUserDTO().getUsername(),frDTO.getSdtUserDTO().getUsername());
         }
        System.out.println("size"+frNameMap.size());
        dashboardView.setFrMap(frNameMap);
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
    
    private void initDummyTimeGapDia() {
        ChartSeries chartSeries = new ChartSeries();
        chartSeries.set(ConstantValues.CHART_DUMMY_NAME, 0);
        chartSeries.setLabel(ConstantValues.CHART_DUMMY_NAME);
        dashboardView.getTimeGapDiagram().addSeries(chartSeries);
    }
    
    private void initsnakeModel() {
       DefaultDiagramModel model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        
        Element start;
        TouchPointDTO startTouch=new TouchPointDTO();
        startTouch.setTouchPointDesc("start");
        start = new Element(startTouch, "6em", "2em");
        
       
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        start.setDraggable(false);
        start.setStyleClass("ui-start-element");

        model.addElement(start);
        dashboardView.setSnakeModel(model);
        
    }

    
    private void updateSnakeMap(JourneyDTO journeyDTO) {
            System.out.println("inside updateSnakeMap ");
       
       dashboardView.getSnakeModel().clear();
       
      List<TouchPointDTO> touchPointList=getTouchPointList(journeyDTO);
     System.out.println("touchpointlist size "+touchPointList.size()+"for journey"+journeyDTO.getJourneyName());
     initsnakeModel();
      for (TouchPointDTO touchPoint:touchPointList)
      {
          System.out.println("touchpoint"+touchPoint.getTouchPointDesc());
          addElement(touchPoint);
      }
    }
    
    
     public void addElement(TouchPointDTO touchPoint) {        
        String X, Y, X1 = null, Y1;
        int a, b;   
        X = dashboardView.getSnakeModel().getElements().get(dashboardView.getSnakeModel().getElements().size() - 1).getX();
        Y = dashboardView.getSnakeModel().getElements().get(dashboardView.getSnakeModel().getElements().size() - 1).getY();
        System.out.println("x" + X);
        System.out.println("Y" + Y);
        a = Integer.parseInt(X.split("em")[0]);
        b = Integer.parseInt(Y.split("em")[0]);

        if (a < 40) {
            a = a + 20;
        } else {
            System.out.println("a>60");
            a = 6;
            b = b + 10;
        }

        X1 = a + "em";
        Y1 = b + "em";

        Element touch = new Element(touchPoint, X1, Y1);
        //Element touch = new Element(new NetworkElement(touchPoint.getTouchPointDesc(),touchPoint.getChannelDTO().getChannelName(), touchPoint.getChannelDescription()), X1, Y1);
        touch.setDraggable(false);
        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        dashboardView.getSnakeModel().addElement(touch);

        int size = dashboardView.getSnakeModel().getElements().size();

        if (size == 2) {
            dashboardView.getSnakeModel().connect(createConnection(dashboardView.getSnakeModel().getElements().get(0).getEndPoints().get(0), 
                    touch.getEndPoints().get(0), null));
        } else {
            dashboardView.getSnakeModel().connect(createConnection(dashboardView.getSnakeModel().getElements().get(size - 2).getEndPoints().get(1), 
                    touch.getEndPoints().get(0), null));
        }

    }
      private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
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
    

}
