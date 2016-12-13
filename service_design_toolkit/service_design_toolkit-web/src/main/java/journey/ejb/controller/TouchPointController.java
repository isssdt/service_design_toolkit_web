/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import journey.ejb.model.TouchPointListModel;
import journey.ejb.model.TouchPointModel;
import journey.ejb.view.TouchPointFlowView;

/**
 *
 * @author samru
 */
@Named(value = "touchPointController")
@SessionScoped
public class TouchPointController implements Serializable  {
    
    
    private static final long serialVersionUID = 1L;
    
     @Inject
    private TouchPointListModel touchPointListModel;
    
    @Inject
    private TouchPointModel touchPointModel;
    
    @Inject 
    private TouchPointFlowView touchPointFlowView;

    public TouchPointFlowView getTouchPointFlowView() {
        return touchPointFlowView;
    }

    public void setTouchPointFlowView(TouchPointFlowView touchPointFlowView) {
        this.touchPointFlowView = touchPointFlowView;
    }

    public TouchPointListModel getTouchPointListModel() {
        return touchPointListModel;
    }

    public void setTouchPointListModel(TouchPointListModel touchPointListModel) {
        this.touchPointListModel = touchPointListModel;
    }

    public TouchPointModel getTouchPointModel() {
        return touchPointModel;
    }

    public void setTouchPointModel(TouchPointModel touchPointModel) {
        this.touchPointModel = touchPointModel;
    }
    
   
     public TouchPointListModel addTouchPoint() {
         System.out.println("inside addTouchPoint "+touchPointModel.createCopy().getTouchPointName());
         
        touchPointListModel.getTouchPointListModel().add(touchPointModel.createCopy());
         System.out.println("in list "+touchPointListModel.getTouchPointListModel().size());
        
        touchPointFlowView.addElement();
         System.out.println("after addition of element ");
         return touchPointListModel;
     }
       
}
