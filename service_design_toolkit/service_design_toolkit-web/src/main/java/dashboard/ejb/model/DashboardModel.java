/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.model;

import java.io.Serializable;
import user.dto.TouchPointFieldResearcherDTO;

/**
 *
 * @author Leon
 */

public class DashboardModel implements Serializable {
    private String journeyName;
    private String fRName;
    private TouchPointFieldResearcherDTO touchPointFieldResearcherDTO;

    public TouchPointFieldResearcherDTO getTouchPointFieldResearcherDTO() {
        return touchPointFieldResearcherDTO;
    }

    public void setTouchPointFieldResearcherDTO(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        this.touchPointFieldResearcherDTO = touchPointFieldResearcherDTO;
    }
    

    public void setfRName(String fRName) {
        this.fRName = fRName;
    }

    public String getfRName() {
        return fRName;
    }
    private String touchPointDesc;
    
    private String latitude;
    private String longitude;

    /**
     * Creates a new instance of DashboardModel
     */
      private String fieldResearcherName;

    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

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
