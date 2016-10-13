/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import common.ejb.eao.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


    
}
