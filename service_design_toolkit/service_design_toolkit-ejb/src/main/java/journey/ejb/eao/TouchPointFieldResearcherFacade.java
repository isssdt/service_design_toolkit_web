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
import journey.dto.JourneyDTO;
import user.dto.TouchPointFieldResearcherDTO;
import touchpoint.entity.TouchPoint;
import user.entity.TouchpointFieldResearcher;
import user.dto.SdtUserDTO;

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

    @Override
    public List<TouchpointFieldResearcher> findByJourneyName(JourneyDTO journeyDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("journeyName", journeyDTO.getJourneyName());        
        return findListByQueryName("TouchpointFieldResearcher.findByJourneyName", params);
    }

    @Override
    public List<TouchpointFieldResearcher> findByJourneyNameAndUsername(JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("journeyName", journeyDTO.getJourneyName());
        params.put("username", sdtUserDTO.getUsername());
        return findListByQueryName("TouchpointFieldResearcher.findByJourneyNameAndUsername", params);
    }


    
}
