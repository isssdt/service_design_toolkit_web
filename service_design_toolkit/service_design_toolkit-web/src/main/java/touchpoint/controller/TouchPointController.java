/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.controller;

import common.ScreenTitles;
import common.action.ActionHandler;
import common.controller.AbstractController;
import common.view.AbstractView;
import javax.faces.event.FacesEvent;
import touchpoint.action.ACTION_BUTTON_CREATE_TOUCH_POINT_ADD;
import touchpoint.action.ACTION_BUTTON_CREATE_TOUCH_POINT_SPECFIY_LOCATION;
import touchpoint.action.ACTION_BUTTON_TOUCH_POINT_LOCATION_SET;
import touchpoint.action.ACTION_GMAP_TOUCH_POINT_LOCATION_AJAX;

/**
 *
 * @author longnguyen
 */
public class TouchPointController extends AbstractController {

    public TouchPointController(AbstractView view) {
        super(view);
    }      

    @Override
    protected ActionHandler initActionHandler(FacesEvent event) {         
        if (ScreenTitles.SCREEN_COMPONENT_BUTTON_TOUCH_POINT_LOCATION_SET_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_TOUCH_POINT_LOCATION_SET();
        }
        if (ScreenTitles.SCREEN_COMPONENT_GMAP_TOUCH_POINT_LOCATION_ID.equals(event.getComponent().getId())) {
            return new ACTION_GMAP_TOUCH_POINT_LOCATION_AJAX();
        }        
        if (ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_CREATE_TOUCH_POINT_ADD();
        }
        if (ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_TOUCH_POINT_SPECFIY_LOCATION_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_CREATE_TOUCH_POINT_SPECFIY_LOCATION();
        }
        return null;
    }
}
