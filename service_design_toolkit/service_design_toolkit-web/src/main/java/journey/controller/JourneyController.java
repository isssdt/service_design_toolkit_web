/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.controller;

import journey.action.ActionOpenCreateTouchPointDialog;
import common.controller.AbstractController;
import common.view.AbstractView;
import journey.action.ActionAddTouchPoint;
import journey.action.ActionForwardToAddTouchPointPage;
import journey.action.ActionSaveJourney;

/**
 *
 * @author longnguyen
 */
public class JourneyController extends AbstractController {

    public JourneyController(AbstractView view) {
        super(view);
    }
    @Override
    protected void addObservers() {
        addObserver(new ActionForwardToAddTouchPointPage());
        addObserver(new ActionOpenCreateTouchPointDialog());
        addObserver(new ActionAddTouchPoint());
        addObserver(new ActionSaveJourney());
    }
}
