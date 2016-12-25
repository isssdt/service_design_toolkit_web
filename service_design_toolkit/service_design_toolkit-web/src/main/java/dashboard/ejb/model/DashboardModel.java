/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.ejb.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Leon
 */
@Named(value = "dashboardModel")
@SessionScoped
public class DashboardModel implements Serializable {
    private String journeyName;

    /**
     * Creates a new instance of DashboardModel
     */
    public DashboardModel() {
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }
    
}
