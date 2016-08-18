/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

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
    
    private String journeyName;
    private Integer noOfFieldResearcher;
    private String radius;

    public String getRadius() {
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
