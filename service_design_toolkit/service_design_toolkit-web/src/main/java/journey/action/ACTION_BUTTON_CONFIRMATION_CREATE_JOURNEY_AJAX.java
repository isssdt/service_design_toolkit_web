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
import journey.ejb.view.AddTouchPointView;
import journey.ejb.view.ConfirmationView;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Manish
 */
public class ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY_AJAX implements ActionHandler {
    
    @Override
    public void execute(AbstractView view, FacesEvent event) {
        ConfirmationView confirmationView = (ConfirmationView)view;
        try {
            Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_CREATE_JOURNEY_PAGE);
            System.out.println("inside try");
        } catch (IOException ex) {
            System.out.println("inside catch");
            Logger.getLogger(ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY_AJAX.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
