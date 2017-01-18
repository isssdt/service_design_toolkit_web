/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.eao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.JourneyFieldResearcherFacadeLocal;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import user.ejb.eao.FieldResearcherFacadeLocal;
import user.ejb.eao.SdtUserFacadeLocal;
import user.ejb.eao.UserRoleFacadeLocal;

/**
 *
 * @author longnguyen
 */
@Stateless
@LocalBean
public class EAOFactory {

    @EJB
    JourneyFieldResearcherFacadeLocal journeyFieldResearcherFacade;
    
    @EJB
    TouchPointFieldResearcherFacadeLocal touchPointFieldResearcherFacade;
    
    @EJB
    RatingFacadeLocal ratingFacade;
    
    @EJB
    TouchPointFacadeLocal touchPointFacade;
    
    @EJB
    SdtUserFacadeLocal sdtUserFacade;
    
    @EJB
    JourneyFacadeLocal journeyFacade;
    
    @EJB
    ChannelFacadeLocal channelFacade;
    
    @EJB
    private UserRoleFacadeLocal userRoleFacade;
    
    @EJB
    private FieldResearcherFacadeLocal fieldResearcherFacade;

    public FieldResearcherFacadeLocal getFieldResearcherFacade() {
        return fieldResearcherFacade;
    }

    public ChannelFacadeLocal getChannelFacade() {
        return channelFacade;
    }

    public UserRoleFacadeLocal getUserRoleFacade() {
        return userRoleFacade;
    }
    
    public JourneyFieldResearcherFacadeLocal getJourneyFieldResearcherFacade() {
        return journeyFieldResearcherFacade;
    }

    public TouchPointFieldResearcherFacadeLocal getTouchPointFieldResearcherFacade() {
        return touchPointFieldResearcherFacade;
    }

    public RatingFacadeLocal getRatingFacade() {
        return ratingFacade;
    }

    public TouchPointFacadeLocal getTouchPointFacade() {
        return touchPointFacade;
    }

    public SdtUserFacadeLocal getSdtUserFacade() {
        return sdtUserFacade;
    }

    public JourneyFacadeLocal getJourneyFacade() {
        return journeyFacade;
    }
    
    public EAOFacade getFacade(String facade) {
        if (JourneyFacadeLocal.class.toString().equals(facade)) {
            return journeyFacade;
        }
        if (JourneyFieldResearcherFacadeLocal.class.toString().equals(facade)) {
            return journeyFieldResearcherFacade;
        }
        if (SdtUserFacadeLocal.class.toString().equals(facade)) {
            return sdtUserFacade;
        }
        if (UserRoleFacadeLocal.class.toString().equals(facade)) {
            return userRoleFacade;
        }
        return null;
    }
}
