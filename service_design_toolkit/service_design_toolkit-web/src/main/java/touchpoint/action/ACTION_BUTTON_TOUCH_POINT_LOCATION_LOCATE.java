/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.ScreenTitles;
import common.action.ActionHandler;
import common.view.AbstractView;
import javax.faces.event.FacesEvent;
import org.primefaces.context.RequestContext;
import touchpoint.ejb.view.GeoMapView;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_TOUCH_POINT_LOCATION_LOCATE implements ActionHandler {

    @Override
    public void execute(AbstractView view, FacesEvent event) {
        GeoMapView geoMapView = (GeoMapView)view;
        geoMapView.getTouchPointLocationModel().getMarkers().clear();
        geoMapView.getTouchPointDTO().setLatitude(null);
        geoMapView.getTouchPointDTO().setLongitude(null);
        geoMapView.getTouchPointDTO().setRadius(null);
        
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(ScreenTitles.SCREEN_COMPONENT_TEXT_FIELD_TOUCH_POINT_LOCATION_ADDRESS_ID);
        context.execute(ScreenTitles.SCREEN_COMPONENT_JS_FUNCTION_LOCATE_TOUCH_POINT);
    }
    
}
