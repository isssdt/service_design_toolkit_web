/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public Journey findJourneyByName(Object journeyName) {
        Query query = em.createNamedQuery("Journey.findByJourneyName");
        query.setParameter("journeyName", journeyName);
        Journey journey = (Journey)query.getSingleResult();        
        journey.getTouchPointList().size();
        return journey;
    }

    @Override
    public List<Journey> findListOfJourneyByIsActive(Object isActive) {
        Query query = em.createNamedQuery("Journey.findByIsActive");
        query.setParameter("isActive", isActive);
        return query.getResultList();       
    }
    
}
