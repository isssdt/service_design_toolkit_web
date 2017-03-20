/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.action;

import common.ScreenTitles;
import common.action.ActionHandler;
import common.constant.ConstantValues;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import journey.ejb.business.JourneyServiceLocal;
import journey.ejb.view.AddTouchPointView;
import org.primefaces.context.RequestContext;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_ADD_TOUCH_POINT_SAVE implements ActionHandler {
    

    @Override
    public void execute(AbstractView view, FacesEvent event) {
        AddTouchPointView addTouchPointView = (AddTouchPointView)view;
        
        if (null == addTouchPointView.getJourneyDTO().getTouchPointListDTO() || 
                null == addTouchPointView.getJourneyDTO().getTouchPointListDTO().getTouchPointDTOList() ||
                addTouchPointView.getJourneyDTO().getTouchPointListDTO().getTouchPointDTOList().isEmpty()) {
            Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, 
                    ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_SAVE_MESSAGE, null, null, false);
            return;
        }
        
        JourneyServiceLocal journeyService = (JourneyServiceLocal)addTouchPointView.getServices().getBusinessService(JourneyServiceLocal.class.toString());
        addTouchPointView.getJourneyDTO().setCanBeRegistered('Y');
        addTouchPointView.getJourneyDTO().setIsActive('Y');
        try {            
            journeyService.createJourney(addTouchPointView.getJourneyDTO());
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(ACTION_BUTTON_ADD_TOUCH_POINT_SAVE.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String,Object> options = new HashMap<>();
        options.put("draggable", true);
        options.put("modal", true);
        options.put("resizable", false);
        options.put("responsive", true);
        RequestContext.getCurrentInstance().openDialog(ConstantValues.DIALOG_JOURNEY_CREATED_CONFIRMATION, options, null);       
    }
}
