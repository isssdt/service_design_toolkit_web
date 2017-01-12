package journey.action;


import common.action.ActionHandler;
import common.constant.ConstantValues;
import common.view.AbstractView;
import java.util.HashMap;
import java.util.Map;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_ADD_TOUCH_POINT_ADD implements ActionHandler {
    @Override
    public void execute(AbstractView view, FacesEvent event) {
        Map<String,Object> options = new HashMap<>();
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog(ConstantValues.DIALOG_LOCATION_CREATE_TOUCH_POINT, options, null);
    }
}
