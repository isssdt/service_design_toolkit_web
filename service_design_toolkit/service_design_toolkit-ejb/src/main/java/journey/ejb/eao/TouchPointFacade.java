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
import touchpoint.entity.TouchPoint;

/**
 *
 * @author samru
 */

@Stateless
public class TouchPointFacade  extends AbstractFacade<TouchPoint> implements TouchPointFacadeLocal{

    @PersistenceContext(unitName = "service_design_toolkit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TouchPointFacade() {
        super(TouchPoint.class);
    }    
}
