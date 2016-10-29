/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import journey.dto.JourneyFieldResearcherDTO;
import journey.entity.JourneyFieldResearcher;

/**
 *
 * @author longnguyen
 */
@Local
public interface JourneyFieldResearcherFacadeLocal {

    JourneyFieldResearcher create(JourneyFieldResearcher journeyFieldResearcher);

    void edit(JourneyFieldResearcher journeyFieldResearcher);

    void remove(JourneyFieldResearcher journeyFieldResearcher);

    JourneyFieldResearcher find(Object id);

    List<JourneyFieldResearcher> findAll();

    List<JourneyFieldResearcher> findRange(int[] range);

    int count();   
    
    JourneyFieldResearcher findSingleByQueryName(String queryName, Map<String, Object> params);
    
    JourneyFieldResearcher findJourneyOfFieldResearcherByStatus(JourneyFieldResearcherDTO journeyFieldResearcherDTO);
    
    JourneyFieldResearcher findJourneyByNameAndFieldResearcher(JourneyFieldResearcherDTO journeyFieldResearcherDTO);
}
