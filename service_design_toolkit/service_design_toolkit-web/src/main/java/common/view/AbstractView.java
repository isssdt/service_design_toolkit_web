 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.view;

import common.action.ActionActionEventFactory;
import common.action.ActionFactoryProducer;
import common.controller.AbstractController;
import common.ejb.business.ServiceFactory;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.GeocodeEvent;

/**
 *
 * @author longnguyen
 */


public abstract class AbstractView {    
    protected AbstractController controller;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbstractView() {
        initController();
        initData();
    }    
    
    public abstract void initController();
    
    public abstract void initData();
    
    public abstract ServiceFactory getServices();

    public void actionListener(ActionEvent event) {               
        controller.actionListener(event);
    }
    
    public void onDialogReturnListener(SelectEvent event) {
        controller.actionListener(event);
    }
    
    public void onGeoCode(GeocodeEvent event) {
        controller.actionListener(event);
    }
    
    public void onDropDownChange(AjaxBehaviorEvent event) {
        controller.actionListener(event);
    }
    
    public void actionEventHandler(ActionEvent event) {
        ActionFactoryProducer.produceActionFactory(ActionActionEventFactory.class.toString()).initActionEventHandler(event).execute(this, event);
    }
}
