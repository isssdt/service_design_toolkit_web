/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import common.ejb.business.ServiceFactory;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import journey.controller.JourneyController;
import journey.dto.JourneyDTO;
import touchpoint.dto.TouchPointDTO;
/**
 *
 * @author Manish
 */
@Named (value = "journey_ejb_view_ConfirmationView")
@ViewScoped
public class ConfirmationView extends AbstractView implements Serializable{
    private JourneyDTO journeyDTO; 
    private TouchPointDTO touchPointDTO;

    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public TouchPointDTO getTouchPointDTO() {
        return touchPointDTO;
    }

    public void setTouchPointDTO(TouchPointDTO touchPointDTO) {
        this.touchPointDTO = touchPointDTO;
    }
    
    /**
     * Creates a new instance of ConfirmationView
     */
    public ConfirmationView() {
        super();                
    }

    @Override
    public void initController() {
        controller = new JourneyController(this);
    }

    @Override
    public void initData() {
        journeyDTO = (JourneyDTO)Utils.getAttributeOfSession(JourneyDTO.class.toString()); 
        touchPointDTO = (TouchPointDTO)Utils.getAttributeOfSession(TouchPointDTO.class.toString()); 
    }

    @Override
    public ServiceFactory getServices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
