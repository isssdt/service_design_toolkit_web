/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import journey.dto.TouchPointDTO;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import user.dto.FieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
public class DashboardView implements Serializable {

    private Map<String, String> journeyNameMap;
    private MapModel field_researcher_location_map;
    private String centerGeoMap = "1.2971342, 103.7777567";   
    private LineChartModel integrationMapModel;
    private  DefaultDiagramModel snakeModel;

    /**
     * Creates a new instance of DashboardView
     */
    public DashboardView() {
        field_researcher_location_map = new DefaultMapModel();   
        integrationMapModel = new LineChartModel();
    }

    public LineChartModel getIntegrationMapModel() {
        return integrationMapModel;
    }

    public void setIntegrationMapModel(LineChartModel integrationMapModel) {
        this.integrationMapModel = integrationMapModel;
    }

    public Map<String, String> getJourneyNameMap() {
        return journeyNameMap;
    }

    public void setJourneyNameMap(Map<String, String> journeyNameMap) {
        this.journeyNameMap = journeyNameMap;
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

    public DefaultDiagramModel getSnakeModel() {
        return snakeModel;
    }

    public void setSnakeModel(DefaultDiagramModel snakeModel) {
        this.snakeModel = snakeModel;
    }
    
}
