/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.controller;

import common.ScreenTitles;
import common.controller.AbstractController;
import common.view.AbstractView;
import javax.faces.event.FacesEvent;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_ADD;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_ADD_AJAX;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author longnguyen
 */
public class JourneyController extends AbstractController {

    public JourneyController(AbstractView view) {
        super(view);
    }    

    @Override
    protected void initActionHandler(FacesEvent event) {
        if (!(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId())) {
            actionHandler = new ACTION_BUTTON_ADD_TOUCH_POINT_ADD();
        }
        if ((event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId())) {
            actionHandler = new ACTION_BUTTON_ADD_TOUCH_POINT_ADD_AJAX();
        }
        if (!(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_HIDDEN_ID.equals(event.getComponent().getId())) {
            actionHandler = new ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN();
        }
    }
}
