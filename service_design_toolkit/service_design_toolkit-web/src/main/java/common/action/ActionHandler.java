/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.action;

import common.view.AbstractView;
import javax.faces.event.FacesEvent;

/**
 *
 * @author longnguyen
 */
public interface ActionHandler {
    public void execute(AbstractView view, FacesEvent event);
}
