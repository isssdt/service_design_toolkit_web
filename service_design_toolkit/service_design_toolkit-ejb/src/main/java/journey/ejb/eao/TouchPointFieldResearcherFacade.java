/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import common.ejb.eao.AbstractFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.entity.TouchPoint;
import journey.entity.TouchpointFieldResearcher;

/**
 *
 * @author samru
 */

@Stateless
public class TouchPointFieldResearcherFacade  extends AbstractFacade<TouchpointFieldResearcher> implements TouchPointFieldResearcherFacadeLocal{

    @PersistenceContext(unitName = "service_design_toolkit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TouchPointFieldResearcherFacade() {
        super(TouchpointFieldResearcher.class);
    }

    @Override
    public TouchpointFieldResearcher findByTouchpointIdAndFieldResearcherName(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        TouchPoint touchPoint = new TouchPoint(touchPointFieldResearcherDTO.getTouchpointDTO().getId());        
        Map<String, Object> params = new HashMap<>();
        params.put("touchpointId", touchPoint);
        params.put("username", touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        return findSingleByQueryName("TouchpointFieldResearcher.findByTouchpointIdAndFieldResearcherName", params);
    }

    @Override
    public List<TouchpointFieldResearcher> findByStatusAndFieldResearcherName(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        params.put("status", touchPointFieldResearcherDTO.getStatus());
        return findListByQueryName("TouchpointFieldResearcher.findByStatusAndFieldResearcherName", params);
    }


    
}
