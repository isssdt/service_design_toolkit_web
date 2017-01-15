/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.ScreenTitles;
import common.action.ActionHandler;
import common.utils.Utils;
import common.view.AbstractView;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;
import touchpoint.ejb.view.GeoMapView;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_TOUCH_POINT_LOCATION_SET implements ActionHandler {

    @Override
    public void execute(AbstractView view, FacesEvent event) {        
        GeoMapView geoMapView = (GeoMapView)view;
        if (null == geoMapView.getTouchPointDTO() || null == geoMapView.getTouchPointDTO().getLatitude()) {
            Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_ERROR, 
                    ScreenTitles.SCREEN_COMPONENT_BUTTON_TOUCH_POINT_LOCATION_SET_MESSAGE, null, null, false);
            return;
        }
        RequestContext.getCurrentInstance().closeDialog(geoMapView.getTouchPointDTO());
    }
    
}
