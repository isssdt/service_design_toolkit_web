/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import java.util.List;
import java.util.Map;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.entity.TouchpointFieldResearcher;

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
}
