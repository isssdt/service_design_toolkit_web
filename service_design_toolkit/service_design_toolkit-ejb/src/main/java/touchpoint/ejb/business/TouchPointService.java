/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.business;

import touchpoint.dto.TouchPointFieldResearcherListDTO;
import common.constant.ConstantValues;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.exception.Utils;
import common.rest.dto.RESTReponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import journey.dto.ChannelDTO;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.entity.Journey;
import journey.entity.JourneyFieldResearcher;
import journey.entity.TouchPoint;
import journey.entity.TouchpointFieldResearcher;
import org.apache.commons.beanutils.BeanUtils;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Stateless
public class TouchPointService implements TouchPointServiceLocal {
    @EJB
    EAOFactory factory;

    @Override
    public TouchPointFieldResearcherListDTO getTouchPointListOfRegisteredJourney(SdtUserDTO sdtUserDTO)
            throws AppException, CustomReasonPhraseException {
        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);        
        
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
        journeyFieldResearcherDTO.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        
        JourneyFieldResearcher journeyFieldResearcher = factory.getJourneyFieldResearcherFacade().
                findJourneyOfFieldResearcherByStatus(journeyFieldResearcherDTO);
        if (null == journeyFieldResearcher) {
            throw Utils.throwAppException("No IN PROGRESS Journey for this Field Researcher", getClass().getName(), Response.Status.NOT_FOUND.getStatusCode());
        }       
        
        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = new ArrayList<>();
        for (TouchPoint touchPoint : journeyFieldResearcher.getJourneyId().getTouchPointList()) {
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
            touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
            TouchPointDTO touchPointDTO = new TouchPointDTO();
            try {
                BeanUtils.copyProperties(touchPointDTO, touchPoint);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }
            
            ChannelDTO channelDTO = new ChannelDTO();
            try {
                BeanUtils.copyProperties(channelDTO, touchPoint.getChannelId());
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }
            touchPointDTO.setChannelDTO(channelDTO);
            
            touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
            TouchpointFieldResearcher touchpointFieldResearcher = factory.getTouchPointFieldResearcherFacade()
                    .findByTouchpointIdAndFieldResearcherName(touchPointFieldResearcherDTO);
            try {
                BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }
            
            touchPointFieldResearcherDTOList.add(touchPointFieldResearcherDTO);
        }
        
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = new TouchPointFieldResearcherListDTO();
        touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(touchPointFieldResearcherDTOList);
        return touchPointFieldResearcherListDTO;
    }
    
    @Override
    public RESTReponse saveResponse(TouchPointFieldResearcherDTO touchpointFieldResearcherDTO) throws AppException {
        TouchpointFieldResearcher touchpointFieldResearcher = factory.getTouchPointFieldResearcherFacade()
                .findByTouchpointIdAndFieldResearcherName(touchpointFieldResearcherDTO);
        
        //throws exception if there is no data in database for this Touch Point and Field Researcher
        if (null == touchpointFieldResearcher) {            
            throw Utils.throwAppException(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_ERROR_NON_EXISTS, getClass().getName(), 
                    Response.Status.NOT_FOUND.getStatusCode());
        }               
        
        //update rating only when the status of this TouchPoint is IN_PROGRESS
        if (ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS.equals(touchpointFieldResearcher.getStatus())) {
            //throws exception if there is no rating for this Touch Point
            if (null == touchpointFieldResearcherDTO.getRatingDTO() || null == touchpointFieldResearcherDTO.getRatingDTO().getValue() ||
                    touchpointFieldResearcherDTO.getRatingDTO().getValue().isEmpty()) {
                throw Utils.throwAppException(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_ERROR_NO_RATING, getClass().getName(), 
                        Response.Status.NOT_ACCEPTABLE.getStatusCode());
            }            
            touchpointFieldResearcher.setRatingId(factory.getRatingFacade().findRatingByValue(touchpointFieldResearcherDTO.getRatingDTO().getValue()));
        }
        
        //update comment and reaction for this Touch Point        
        touchpointFieldResearcher.setComments(touchpointFieldResearcherDTO.getComments());
        touchpointFieldResearcher.setReaction(touchpointFieldResearcherDTO.getReaction());
        touchpointFieldResearcher.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE);
        
        factory.getTouchPointFieldResearcherFacade().edit(touchpointFieldResearcher);
        
        //if all Touch Point of this Journey has been updated, inform that this Journey has been completed.
        touchpointFieldResearcherDTO.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = factory.getTouchPointFieldResearcherFacade()
                .findByStatusAndFieldResearcherName(touchpointFieldResearcherDTO);
        if (null == touchpointFieldResearcherList || touchpointFieldResearcherList.isEmpty()) {
            return new RESTReponse(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_RESPONSE_COMPLETE_JOURNEY);
        }        

        //if not, inform that this Touch Point has been updated successfully
        return new RESTReponse(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_RESPONSE_UPDATE_SUCCESSFUL);
    }

    @Override
    public List<TouchPointDTO> getTouchPointListJourney(JourneyDTO journeyDTO) {
        Journey journey = factory.getJourneyFacade().findJourneyByName(journeyDTO);

        List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
        for (TouchPoint touchPoint : journey.getTouchPointList()) {            
            ChannelDTO channelDTO = new ChannelDTO();
            try {
                BeanUtils.copyProperties(channelDTO, touchPoint.getChannelId());
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            TouchPointDTO touchPointDTO = new TouchPointDTO();
            try {
                BeanUtils.copyProperties(touchPointDTO, touchPoint);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
            }
            touchPointDTO.setChannelDTO(channelDTO);           
            touchPointDTOList.add(touchPointDTO);
        }
        
        return touchPointDTOList;
    }
}
