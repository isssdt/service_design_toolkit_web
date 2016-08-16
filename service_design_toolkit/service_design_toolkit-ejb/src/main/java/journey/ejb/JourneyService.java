/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import journey.dto.TouchPointDTO;
import journey.entity.Journey;
import journey.entity.TouchPoint;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author longnguyen
 */
@Stateless
@LocalBean
public class JourneyService {

    @EJB
    private JourneyFacadeLocal journeyFacade;
    
    public JourneyListDTO getJourneyList() {
        JourneyListDTO journeyListDTO = new JourneyListDTO();        
        for (Journey journey: journeyFacade.findAll()) {                        
            try {
                JourneyDTO journeyDTO = new JourneyDTO();
                BeanUtils.copyProperties(journeyDTO, journey);
                journeyListDTO.getJourneyDTOList().add(journeyDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return journeyListDTO;
    }
    
    public void createJourney(JourneyDTO journeyDTO) {
        try {
            Journey journey = new Journey();
            BeanUtils.copyProperties(journey, journeyDTO);
            journeyFacade.create(journey);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);              
        }        
    }
    
    public JourneyDTO getTouchPointListOfJourney(JourneyDTO journeyDTO) {
        Journey journey = journeyFacade.findJourneyByName(journeyDTO.getJourneyName());
        for (TouchPoint touchPoint: journey.getTouchPointList()) {
            try {
                TouchPointDTO touchPointDTO = new TouchPointDTO();
                BeanUtils.copyProperties(touchPointDTO, touchPoint);
                journeyDTO.getTouchPointDTOList().add(touchPointDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return journeyDTO;
    }
}
