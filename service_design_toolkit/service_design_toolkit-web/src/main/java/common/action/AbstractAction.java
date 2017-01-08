/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.action;

import common.controller.AbstractController;
import java.util.Observable;
import java.util.Observer;
import javax.faces.event.FacesEvent;

/**
 *
 * @author longnguyen
 */
public abstract class AbstractAction implements Observer {
    protected abstract boolean checkSource(FacesEvent event);
    
    public abstract void actionHandler(AbstractController controller, FacesEvent event);

    @Override
    public void update(Observable o, Object arg) {        
        FacesEvent event = (FacesEvent)arg;        
        if (!checkSource(event)) {            
            return;
        }
        actionHandler((AbstractController)o, (FacesEvent)arg);
    }
}
