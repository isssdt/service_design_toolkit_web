/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.controller;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import journey.dto.TouchPointDTO;
import journey.ejb.business.JourneyServiceLocal;
import touchpoint.ejb.business.TouchPointServiceLocal;
import user.dto.FieldResearcherDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "dashboardController")
@SessionScoped
public class DashboardController implements Serializable {
    @EJB
    private JourneyServiceLocal journeyService;
    @EJB
    private TouchPointServiceLocal touchPointService;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
    }
    
    public List<JourneyDTO> getActiveJourneyList() throws AppException, CustomReasonPhraseException {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setIsActive('Y');        
        return journeyService.getAllJourney();
    }
    
    public List<FieldResearcherDTO> getRegisteredFieldResearchersByJourneyName(JourneyDTO journeyDTO) {
        return journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);
    }
    
    public List<TouchPointDTO> getTouchPointList (JourneyDTO journeyDTO){
        return touchPointService.getTouchPointListJourney(journeyDTO);
    }
}
