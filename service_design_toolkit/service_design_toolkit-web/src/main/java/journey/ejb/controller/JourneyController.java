/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.controller;

import common.constant.ConstantValues;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.utils.Utils;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import journey.dto.JourneyDTO;
import journey.dto.ChannelDTO;
import journey.dto.ChannelListDTO;
import journey.dto.TouchPointDTO;
import journey.ejb.business.JourneyServiceLocal;
import journey.ejb.model.JourneyModel;
import journey.ejb.model.TouchPointListModel;
import org.apache.commons.beanutils.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;



/**
 *
 * @author longnguyen
 */
@Named(value = "journeyController")
@SessionScoped
public class JourneyController implements Serializable {

    @EJB
    private JourneyServiceLocal journeyService;

    /**
     * Creates a new instance of JourneyController
     */
    public JourneyController() {
    }

    @Inject
    private JourneyModel journeyModel;

    @Inject
    private TouchPointListModel touchPointListModel;
    
    private JourneyDTO journeyDTO = new JourneyDTO();
    
    private TouchPointDTO touchPointDTO;
    
    private ChannelDTO channelDTO  ;
    
    private ChannelListDTO channelListDTO = new ChannelListDTO();
    
    
    public Integer createJourney() throws AppException, CustomReasonPhraseException {   
        Integer journeyId = null;
        try {
            BeanUtils.copyProperties(journeyDTO, journeyModel.createCopy());
            journeyDTO.setIsActive('Y');
            journeyDTO.setCanBeRegistered('Y');
            List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
            for (int i=0; i < touchPointListModel.createCopy().getTouchPointListModel().size(); i++){
                channelDTO=new ChannelDTO();
                touchPointDTO = new TouchPointDTO();
                touchPointDTO.setTouchPointDesc(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchPointName());
                touchPointDTO.setChannelDescription(touchPointListModel.createCopy().getTouchPointListModel().get(i).getChannelDesc());                   
                touchPointDTO.setAction(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointAction());
                channelDTO.setChannelName(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchPointChannel());
                touchPointDTO.setChannelDTO(channelDTO);
              
                 if (touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointLatitude() != null){
                    System.out.println(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointRadius().toString());
                    touchPointDTO.setRadius(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointRadius().toString());
                    touchPointDTO.setLatitude(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointLatitude().toString());
                    touchPointDTO.setLongitude(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointLongitude().toString());
                    }
                else {
                    touchPointDTO.setRadius("NONE");
                    touchPointDTO.setLatitude("NONE");
                    touchPointDTO.setLongitude("NONE"); 
                    System.out.println("inside null");
                }
                touchPointDTOList.add(touchPointDTO);                
            }            
            journeyDTO.setTouchPointDTOList(touchPointDTOList);
            touchPointListModel.getGeoModel().getMarkers().clear();
            journeyId = journeyService.createJourney(journeyDTO);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return journeyId;
    }
    
    public List<ChannelDTO> getChannelList() {
        channelListDTO = journeyService.getChannelList();
        if (null != channelListDTO) {
            return channelListDTO.getChannelDTOList();
        }
        return null;
    }
    
    public void forwardToCreateJourneyPage() {
        
    }
}
