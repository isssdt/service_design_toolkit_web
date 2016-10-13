/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.eao;

import common.ejb.eao.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import journey.entity.Channel;

/**
 *
 * @author samru
 */
@Stateless
public class ChannelFacade  extends AbstractFacade<Channel> implements ChannelFacadeLocal{

    @PersistenceContext(unitName = "service_design_toolkit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChannelFacade() {
        super(Channel.class);
    }

    @Override
    public Channel findChannelByName(Object channelName) {
        Query query = em.createNamedQuery("Channel.findByChannelName");
        query.setParameter("channelName", channelName);
        Channel channel = (Channel) query.getSingleResult(); 
        return channel;
    }
    
    @Override
    public List<Channel> findListOfChannel() {
        Query query = em.createNamedQuery("Channel.findChannelAll");
       
        return query.getResultList();       
    }

    
}
