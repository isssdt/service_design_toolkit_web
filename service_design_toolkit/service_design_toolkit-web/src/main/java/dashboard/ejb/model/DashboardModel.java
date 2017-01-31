/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.model;

import java.io.Serializable;

/**
 *
 * @author Leon
 */

public class DashboardModel implements Serializable {
    private String journeyName;

    /**
     * Creates a new instance of DashboardModel
     */
      private String fieldResearcherName;

    public String getFieldResearcherName() {
        return fieldResearcherName;
    }

    public void setFieldResearcherName(String fieldResearcherName) {
        this.fieldResearcherName = fieldResearcherName;
    }
    public DashboardModel() {
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }
    
}
