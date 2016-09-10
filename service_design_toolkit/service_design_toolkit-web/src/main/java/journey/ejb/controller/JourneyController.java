/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.controller;

import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
import journey.ejb.business.JourneyServiceLocal;
import journey.ejb.model.JourneyModel;
import journey.ejb.model.TouchPointListModel;
import org.apache.commons.beanutils.BeanUtils;
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

    public void createJourney() {
        if (touchPointListModel.getGeoModel().getMarkers().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No Marker"));
            return;
        }
        try {
            JourneyDTO journeyDTO = new JourneyDTO();
            BeanUtils.copyProperties(journeyDTO, journeyModel);
            journeyDTO.setIsActive('Y');
            journeyDTO.setCanBeRegistered('Y');
            List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
            for (Marker marker : touchPointListModel.getGeoModel().getMarkers()) {
                TouchPointDTO touchPointDTO = new TouchPointDTO();
                touchPointDTO.setLatitude(Double.toString(marker.getLatlng().getLat()));
                touchPointDTO.setLongitude(Double.toString(marker.getLatlng().getLng()));
                touchPointDTO.setTouchPointDesc(marker.getTitle());
                touchPointDTO.setRadius(journeyModel.getRadius());
                touchPointDTOList.add(touchPointDTO);
            }
            journeyDTO.setTouchPointDTOList(touchPointDTOList);
            touchPointListModel.getGeoModel().getMarkers().clear();

            journeyService.createJourney(journeyDTO);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Journey has been created!"));
    }
}
