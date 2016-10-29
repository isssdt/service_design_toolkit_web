/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import common.ejb.eao.AbstractFacade;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import journey.dto.JourneyFieldResearcherDTO;
import journey.entity.JourneyFieldResearcher;

/**
 *
 * @author longnguyen
 */
@Stateless
public class JourneyFieldResearcherFacade extends AbstractFacade<JourneyFieldResearcher> implements JourneyFieldResearcherFacadeLocal {

    @PersistenceContext(unitName = "service_design_toolkit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JourneyFieldResearcherFacade() {
        super(JourneyFieldResearcher.class);
    }

    @Override
    public JourneyFieldResearcher findJourneyOfFieldResearcherByStatus(JourneyFieldResearcherDTO journeyFieldResearcherDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        params.put("status", journeyFieldResearcherDTO.getStatus());
        return findSingleByQueryName("JourneyFieldResearcher.findJourneyOfFieldResearcherByStatus", params);
    }

    @Override
    public JourneyFieldResearcher findJourneyByNameAndFieldResearcher(JourneyFieldResearcherDTO journeyFieldResearcherDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        params.put("journeyName", journeyFieldResearcherDTO.getJourneyDTO().getJourneyName());
        return findSingleByQueryName("JourneyFieldResearcher.findJourneyByNameAndFieldResearcher", params);
    }
    
}
