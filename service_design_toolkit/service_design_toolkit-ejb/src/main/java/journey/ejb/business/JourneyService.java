/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.business;

import common.dto.QueryParamValue;
import journey.ejb.eao.JourneyFacadeLocal;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import journey.dto.TouchPointDTO;
import journey.ejb.eao.JourneyFieldResearcherFacadeLocal;
import journey.entity.Journey;
import journey.entity.JourneyFieldResearcher;
import journey.entity.TouchPoint;
import org.apache.commons.beanutils.BeanUtils;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;
import user.ejb.business.UserServiceLocal;
import user.entity.FieldResearcher;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
@Stateless
public class JourneyService implements JourneyServiceLocal {

    @EJB
    private JourneyFieldResearcherFacadeLocal journeyFieldResearcherFacade;

    @EJB
    private JourneyFacadeLocal journeyFacade;
    
    @EJB
    private UserServiceLocal userService;
    
    @Override
    public JourneyListDTO getJourneyList(JourneyDTO content) {
        JourneyListDTO journeyListDTO = new JourneyListDTO();        
        for (Journey journey: journeyFacade.findListOfJourneyByIsActive(content.getIsActive())) {                        
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
    
    @Override
    public void createJourney(JourneyDTO journeyDTO) {
        try {
            Journey journey = new Journey();
            BeanUtils.copyProperties(journey, journeyDTO);
            List<TouchPoint> touchPointList = new ArrayList<>();
            for (TouchPointDTO touchPointDTO: journeyDTO.getTouchPointDTOList()) {
                TouchPoint touchPoint = new TouchPoint();
                BeanUtils.copyProperties(touchPoint, touchPointDTO);
                touchPoint.setJourneyId(journey);
                touchPointList.add(touchPoint);
            }
            journey.setTouchPointList(touchPointList);
            
            journeyFacade.create(journey);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);              
        }        
    }
    
    @Override
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

    @Override
    public JourneyDTO getJourneyByName(JourneyDTO journeyDTO) {
        Journey journey = journeyFacade.findSingleByQueryName("Journey.findByJourneyName", 
                new QueryParamValue[] {new QueryParamValue("journeyName", journeyDTO.getJourneyName())});       
        if (null == journey) {
            return null;
        }
        try {
            BeanUtils.copyProperties(journeyDTO, journey);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return journeyDTO;
    }

    @Override
    public String registerFieldResearcherWithJourney(JourneyFieldResearcherDTO journeyFieldResearcherDTO) {
        JourneyFieldResearcher journeyFieldResearcher = new JourneyFieldResearcher();
        Journey journey = journeyFacade.findJourneyByName(journeyFieldResearcherDTO.getJourneyDTO().getJourneyName());        
        FieldResearcher fieldResearcher = userService.getFieldResearcherByName(journeyFieldResearcherDTO.getFieldResearcherDTO());
        
        journeyFieldResearcher.setJourneyId(journey);
        journeyFieldResearcher.setFieldResearcherId(fieldResearcher);
        
        journeyFieldResearcherFacade.create(journeyFieldResearcher);
        
        return "OK";
    }

    @Override
    public List<FieldResearcherDTO> getRegisteredFieldResearchersByJourneyName(JourneyDTO journeyDTO) {
        Journey journey = journeyFacade.findJourneyByName(journeyDTO.getJourneyName());
        List<FieldResearcherDTO> fieldResearcherDTOList = new ArrayList<>();
        for (JourneyFieldResearcher journeyFieldResearcher : journey.getJourneyFieldResearcherList()) {
            FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();                                
            FieldResearcher fieldResearcher = journeyFieldResearcher.getFieldResearcherId();
            
            SdtUserDTO sdtUserDTO = new SdtUserDTO();
            SdtUser sdtUser = fieldResearcher.getSdtUser();
            try {
                BeanUtils.copyProperties(fieldResearcherDTO, fieldResearcher);
                BeanUtils.copyProperties(sdtUserDTO, sdtUser);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
            fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
            fieldResearcherDTOList.add(fieldResearcherDTO);
        }
        return fieldResearcherDTOList;
    }
}
