/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.business;

import javax.ejb.Local;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;

/**
 *
 * @author longnguyen
 */
@Local
public interface JourneyServiceLocal {
    public JourneyListDTO getJourneyList(JourneyDTO content);
    public void createJourney(JourneyDTO journeyDTO);
    public JourneyDTO getTouchPointListOfJourney(JourneyDTO journeyDTO);
}
