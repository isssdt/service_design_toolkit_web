/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.action.ActionHandler;
import common.view.AbstractView;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;
import touchpoint.ejb.view.CreateView;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_CREATE_TOUCH_POINT_SPECFIY_LOCATION implements ActionHandler {
    @Override
    public void execute(AbstractView view, FacesEvent event) {
        CreateView createView = (CreateView)view;        
        RequestContext.getCurrentInstance().closeDialog(createView.getTouchPointDTO());       
    }
    
}
