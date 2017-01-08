/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import common.ejb.business.ServiceFactory;
import common.view.AbstractView;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import journey.constraint.JourneyConstraint;
import journey.controller.JourneyController;
import journey.dto.JourneyDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "journey_ejb_view_CreateView")
@ViewScoped
public class CreateView extends AbstractView implements Serializable {
    @EJB
    ServiceFactory serviceFactory;

    @JourneyConstraint(message = "Journey Name already exists!")
    private String journeyName;

    //model of new Journey
    private JourneyDTO journeyDTO;

    private Date currentDate;

    /**
     * Creates a new instance of CreateView
     */
    public CreateView() {
        super();
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public Date getCurrentDate() {
        return new Date();
    }

    @Override
    public void initController() {
        controller = new JourneyController(this);
    }

    @Override
    public void initData() {
        journeyDTO = new JourneyDTO();
    }

    @Override
    public ServiceFactory getServices() {
        return serviceFactory;
    }
}
