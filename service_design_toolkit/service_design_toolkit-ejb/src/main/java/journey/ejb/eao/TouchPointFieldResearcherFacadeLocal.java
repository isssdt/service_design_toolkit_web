/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import java.util.List;
import java.util.Map;
import journey.dto.JourneyDTO;
import user.dto.TouchPointFieldResearcherDTO;
import user.entity.TouchpointFieldResearcher;
import user.dto.SdtUserDTO;

/**
 *
 * @author samru
 */
public interface TouchPointFieldResearcherFacadeLocal {

    TouchpointFieldResearcher create(TouchpointFieldResearcher touchpointFieldResearcher);

    void edit(TouchpointFieldResearcher touchpointFieldResearcher);

    void remove(TouchpointFieldResearcher TouchpointFieldResearcher);

    TouchpointFieldResearcher find(Object id);

    List<TouchpointFieldResearcher> findAll();

    List<TouchpointFieldResearcher> findRange(int[] range);

    int count();

    List<TouchpointFieldResearcher> findListByNativeQuery(String query, List<Object> params);

    TouchpointFieldResearcher findSingleByNativeQuery(String query, List<Object> params);

    List<TouchpointFieldResearcher> findListByQueryName(String queryName, Map<String, Object> queryParamValues);    
    
    /**
     * get TouchPointFieldResearcher by Touch Point ID and Field Researcher Name 
     * @param touchPointFieldResearcherDTO
     * @return 
     */
    TouchpointFieldResearcher findByTouchpointIdAndFieldResearcherName(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO);
    
    List<TouchpointFieldResearcher> findByStatusAndFieldResearcherName(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO);
    
    /**
     * This method is used to get list of all TouchPointFieldResearcher of a Journey which has been rated already
     * @param journeyDTO contains name of Journey to find list of TouchPointFieldResearcher
     * @return list of TouchPointFieldResearcher of that Journey
     */
    List<TouchpointFieldResearcher> findByJourneyName(JourneyDTO journeyDTO);
    
    /**
     * This method is used to get list of all TouchPointFieldResearcher of a Journey that has been done by a SdtUser, which means it has been rated already
     * @param journeyDTO contains name of Journey to find list of TouchPointFieldResearcher
     * @param sdtUserDTO contains username of the user that has been worked on that Journey
     * @return list of all TouchPointFieldResearcher of the Journey that the user has been worked on
     */
    List<TouchpointFieldResearcher> findByJourneyNameAndUsername(JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO);
}
