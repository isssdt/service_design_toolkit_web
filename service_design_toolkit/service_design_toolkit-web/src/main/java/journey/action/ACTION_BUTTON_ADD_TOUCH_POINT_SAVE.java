/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.action;

import common.action.ActionHandler;
import common.constant.ConstantValues;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import journey.ejb.business.JourneyServiceLocal;
import journey.ejb.view.AddTouchPointView;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_ADD_TOUCH_POINT_SAVE implements ActionHandler {
    

    @Override
    public void execute(AbstractView view, FacesEvent event) {
        AddTouchPointView addTouchPointView = (AddTouchPointView)view;
        JourneyServiceLocal journeyService = (JourneyServiceLocal)addTouchPointView.getServices().getBusinessService(JourneyServiceLocal.class.toString());
        try {            
            journeyService.createJourney(addTouchPointView.getJourneyDTO());
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(ACTION_BUTTON_ADD_TOUCH_POINT_SAVE.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.removeAttributeOfSession(addTouchPointView.getJourneyDTO());
        Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Information", "A Journey has been created", null, true);
        try {
            Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_CREATE_JOURNEY_PAGE);
        } catch (IOException ex) {
            Logger.getLogger(ACTION_BUTTON_ADD_TOUCH_POINT_SAVE.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
