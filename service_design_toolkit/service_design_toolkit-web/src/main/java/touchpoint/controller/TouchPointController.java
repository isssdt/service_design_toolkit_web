/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.controller;

import common.controller.AbstractController;
import common.view.AbstractView;
import touchpoint.action.ActionSaveTouchPoint;
import touchpoint.action.ActionSpecifyLocationOfTouchPoint;

/**
 *
 * @author longnguyen
 */
public class TouchPointController extends AbstractController {

    public TouchPointController(AbstractView view) {
        super(view);
    }

    @Override
    protected void addObservers() {
        addObserver(new ActionSaveTouchPoint());
        addObserver(new ActionSpecifyLocationOfTouchPoint());
    }    
}
