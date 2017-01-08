package journey.action;


import common.ScreenTitles;
import common.action.AbstractAction;
import common.constant.ConstantValues;
import common.controller.AbstractController;
import java.util.HashMap;
import java.util.Map;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author longnguyen
 */
public class ActionOpenCreateTouchPointDialog extends AbstractAction {
    @Override
    protected boolean checkSource(FacesEvent event) {
        return !(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId());
    }

    @Override
    public void actionHandler(AbstractController controller, FacesEvent event) {
        Map<String,Object> options = new HashMap<>();
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog(ConstantValues.DIALOG_LOCATION_CREATE_TOUCH_POINT, options, null);
    }
}
