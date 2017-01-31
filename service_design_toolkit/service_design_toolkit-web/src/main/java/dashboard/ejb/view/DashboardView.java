/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
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
    private MapModel touch_point_location_map;
    private MapModel combine_map;
    private String centerGeoMap = "1.2971342, 103.7777567";   
    private CartesianChartModel integrationMapModel;
    private  DefaultDiagramModel snakeModel;
    private HorizontalBarChartModel timeGapDiagram;
    private List<FieldResearcherDTO> fieldResearcherDTOList;
     private Map<String, String> frMap;
     private MapModel polylineModel;

    /**
     * Creates a new instance of DashboardView
     */
    public DashboardView() {
        field_researcher_location_map = new DefaultMapModel();
        touch_point_location_map = new DefaultMapModel();
        combine_map = new DefaultMapModel();
        integrationMapModel = new LineChartModel();
        timeGapDiagram = new HorizontalBarChartModel();
        polylineModel = new DefaultMapModel();
    }

    public MapModel getTouch_point_location_map() {
        return touch_point_location_map;
    }

    public void setTouch_point_location_map(MapModel touch_point_location_map) {
        this.touch_point_location_map = touch_point_location_map;
    }

    public CartesianChartModel getIntegrationMapModel() {
        return integrationMapModel;
    }

    public void setIntegrationMapModel(CartesianChartModel integrationMapModel) {
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
    
    public void setTimeGapDiagram(HorizontalBarChartModel timeGapDiagram) {
    this.timeGapDiagram = timeGapDiagram;
    }

    public HorizontalBarChartModel getTimeGapDiagram() {
        return timeGapDiagram;
    }
    
    public List<FieldResearcherDTO> getFieldResearcherDTOList() {
        return fieldResearcherDTOList;
    }
    
    public void setFieldResearcherDTOList(List<FieldResearcherDTO> fieldResearcherDTOList) {
        this.fieldResearcherDTOList = fieldResearcherDTOList;
    }
    
    public MapModel getCombine_map() {
        return combine_map;
    }

    public void setCombine_map(MapModel combine_map) {
        this.combine_map = combine_map;
    }
    
    public Map<String, String> getFrMap() {
        return frMap;
    }

    public void setFrMap(Map<String, String> frMap) {
        this.frMap = frMap;
    }

    public MapModel getPolylineModel() {
        return polylineModel;
    }

    public void setPolylineModel(MapModel polylineModel) {
        this.polylineModel = polylineModel;
    }
    
    
}
