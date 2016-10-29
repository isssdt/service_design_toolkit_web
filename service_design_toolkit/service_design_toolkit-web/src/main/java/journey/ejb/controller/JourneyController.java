/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.controller;

import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import journey.dto.JourneyDTO;
import journey.dto.ChannelDTO;
import journey.dto.ChannelListDTO;
import journey.dto.TouchPointDTO;
import journey.ejb.business.JourneyServiceLocal;
import journey.ejb.model.JourneyModel;
import journey.ejb.model.TouchPointListModel;
import org.apache.commons.beanutils.BeanUtils;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import journey.ejb.model.TouchPointModel;
import org.primefaces.model.map.Marker;



/**
 *
 * @author longnguyen
 */
@Named(value = "journeyController")
@RequestScoped
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
    
    
    public void createJourney() {
        //if (touchPointListModel.getGeoModel().getMarkers().isEmpty()) {
        //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No Marker"));
        //    return;
        //}
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
                touchPointDTO.setRadius(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointRadius().toString());                
                channelDTO.setChannelName(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchPointChannel());
                touchPointDTO.setChannelDTO(channelDTO);
                touchPointDTO.setLatitude(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointLatitude().toString());
                touchPointDTO.setLongitude(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchpointLongitude().toString());
                touchPointDTOList.add(touchPointDTO);                
            }            
            
//            for (Marker marker : touchPointListModel.getGeoModel().getMarkers()) {
//        
//                touchPointDTO.setLatitude(Double.toString(marker.getLatlng().getLat()));
//                touchPointDTO.setLongitude(Double.toString(marker.getLatlng().getLng()));
//                touchPointDTO.setTouchPointDesc(marker.getTitle());               
//                touchPointDTOList.add(touchPointDTO);
//            }
            journeyDTO.setTouchPointDTOList(touchPointDTOList);
            touchPointListModel.getGeoModel().getMarkers().clear();
            journeyService.createJourney(journeyDTO);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Journey has been created!"));
    }
    
    public List<ChannelDTO> getChannelList() {
        channelListDTO = journeyService.getChannelList();
        if (null != channelListDTO) {
            return channelListDTO.getChannelDTOList();
        }
        return null;
    }
}
