/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.action;

import common.ScreenTitles;
import common.action.AbstractAction;
import common.constant.ConstantValues;
import common.controller.AbstractController;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.utils.Utils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import journey.dto.JourneyDTO;
import journey.ejb.view.AddTouchPointView;

/**
 *
 * @author longnguyen
 */
public class ActionSaveJourney extends AbstractAction {

    @Override
    protected boolean checkSource(FacesEvent event) {
        return ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_SAVE_ID.equals(event.getComponent().getId());
    }

    @Override
    public void actionHandler(AbstractController controller, FacesEvent event) {
        AddTouchPointView addTouchPointView = (AddTouchPointView)controller.getView();
        try {
            addTouchPointView.getServices().getJourneyService().createJourney(addTouchPointView.getJourneyDTO());
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(ActionSaveJourney.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.removeAttributeOfSession(JourneyDTO.class.toString());
        Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "Information", "A Journey has been created", null, true);
        try {
            Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_CREATE_JOURNEY_PAGE);
        } catch (IOException ex) {
            Logger.getLogger(ActionSaveJourney.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
