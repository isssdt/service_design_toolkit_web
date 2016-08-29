/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.business;

import javax.ejb.Local;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface JourneyServiceLocal {

    /**
     *
     * @param content
     * @return
     */
    public JourneyListDTO getJourneyList(JourneyDTO content);

    /**
     *
     * @param journeyDTO
     */
    public void createJourney(JourneyDTO journeyDTO);

    /**
     *
     * @param journeyDTO
     * @return
     */
    public JourneyDTO getTouchPointListOfJourney(JourneyDTO journeyDTO);

    /**
     *
     * @param journeyDTO
     * @return
     */
    public boolean isJourneyWithNameExist(JourneyDTO journeyDTO);

    /**
     *
     * @param journeySdtUserDTO
     * @return
     */
    public String registerFieldResearcherWithJourney(JourneyFieldResearcherDTO journeySdtUserDTO);
}
