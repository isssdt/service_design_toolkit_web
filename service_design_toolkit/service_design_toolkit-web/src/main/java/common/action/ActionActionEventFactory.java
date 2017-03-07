/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.action;

import auth.action.ACTION_BUTTON_ACTION_EVENT_LOGIN_LOGIN;
import common.ScreenTitles;
import javax.faces.event.ActionEvent;

/**
 *
 * @author longnguyen
 */
public class ActionActionEventFactory implements ActionAbstractFactory {
    @Override
    public GenericActionHandler<ActionEvent> initActionEventHandler(ActionEvent event) {
        if (ScreenTitles.SCREEN_COMPONENT_BUTTON_LOGIN_LOGIN_ID.equals(event.getComponent().getId())) {
            return new ACTION_BUTTON_ACTION_EVENT_LOGIN_LOGIN();
        } 
        return null;
    }
}
