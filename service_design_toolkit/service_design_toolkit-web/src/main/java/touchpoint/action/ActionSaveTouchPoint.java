/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.ScreenTitles;
import common.controller.AbstractController;
import java.util.Observable;
import java.util.Observer;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import touchpoint.ejb.view.CreateView;

/**
 *
 * @author longnguyen
 */
public class ActionSaveTouchPoint implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        ActionEvent actionEvent = (ActionEvent)arg;
        if (!ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_TOUCH_POINT_ADD_ID.equals(actionEvent.getComponent().getId())) {
            return;
        }
        AbstractController controller = (AbstractController)o;
        CreateView createView = (CreateView)controller.getView();
        RequestContext.getCurrentInstance().closeDialog(createView.getTouchPointDTO());
    }    
}
