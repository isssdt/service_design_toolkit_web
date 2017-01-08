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
import common.utils.Utils;
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
public class ActionForwardToAddTouchPointPage extends AbstractAction {
    @Override
    protected boolean checkSource(FacesEvent event) {
        return ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_JOURNEY_NEXT_ID.equals(event.getComponent().getId()); 
    }

    @Override
    public void actionHandler(AbstractController controller, FacesEvent event) {
        CreateView createView = (CreateView)controller.getView();
        JourneyDTO journeyDTO = createView.getJourneyDTO();        
        journeyDTO.setJourneyName(createView.getJourneyName());
        Utils.setAttributeOfSession(JourneyDTO.class.toString(), journeyDTO);
        try {
            Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_ADD_TOUCH_POINT_PAGE);
        } catch (IOException ex) {
            Logger.getLogger(ActionForwardToAddTouchPointPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
