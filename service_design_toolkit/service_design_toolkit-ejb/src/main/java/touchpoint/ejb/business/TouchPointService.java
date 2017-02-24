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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import common.dto.ChannelDTO;
import common.dto.MasterDataDTO;
import journey.dto.JourneyDTO;
import common.dto.RatingDTO;
import common.entity.Channel;
import common.entity.MasterData;
import java.util.Date;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import touchpoint.dto.TouchPointDTO;
import user.dto.TouchPointFieldResearcherDTO;
import journey.entity.Journey;
import touchpoint.entity.TouchPoint;
import user.entity.TouchpointFieldResearcher;
import org.apache.commons.beanutils.BeanUtils;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;
import user.ejb.eao.FieldResearcherFacadeLocal;
import user.entity.FieldResearcher;

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
        Map<String, Object> params = new HashMap<>();
        params.put("username", sdtUserDTO.getUsername());
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = factory.getTouchPointFieldResearcherFacade().findListByQueryName(
                ConstantValues.QUERY_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY_OF_FIELD_RESEARCHER, params);

        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = new ArrayList<>();
        for (TouchpointFieldResearcher touchpointFieldResearcher : touchpointFieldResearcherList) {
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
            FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
            fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
            TouchPointDTO touchPointDTO = new TouchPointDTO();
            touchPointDTO.setJourneyDTO(new JourneyDTO());
            touchPointDTO.setChannelDTO(new ChannelDTO());

            touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
            touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
            touchPointFieldResearcherDTO.setRatingDTO(new RatingDTO());
            touchPointFieldResearcherDTO.getTouchpointDTO().setMasterDataDTO(new MasterDataDTO());

            try {
                BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);
                BeanUtils.copyProperties(touchPointFieldResearcherDTO.getTouchpointDTO(), touchpointFieldResearcher.getTouchpointId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO(), touchpointFieldResearcher.getTouchpointId().getJourneyId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO(), touchpointFieldResearcher.getTouchpointId().getChannelId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO(), touchpointFieldResearcher.getTouchpointId().getDurationUnit());
                if (ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE.equals(touchPointFieldResearcherDTO.getStatus())) {
                    BeanUtils.copyProperties(touchPointFieldResearcherDTO.getRatingDTO(), touchpointFieldResearcher.getRatingId());
                    touchPointFieldResearcherDTO.setDurationUnitDTO(new MasterDataDTO());
                    BeanUtils.copyProperties(touchPointFieldResearcherDTO.getDurationUnitDTO(), touchpointFieldResearcher.getDurationUnit());
                }
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
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
            if (null == touchpointFieldResearcherDTO.getRatingDTO() || null == touchpointFieldResearcherDTO.getRatingDTO().getValue()
                    || touchpointFieldResearcherDTO.getRatingDTO().getValue().isEmpty()) {
                throw Utils.throwAppException(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_ERROR_NO_RATING, getClass().getName(),
                        Response.Status.NOT_ACCEPTABLE.getStatusCode());
            }
            touchpointFieldResearcher.setRatingId(factory.getRatingFacade().findRatingByValue(touchpointFieldResearcherDTO.getRatingDTO().getValue()));
            touchpointFieldResearcher.setActionTime(new Date());
        }

        //update comment and reaction for this Touch Point        
        touchpointFieldResearcher.setComments(touchpointFieldResearcherDTO.getComments());
        touchpointFieldResearcher.setReaction(touchpointFieldResearcherDTO.getReaction());
        touchpointFieldResearcher.setDuration(touchpointFieldResearcherDTO.getDuration());
        touchpointFieldResearcher.setPhotoLocation(touchpointFieldResearcherDTO.getPhotoLocation());
        String durationUnitID = common.util.Utils.getMasterDataID(touchpointFieldResearcherDTO.getDurationUnitDTO());
        if (null != durationUnitID) {
            touchpointFieldResearcher.setDurationUnit(new MasterData(durationUnitID));
        }
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
        TouchPointFacadeLocal touchPointFacadeLocal = (TouchPointFacadeLocal)factory.getFacade(TouchPointFacadeLocal.class.toString());
        Map<String, Object> params = new HashMap<>();        
        params.put("journeyName", journeyDTO.getJourneyName());
        List<TouchPoint> touchPointList = touchPointFacadeLocal.findListByQueryName(ConstantValues.QUERY_TOUCH_POINT_GET_LIST_OF_TOUCH_POINT_OF_JOURNEY, params);

        TouchPointFieldResearcherFacadeLocal touchPointFieldResearcherFacade = (TouchPointFieldResearcherFacadeLocal) 
                factory.getFacade(TouchPointFieldResearcherFacadeLocal.class.toString());
        
        
        List<Object[]> touchPointNeutralRatingList = touchPointFieldResearcherFacade.countByQueryName(
                    ConstantValues.QUERY_GET_COUNT_NEUTRAL_RATING_FOR_TOUCH_POINT_OF_JOURNEY, params);
        List<Object[]> touchPointLikeRatingList = touchPointFieldResearcherFacade.countByQueryName(
                    ConstantValues.QUERY_GET_COUNT_LIKE_RATING_FOR_TOUCH_POINT_OF_JOURNEY, params);
        List<Object[]> touchPointDislikeRatingList = touchPointFieldResearcherFacade.countByQueryName(
                    ConstantValues.QUERY_GET_COUNT_DISLIKE_RATING_FOR_TOUCH_POINT_OF_JOURNEY, params);

        List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
        for (TouchPoint touchPoint : touchPointList) {
            System.out.println(touchPoint.getTouchPointDesc());
            ChannelDTO channelDTO = new ChannelDTO();
            try {
                BeanUtils.copyProperties(channelDTO, touchPoint.getChannelId());
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
            }
            MasterDataDTO masterDataDTO = new MasterDataDTO();
            try {
                BeanUtils.copyProperties(masterDataDTO, touchPoint.getDurationUnit());
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
            touchPointDTO.setMasterDataDTO(masterDataDTO);
            setNeutralLikeDislike(touchPointDTO, touchPointNeutralRatingList, touchPointLikeRatingList, touchPointDislikeRatingList);            
            
            touchPointDTOList.add(touchPointDTO);
        }

        return touchPointDTOList;
    }
    
    private void setNeutralLikeDislike(TouchPointDTO touchPointDTO, List<Object[]> touchPointNeutralRatingList, List<Object[]> touchPointLikeRatingList, 
            List<Object[]> touchPointDislikeRatingList) {
        for (Object[] neutralRating : touchPointNeutralRatingList) {
            if (touchPointDTO.getId().equals(neutralRating[0])) {
                touchPointDTO.setNo_neutral(((Long)neutralRating[1]).intValue());
            }
        }
        for (Object[] likeRating : touchPointLikeRatingList) {
            if (touchPointDTO.getId().equals(likeRating[0])) {
                touchPointDTO.setNo_like(((Long)likeRating[1]).intValue());
            }
        }
        for (Object[] dislikeRating : touchPointDislikeRatingList) {
            if (touchPointDTO.getId().equals(dislikeRating[0])) {
                touchPointDTO.setNo_dislike(((Long)dislikeRating[1]).intValue());
            }
        }
    }

    @Override
    public RESTReponse addTouchPointToJourney(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) throws AppException, CustomReasonPhraseException {        
        JourneyFacadeLocal journeyFacadeLocal = (JourneyFacadeLocal)factory.getFacade(JourneyFacadeLocal.class.toString());
        
        Map<String, Object> params = new HashMap<>();
        params.put("journeyName", touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO().getJourneyName());
        Journey journey = journeyFacadeLocal.findSingleByQueryName(ConstantValues.QUERY_JOURNEY_FIND_JOURNEY_BY_NAME, params);
        
        ChannelFacadeLocal channelFacadeLocal = (ChannelFacadeLocal)factory.getFacade(ChannelFacadeLocal.class.toString());
        params.clear();
        params.put("channelName", touchPointFieldResearcherDTO.getTouchpointDTO().getChannelDTO().getChannelName());
        Channel channel = channelFacadeLocal.findSingleByQueryName(ConstantValues.QUERY_CHANNEL_FIND_CHANNEL_BY_NAME, params);  
        
        MasterData durationUnit = new MasterData(common.util.Utils.getMasterDataID(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO()));
        
        FieldResearcherFacadeLocal fieldResearcherFacadeLocal = (FieldResearcherFacadeLocal)factory.getFacade(FieldResearcherFacadeLocal.class.toString());
        params.clear();
        params.put("username", touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        FieldResearcher fieldResearcher = fieldResearcherFacadeLocal.findSingleByQueryName(ConstantValues.QUERY_FIELD_RESEARCHER_FIND_BY_USERNAME, params);
        
        TouchpointFieldResearcher touchpointFieldResearcher = new TouchpointFieldResearcher();
        touchpointFieldResearcher.setFieldResearcherId(fieldResearcher);
        touchpointFieldResearcher.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        touchpointFieldResearcher.setActionTime(new Date());
        
        TouchPoint touchPoint = new TouchPoint();                
        touchpointFieldResearcher.setTouchpointId(touchPoint);
        
        try {
            BeanUtils.copyProperties(touchPoint, touchPointFieldResearcherDTO.getTouchpointDTO());                        
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(TouchPointService.class.getName()).log(Level.SEVERE, null, ex);
        }        
        if (null == touchPoint.getLongitude()) {
            touchPoint.setLatitude("NONE");
            touchPoint.setLongitude("NONE");
            touchPoint.setRadius("NONE");
        }
        touchPoint.setJourneyId(journey);       
        touchPoint.setChannelId(channel);
        touchPoint.setDurationUnit(durationUnit);
        touchPoint.setTouchpointFieldResearcherList(new ArrayList<>());
        touchPoint.getTouchpointFieldResearcherList().add(touchpointFieldResearcher);               
        
        TouchPointFacadeLocal touchPointFacadeLocal = (TouchPointFacadeLocal)factory.getFacade(TouchPointFacadeLocal.class.toString());
        touchPointFacadeLocal.create(touchPoint);
        
        
        
        return new RESTReponse(touchPoint.getId().toString());
    }
}
