/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import common.ejb.eao.AbstractFacade;
import java.sql.Date;
import java.sql.SQLXML;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import journey.dto.JourneyDTO;
import journey.entity.Journey;

/**
 *
 * @author longnguyen
 */
@Stateless
public class JourneyFacade extends AbstractFacade<Journey> implements JourneyFacadeLocal {

    @PersistenceContext(unitName = "service_design_toolkit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JourneyFacade() {
        super(Journey.class);
    }

    @Override
    public Journey findJourneyByName(JourneyDTO journeyDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("journeyName", journeyDTO.getJourneyName());        
        Journey journey = findSingleByQueryName("Journey.findByJourneyName", params);        
        return journey;
    }

    @Override
    public List<Journey> findListOfJourneyByIsActive(Object isActive) {
        Query query = em.createNamedQuery("Journey.findByIsActive");
        query.setParameter("isActive", isActive);
        return query.getResultList();       
    }

    @Override
    public List<Journey> findJourneyListForRegister() {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", new Date(new java.util.Date().getTime()));
        params.put("endDate", new Date(new java.util.Date().getTime()));
        return findListByQueryName("Journey.findJourneyListForRegister", params);
    }
    
}
