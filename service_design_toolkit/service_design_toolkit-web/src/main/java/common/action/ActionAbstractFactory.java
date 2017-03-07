/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.action;

import javax.faces.event.ActionEvent;

/**
 *
 * @author longnguyen
 */
public interface ActionAbstractFactory {
    public GenericActionHandler<ActionEvent> initActionEventHandler(ActionEvent event);
}
