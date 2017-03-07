/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.controller;

import common.ScreenTitles;
import common.action.ActionHandler;
import common.controller.AbstractController;
import common.view.AbstractView;
import javax.faces.event.FacesEvent;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_ADD;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_ADD_AJAX;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN_AJAX;
import journey.action.ACTION_BUTTON_ADD_TOUCH_POINT_SAVE;
import journey.action.ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY;
import journey.action.ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY_AJAX;
import journey.action.ACTION_BUTTON_CREATE_JOURNEY_NEXT;
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
    protected ActionHandler initActionHandler(FacesEvent event) {
        if (!(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_ADD_TOUCH_POINT_ADD();
        }
        if ((event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_ADD_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_ADD_TOUCH_POINT_ADD_AJAX();
        }
        if (!(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_HIDDEN_ID.equals(event.getComponent().getId())) {
             return new ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN();
        }
        if ((event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_HIDDEN_ID.equals(event.getComponent().getId())) {
             return new ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN_AJAX();
        }
        if ((event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_HIDDEN_ID.equals(event.getComponent().getId())) {
             return new ACTION_BUTTON_ADD_TOUCH_POINT_HIDDEN_AJAX();
        }
        if (!(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_SAVE_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_ADD_TOUCH_POINT_SAVE();
        }
        if (ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_JOURNEY_NEXT_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_CREATE_JOURNEY_NEXT();
        }
        if (!(event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_CREATE_JOURNEY_CONFIRMATION_ID.equals(event.getComponent().getId())) {
            System.out.println("confirmation");
            return new ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY();
        }
        if ((event instanceof SelectEvent) && ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_SAVE_ID.equals(event.getComponent().getId())) {
            System.out.println("confirmation Ajax");
            return new ACTION_BUTTON_CONFIRMATION_CREATE_JOURNEY_AJAX();
        }
        return null;
    }
}
