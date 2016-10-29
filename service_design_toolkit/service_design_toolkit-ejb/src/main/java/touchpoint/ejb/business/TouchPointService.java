/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.business;

import common.constant.ConstantValues;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.exception.Utils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import journey.dto.ChannelDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
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
    public List<TouchPointFieldResearcherDTO> getTouchPointListOfRegisteredJourney(SdtUserDTO sdtUserDTO)
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
        
        return touchPointFieldResearcherDTOList;
    }
    
    @Override
    public String saveResponse(TouchPointFieldResearcherDTO touchpointFieldResearcherDTO) throws AppException {
        TouchpointFieldResearcher touchpointFieldResearcher = factory.getTouchPointFieldResearcherFacade()
                .findByTouchpointIdAndFieldResearcherName(touchpointFieldResearcherDTO);
        
        if (null == touchpointFieldResearcher) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 404, ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_NON_EXISTS_ERROR, 
                ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_NON_EXISTS_ERROR_DEV_INFO, ConstantValues.BLOG_POST_URL);
        }
        
        if (ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE.equals(touchpointFieldResearcher.getStatus())) {
            throw new AppException(Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getStatusCode(), 
                    ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_ALREADY_DONE_ERROR, 
                    ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_ALREADY_DONE_ERROR_DEV_INFO, ConstantValues.BLOG_POST_URL);
        }
        
        touchpointFieldResearcher.setRatingId(factory.getRatingFacade().findRatingByValue(touchpointFieldResearcherDTO.getRatingDTO().getValue()));
        touchpointFieldResearcher.setComments(touchpointFieldResearcherDTO.getComments());
        touchpointFieldResearcher.setReaction(touchpointFieldResearcherDTO.getReaction());
        touchpointFieldResearcher.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE);
        
        factory.getTouchPointFieldResearcherFacade().edit(touchpointFieldResearcher);
        
        touchpointFieldResearcherDTO.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = factory.getTouchPointFieldResearcherFacade()
                .findByStatusAndFieldResearcherName(touchpointFieldResearcherDTO);
        if (null == touchpointFieldResearcherList || touchpointFieldResearcherList.isEmpty()) {
            return "Please informed that you have completed work for all Touch Points";
        }        

        return "A new research work has been created";
    }
}
