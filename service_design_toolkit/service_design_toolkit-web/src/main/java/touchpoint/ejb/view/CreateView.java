/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.view;

import common.ejb.business.ServiceFactory;
import common.view.AbstractView;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import journey.dto.TouchPointDTO;
import touchpoint.controller.TouchPointController;

/**
 *
 * @author longnguyen
 */
@Named(value = "touchpoint_ejb_view_CreateView")
@ViewScoped
public class CreateView extends AbstractView implements Serializable {
    @EJB
    ServiceFactory serviceFactory;
    
    private TouchPointDTO touchPointDTO;

    /**
     * Creates a new instance of CreateView
     */
    public CreateView() {
    }

    public TouchPointDTO getTouchPointDTO() {
        return touchPointDTO;
    }

    public void setTouchPointDTO(TouchPointDTO touchPointDTO) {
        this.touchPointDTO = touchPointDTO;
    }

    @Override
    public void initController() {
        controller = new TouchPointController(this);
    }

    @Override
    public void initData() {
        touchPointDTO = new TouchPointDTO();        
    }    

    @Override
    public ServiceFactory getServices() {
        return serviceFactory;
    }
}
