/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.ScreenTitles;
import common.action.AbstractAction;
import common.constant.ConstantValues;
import common.controller.AbstractController;
import java.util.HashMap;
import java.util.Map;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author longnguyen
 */
public class ActionSpecifyLocationOfTouchPoint extends AbstractAction {
    @Override
    protected boolean checkSource(FacesEvent event) {
        return ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_TOUCH_POINT_SPECFIY_LOCATION_ID.equals(event.getComponent().getId());
    }

    @Override
    public void actionHandler(AbstractController controller, FacesEvent event) {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);        
        options.put("resizable", false);
        options.put("contentWidth", 1000);
        options.put("contentHeight", 500);
        RequestContext.getCurrentInstance().openDialog(ConstantValues.DIALOG_LOCATION_TOUCH_POINT_LOCATION, options, null);
    }
    
}
