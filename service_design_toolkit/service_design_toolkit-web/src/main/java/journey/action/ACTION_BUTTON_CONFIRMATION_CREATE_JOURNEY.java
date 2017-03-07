/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.action;

import common.action.ActionHandler;
import common.constant.ConstantValues;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import journey.ejb.view.ConfirmationView;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Manish
 */
public class ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY implements ActionHandler {
    
    @Override
    public void execute(AbstractView view, FacesEvent event) {
        ConfirmationView confirmationView = (ConfirmationView)view;
        
//        if (null == addTouchPointView.getJourneyDTO().getTouchPointListDTO() || 
//                null == addTouchPointView.getJourneyDTO().getTouchPointListDTO().getTouchPointDTOList() ||
//                addTouchPointView.getJourneyDTO().getTouchPointListDTO().getTouchPointDTOList().isEmpty()) {
//            Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, 
//                    ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_SAVE_MESSAGE, null, null, false);
//            return;
//        }
//        
//        JourneyServiceLocal journeyService = (JourneyServiceLocal)addTouchPointView.getServices().getBusinessService(JourneyServiceLocal.class.toString());
//        addTouchPointView.getJourneyDTO().setCanBeRegistered('Y');
//        addTouchPointView.getJourneyDTO().setIsActive('Y');
//        try {            
//            journeyService.createJourney(addTouchPointView.getJourneyDTO());
//        } catch (AppException | CustomReasonPhraseException ex) {
//            Logger.getLogger(ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Map<String,Object> options = new HashMap<>();
//        options.put("draggable", true);
//        options.put("modal", true);
//        options.put("resizable", false);
//        RequestContext.getCurrentInstance().openDialog(ConstantValues.
//        DIALOG_JOURNEY_CREATED_CONFIRMATION, options, null);
//        System.out.println("b4 cleaning");
        Utils.removeAttributeOfSession(confirmationView.getJourneyDTO());
//        System.out.println("after cleaning");
        RequestContext.getCurrentInstance().closeDialog(ConstantValues.DIALOG_JOURNEY_CREATED_CONFIRMATION);
//        Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_INFO, "A Journey has been created", null, 
//                null, true);


        
//        try {
//            Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_CREATE_JOURNEY_PAGE);
//            System.out.println("inside try");
//        } catch (IOException ex) {
//            System.out.println("inside catch");
//            Logger.getLogger(ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY.class.getName()).log(Level.SEVERE, null, ex);
//        }        
    }
    
}
