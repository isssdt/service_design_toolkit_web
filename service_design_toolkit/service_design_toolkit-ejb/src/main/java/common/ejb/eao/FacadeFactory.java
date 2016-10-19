/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.eao;

import javax.ejb.EJB;
import javax.ejb.Local;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.JourneyFieldResearcherFacadeLocal;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import user.ejb.business.UserServiceLocal;
import user.ejb.eao.FieldResearcherFacadeLocal;
import user.ejb.eao.SdtUserFacadeLocal;

/**
 *
 * @author longnguyen
 */

@Local
public class FacadeFactory {
    @EJB
    private JourneyFieldResearcherFacadeLocal journeyFieldResearcherFacade;

    @EJB
    private JourneyFacadeLocal journeyFacade;

    @EJB
    private ChannelFacadeLocal channelFacade;

    @EJB
    private RatingFacadeLocal ratingFacade;

    @EJB
    private TouchPointFacadeLocal touchPointFacade;

    @EJB
    private SdtUserFacadeLocal sdtUserFacade;

    @EJB
    private FieldResearcherFacadeLocal fieldResearcherFacade;

    @EJB
    private TouchPointFieldResearcherFacadeLocal touchPointFieldResearcherFacade;

    @EJB
    private UserServiceLocal userService;

    public JourneyFieldResearcherFacadeLocal getJourneyFieldResearcherFacade() {
        return journeyFieldResearcherFacade;
    }

    public JourneyFacadeLocal getJourneyFacade() {
        return journeyFacade;
    }

    public ChannelFacadeLocal getChannelFacade() {
        return channelFacade;
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

    public FieldResearcherFacadeLocal getFieldResearcherFacade() {
        return fieldResearcherFacade;
    }

    public TouchPointFieldResearcherFacadeLocal getTouchPointFieldResearcherFacade() {
        return touchPointFieldResearcherFacade;
    }

    public UserServiceLocal getUserService() {
        return userService;
    }
}
