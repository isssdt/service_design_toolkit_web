/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.controller;

import common.action.ActionHandler;
import common.controller.AbstractController;
import common.view.AbstractView;
import javax.faces.event.FacesEvent;

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
        return null;
    }
}
