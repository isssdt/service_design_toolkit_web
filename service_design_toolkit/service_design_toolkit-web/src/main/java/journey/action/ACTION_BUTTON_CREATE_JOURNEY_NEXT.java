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
import journey.dto.JourneyDTO;
import journey.ejb.view.CreateView;
/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_CREATE_JOURNEY_NEXT implements ActionHandler {
    @Override
    public void execute(AbstractView view, FacesEvent event) {
        CreateView createView = (CreateView)view;
        JourneyDTO journeyDTO = createView.getJourneyDTO();        
        journeyDTO.setJourneyName(createView.getJourneyName());
        Utils.setAttributeOfSession(journeyDTO);
        try {
            Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_ADD_TOUCH_POINT_PAGE);
        } catch (IOException ex) {
            Logger.getLogger(ACTION_BUTTON_CREATE_JOURNEY_NEXT.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
