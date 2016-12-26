/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.view;

import dashboard.ejb.controller.DashboardController;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import journey.dto.TouchPointDTO;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
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
    private List<TouchPointDTO> touchPointDTOList;
    private String journeyName;
    private MapModel field_researcher_location_map;
    private String centerGeoMap = "1.2971342, 103.7777567";
    private DashboardModel dashboardModel;
    
     private LineChartModel lineModel1;
    
    /**
     * Creates a new instance of DashboardView
     */
    public DashboardView() {
    }

    @PostConstruct
    public void init() {        
        field_researcher_location_map = new DefaultMapModel();
         lineModel1=new LineChartModel();
        //initizalize dashboard
        dashboardModel = new DefaultDashboardModel();
        
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();        
         
        column1.addWidget("field_researcher_location");        
        column2.addWidget("integrated_view");
        
        dashboardModel.addColumn(column1);
        dashboardModel.addColumn(column2);        
    }

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public void setDashboardModel(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public Map<String, String> getJourneyNameMap() {
        return journeyNameMap;
    }

    public void setJourneyNameMap(Map<String, String> journeyNameMap) {
        this.journeyNameMap = journeyNameMap;
    }

    public List<FieldResearcherDTO> getFieldResearcherDTOList() {
        return fieldResearcherDTOList;
    }

    public void setFieldResearcherDTOList(List<FieldResearcherDTO> fieldResearcherDTOList) {
        this.fieldResearcherDTOList = fieldResearcherDTOList;
    }

    public List<TouchPointDTO> getTouchPointDTOList() {
        return touchPointDTOList;
    }

    public void setTouchPointDTOList(List<TouchPointDTO> touchPointDTOList) {
        this.touchPointDTOList = touchPointDTOList;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public MapModel getField_researcher_location_map() {
        return field_researcher_location_map;
    }

    public void setField_researcher_location_map(MapModel field_researcher_location_map) {
        this.field_researcher_location_map = field_researcher_location_map;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    } 

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }
    
}
