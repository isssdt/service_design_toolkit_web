/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author longnguyen
 */
public class JourneyDTO {
    private String journeyName;
    private Integer noOfFieldResearcher;
    private Character isActive;
    private Date startDate;
    private int journeyLength;
    private List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getJourneyLength() {
        return journeyLength;
    }

    public void setJourneyLength(int journeyLength) {
        this.journeyLength = journeyLength;
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
}
