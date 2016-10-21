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
    
    private TouchPointDTO touchPointDTO = new TouchPointDTO();
    
    private ChannelDTO channelDTO  = new ChannelDTO();
    
    private ChannelListDTO channelListDTO = new ChannelListDTO();
    
    
    public void createJourney(ActionEvent pressSave) {
        System.out.println("inside create");
        //if (touchPointListModel.getGeoModel().getMarkers().isEmpty()) {
        //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No Marker"));
        //    return;
        //}
        try {
            
            System.out.println("inside try1");
            BeanUtils.copyProperties(journeyDTO, journeyModel.createCopy());
            for (int i=0; i< touchPointListModel.getTouchPointListModel().size(); i++) {
                System.out.println(">> Journey Touch Point Model" + touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchPointChannel());
            }
            journeyDTO.setIsActive('Y');
            journeyDTO.setCanBeRegistered('Y');
//            System.out.println("inside try2");
//            BeanUtils.copyProperties(journeyDTO.getTouchPointDTOList(), touchPointListModel.createCopy());
//            for (int i=0; i< journeyDTO.getTouchPointDTOList().size(); i++) {
//                System.out.println(">> Jounrney DTO :" + journeyDTO.getTouchPointDTOList().get(i).getTouchPointDesc());
//            }
            
            System.out.println("inside try3");
            System.out.println(journeyModel.createCopy().getJourneyName());
            for (TouchPointModel t : touchPointListModel.getTouchPointListModel()){
                System.out.println("list value "+t+ t.getTouchPointDesc());        
            }
            
            List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
            for (int i=0; i < touchPointListModel.createCopy().getTouchPointListModel().size(); i++){
                touchPointDTO.setChannelDescription(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchPointDesc());
                touchPointDTO.setTouchPointDesc(touchPointListModel.createCopy().getTouchPointListModel().get(i).getTouchPointDesc()); 
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
