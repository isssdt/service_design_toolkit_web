/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import journey.ejb.business.JourneyServiceLocal;
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

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {
    }
    
    public List<JourneyDTO> getActiveJourneyList() {
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setIsActive('Y');        
        JourneyListDTO journeyListDTO = journeyService.getJourneyList(journeyDTO, "Journey.findByIsActive");
        if (null != journeyListDTO) {
            return journeyListDTO.getJourneyDTOList();
        }
        return null;
    }
    
    public List<FieldResearcherDTO> getRegisteredFieldResearchersByJourneyName(JourneyDTO journeyDTO) {
        return journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO);
    }
    
}
