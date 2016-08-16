/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb;

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
    public Journey find(Object id) {
        Query query = em.createNamedQuery("Journey.findById");
        query.setParameter("id", 2);
        Journey result = (Journey)query.getSingleResult();
//        result.setJourneyName(query.getSingleResult().toString());
        result.getTouchPointList().size();
        return result;
    }
    
}
