/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import journey.constraint.JourneyConstraint;

/**
 *
 * @author Manish
 */
@Named(value="journeyModel")
@SessionScoped
public class JourneyModel implements Serializable {
    
       private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of JourneyModel
     */
    public JourneyModel() {
    }
    @JourneyConstraint(message = "This journey name already exists!")
    
    private String journeyName;
    private int noOfFieldResearcher;    
    private Date startDate;
    private Date endDate;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    @PostConstruct
	private void init() {
		System.out.println(">>> @PostConstruct: JourneyModel");
	}
    
    @PreDestroy
	private void destroy() {
		System.out.println(">>> @PreDestry: JourneyModel");
	}
    public String getJourneyName() {

        return journeyName;
        
    }

    public void setJourneyName(String journeyName) {

        this.journeyName = journeyName;
    }

    public int getNoOfFieldResearcher() {

        return noOfFieldResearcher;
    }

    public void setNoOfFieldResearcher(int noOfFieldResearcher) {
        this.noOfFieldResearcher = noOfFieldResearcher;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
    public JourneyModel createCopy() {
        JourneyModel model = new JourneyModel();
            model.setEndDate(endDate);
            model.setJourneyName(journeyName);
            model.setNoOfFieldResearcher(noOfFieldResearcher);
            model.setStartDate(startDate);
            model.setDescription(description);
       return model;
    }    
}
