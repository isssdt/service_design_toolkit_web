/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.controller;

import common.action.ActionHandler;
import common.view.AbstractView;
import java.util.Observable;
import javax.faces.event.FacesEvent;

/**
 *
 * @author longnguyen
 */
public abstract class AbstractController extends Observable {

    private final AbstractView view;
    protected ActionHandler actionHandler;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbstractController(AbstractView view) {
        this.view = view;
    }

    protected abstract void initActionHandler(FacesEvent event);

    public AbstractView getView() {
        return view;
    }

    public void actionListener(FacesEvent event) {
        initActionHandler(event);
        if (null != actionHandler) {
            actionHandler.execute(view, event);
            actionHandler = null;
        }
    }
}
