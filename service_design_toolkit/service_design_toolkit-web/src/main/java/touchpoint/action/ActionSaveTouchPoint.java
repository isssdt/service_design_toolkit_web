/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.ScreenTitles;
import common.action.AbstractAction;
import common.controller.AbstractController;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;
import touchpoint.ejb.view.CreateView;

/**
 *
 * @author longnguyen
 */
public class ActionSaveTouchPoint extends AbstractAction {
    @Override
    protected boolean checkSource(FacesEvent event) {
        return ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId());
    }

    @Override
    public void actionHandler(AbstractController controller, FacesEvent event) {        
        CreateView createView = (CreateView)controller.getView();
        RequestContext.getCurrentInstance().closeDialog(createView.getTouchPointDTO());
    }
}
