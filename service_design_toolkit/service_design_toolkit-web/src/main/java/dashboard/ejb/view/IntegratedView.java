/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.view;

/**
 *
 * @author samru
 */

 
import dashboard.ejb.controller.IntegratedController;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.inject.Named;
import journey.dto.JourneyDTO;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;

 
@Named(value ="integratedView")
@RequestScoped
public class IntegratedView implements Serializable {
    
    
  private String journeyName;
  private static final long serialVersionUID = 1L;
  private Map<String, String> journeyNameMap;
  private LineChartModel lineModel1;
  
  
  @PostConstruct
  public void init()
  {
      lineModel1=new LineChartModel();
  }
  
 
    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }
    
    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }
 
    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public Map<String, String> getJourneyNameMap() {
        return journeyNameMap;
    }

    public void setJourneyNameMap(Map<String, String> journeyNameMap) {
        this.journeyNameMap = journeyNameMap;
    }
 
   
     
    
 
}