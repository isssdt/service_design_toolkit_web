/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.view;

import common.MasterData;
import common.ejb.business.ServiceFactory;
import common.view.AbstractView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import journey.dto.ChannelDTO;
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
    
    private Map<String, String> channelDropDown;

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

    public Map<String, String> getChannelDropDown() {
        return channelDropDown;
    }

    public void setChannelDropDown(Map<String, String> channelDropDown) {
        this.channelDropDown = channelDropDown;
    }

    @Override
    public void initController() {
        controller = new TouchPointController(this);
    }

    @Override
    public void initData() {
        touchPointDTO = new TouchPointDTO(); 
        touchPointDTO.setChannelDTO(new ChannelDTO());
        
        channelDropDown = new HashMap<>();
        channelDropDown.put(MasterData.CHANNEL_FACE_TO_FACE, MasterData.CHANNEL_FACE_TO_FACE);
        channelDropDown.put(MasterData.CHANNEL_KIOSK, MasterData.CHANNEL_KIOSK);
        channelDropDown.put(MasterData.CHANNEL_WEBSITE, MasterData.CHANNEL_WEBSITE);
    }    

    @Override
    public ServiceFactory getServices() {
        return serviceFactory;
    }
}
