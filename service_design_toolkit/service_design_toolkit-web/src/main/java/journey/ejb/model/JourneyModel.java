/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Min;
import journey.constraint.Journey;

/**
 *
 * @author longnguyen
 */
@Named(value = "journeyModel")
@RequestScoped
public class JourneyModel implements Serializable {

    /**
     * Creates a new instance of JourneyModel
     */
    public JourneyModel() {
    }
    
    @Journey(message = "This journey name already exists!")
    private String journeyName;
    
    @Min(1)
    private Integer noOfFieldResearcher;
    
    private String radius;

    public String getRadius() {
        if (null == radius || radius.length() == 0) {
            return "1";
        }
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Integer getNoOfFieldResearcher() {        
        return noOfFieldResearcher;
    }

    public void setNoOfFieldResearcher(Integer noOfFieldResearcher) {
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }
    
}
