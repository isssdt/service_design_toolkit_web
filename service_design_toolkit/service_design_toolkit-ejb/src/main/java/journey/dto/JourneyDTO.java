/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import java.util.List;

/**
 *
 * @author longnguyen
 */

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JourneyDTO {

    
    private String journeyName;
    private Integer noOfFieldResearcher;
    private Character isActive;
    private Date startDate;
    private Date endDate;
    private Character canBeRegistered;
    private String description;
    private List<TouchPointDTO> touchPointDTOList = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getCanBeRegistered() {
        return canBeRegistered;
    }

    public void setCanBeRegistered(Character canBeRegistered) {
        this.canBeRegistered = canBeRegistered;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public Integer getNoOfFieldResearcher() {
        return noOfFieldResearcher;
    }

    public void setNoOfFieldResearcher(Integer noOfFieldResearcher) {
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
