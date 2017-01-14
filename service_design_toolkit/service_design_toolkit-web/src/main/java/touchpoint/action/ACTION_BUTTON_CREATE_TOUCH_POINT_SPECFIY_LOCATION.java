/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.action.ActionHandler;
import common.constant.ConstantValues;
import common.utils.Utils;
import common.view.AbstractView;
import java.util.HashMap;
import java.util.Map;
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
        Utils.setAttributeOfSession(createView.getTouchPointDTO());
        
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);        
        options.put("resizable", false);
        options.put("contentWidth", 1000);
        options.put("contentHeight", 600);
        RequestContext.getCurrentInstance().openDialog(ConstantValues.DIALOG_LOCATION_TOUCH_POINT_LOCATION, options, null);
    }
    
}
