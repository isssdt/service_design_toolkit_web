/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.business;

import common.constant.ConstantValues;
import common.rest.dto.RESTReponse;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.exception.Utils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import journey.dto.ChannelDTO;
import journey.dto.ChannelListDTO;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import journey.dto.RatingDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.entity.Channel;
import journey.entity.Journey;
import journey.entity.JourneyFieldResearcher;
import journey.entity.TouchPoint;
import journey.entity.TouchpointFieldResearcher;
import org.apache.commons.beanutils.BeanUtils;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;
import user.entity.FieldResearcher;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
@Stateless
public class JourneyService implements JourneyServiceLocal {

    @EJB
    private EAOFactory factory;

    @Override
    public Integer createJourney(JourneyDTO journeyDTO) throws CustomReasonPhraseException {

        Journey journey = new Journey();
        try {
            BeanUtils.copyProperties(journey, journeyDTO);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
        }
        List<TouchPoint> touchPointList = new ArrayList<>();
        for (TouchPointDTO touchPointDTO : journeyDTO.getTouchPointDTOList()) {
            TouchPoint touchPoint = new TouchPoint();
            Channel channel = factory.getChannelFacade().findChannelByName(touchPointDTO.getChannelDTO().getChannelName());
            try {
                BeanUtils.copyProperties(touchPoint, touchPointDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }

            touchPoint.setChannelId(channel);
            touchPoint.setJourneyId(journey);
            touchPointList.add(touchPoint);
        }
        journey.setTouchPointList(touchPointList);

        return factory.getJourneyFacade().create(journey).getId();

    }

    @Override
    public JourneyDTO getJourneyByName(JourneyDTO journeyDTO) {        
        Journey journey = factory.getJourneyFacade().findJourneyByName(journeyDTO);
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
    public RESTReponse registerFieldResearcherWithJourney(JourneyFieldResearcherDTO journeyFieldResearcherDTO) throws AppException {
        journeyFieldResearcherDTO.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        JourneyFieldResearcher journeyFieldResearcher = factory.getJourneyFieldResearcherFacade().findJourneyOfFieldResearcherByStatus(journeyFieldResearcherDTO);

        if (null != journeyFieldResearcher) {  
            String message = "This Field Researcher already registered with a Journey";
            throw Utils.throwAppException(message, getClass().getName(), Response.Status.CONFLICT.getStatusCode());            
        }

        journeyFieldResearcher = factory.getJourneyFieldResearcherFacade().findJourneyByNameAndFieldResearcher(journeyFieldResearcherDTO);       
        if (null != journeyFieldResearcher && ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_DONE.equals(journeyFieldResearcher.getStatus())) {                                    
            throw Utils.throwAppException("This Journey is DONE", JourneyService.class.getName(), Response.Status.CONFLICT.getStatusCode());
        }

        SdtUser sdtUser = factory.getSdtUserFacade().findUserByUsername(journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO());  
        if (null == sdtUser) {
            String message = "Username " + journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername() + " not found";
            throw Utils.throwAppException(message, JourneyService.class.getName(), 
                    Response.Status.NOT_FOUND.getStatusCode());
        }
        
        Journey journey = factory.getJourneyFacade().findJourneyByName(journeyFieldResearcherDTO.getJourneyDTO());
        if (null == journey) {
            String message = "Journey " + journeyFieldResearcherDTO.getJourneyDTO().getJourneyName() + " not found";
            throw Utils.throwAppException(message, JourneyService.class.getName(), 
                    Response.Status.NOT_FOUND.getStatusCode());
        }

        journeyFieldResearcher = new JourneyFieldResearcher();
        journeyFieldResearcher.setJourneyId(journey);
        journeyFieldResearcher.setFieldResearcherId(sdtUser.getFieldResearcher());
        journeyFieldResearcher.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS);

        factory.getJourneyFieldResearcherFacade().create(journeyFieldResearcher);

        if (journey.getJourneyFieldResearcherList().size() + 1 == journey.getNoOfFieldResearcher()) {
            journey.setCanBeRegistered('N');
            factory.getJourneyFacade().edit(journey);
        }

        for (TouchPoint touchPoint : journey.getTouchPointList()) {
            TouchpointFieldResearcher touchpointFieldResearcher = new TouchpointFieldResearcher();
            touchpointFieldResearcher.setTouchpointId(touchPoint);
            touchpointFieldResearcher.setFieldResearcherId(sdtUser.getFieldResearcher());
            touchpointFieldResearcher.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
            factory.getTouchPointFieldResearcherFacade().create(touchpointFieldResearcher);
        }
        
        return new RESTReponse("Field Researcher " + journeyFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername() 
                + " has registered for Journey " + journeyFieldResearcherDTO.getJourneyDTO().getJourneyName());
    }

    @Override
    public List<FieldResearcherDTO> getRegisteredFieldResearchersByJourneyName(JourneyDTO journeyDTO) {
        Journey journey = factory.getJourneyFacade().findJourneyByName(journeyDTO);
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

    @Override
    public ChannelListDTO getChannelList() {
        ChannelListDTO channelListDTO = new ChannelListDTO();

        for (Channel channel : factory.getChannelFacade().findAll()) {
            try {
                ChannelDTO channelDTO = new ChannelDTO();
                BeanUtils.copyProperties(channelDTO, channel);
                channelListDTO.getChannelDTOList().add(channelDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return channelListDTO;
    }

    @Override
    public TouchPointFieldResearcherDTO getTouchPointDetails(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        TouchpointFieldResearcher touchpointFieldResearcher = new TouchpointFieldResearcher();

        Map<String, Object> params = new HashMap<>();
        params.put("username", touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        FieldResearcher fieldResearcher;

        fieldResearcher = factory.getSdtUserFacade().findSingleByQueryName("SdtUser.findByUsername", params).getFieldResearcher();

        String query = "SELECT a.* FROM touchpoint_field_researcher a "
                + "where a.field_researcher_id=? and a.touchpoint_id=? ";
        List<Object> param = new ArrayList<>();

        param.add(fieldResearcher.getSdtUser().getUsername());
        param.add(touchPointFieldResearcherDTO.getTouchpointDTO().getId());

        if (null != factory.getTouchPointFacade().findSingleByNativeQuery(query, param)) {
            touchpointFieldResearcher = factory.getTouchPointFieldResearcherFacade().findSingleByNativeQuery(query, param);
        }
        try {
            BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);

        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return touchPointFieldResearcherDTO;
    }

    @Override
    public JourneyListDTO findJourneyListForRegister() throws AppException, CustomReasonPhraseException {
        JourneyListDTO journeyListDTO = new JourneyListDTO();
        List<Journey> journeyList = factory.getJourneyFacade().findJourneyListForRegister();
        if (null == journeyList || journeyList.isEmpty()) {
            throw Utils.throwAppException("No Journey to register", getClass().getName(), Response.Status.NOT_FOUND.getStatusCode());
            
        }
        List<JourneyDTO> journeyDTOList = new ArrayList<>();
        for (Journey journey : journeyList) {
            JourneyDTO journeyDTO = new JourneyDTO();
            try {
                BeanUtils.copyProperties(journeyDTO, journey);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }
            journeyDTOList.add(journeyDTO);
        }
        journeyListDTO.setJourneyDTOList(journeyDTOList);
        return journeyListDTO;
    }

    @Override
    public List<JourneyDTO> getAllJourney() throws AppException, CustomReasonPhraseException {
        List<Journey> journeyList = factory.getJourneyFacade().findAll();
        List<JourneyDTO> journeyDTOList = new ArrayList<>();
        for (Journey journey : journeyList) {
            JourneyDTO journeyDTO = new JourneyDTO();
            try {
                BeanUtils.copyProperties(journeyDTO, journey);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }
            journeyDTOList.add(journeyDTO);
        }
        return journeyDTOList;
    }

    @Override
    public RESTReponse updateStatusOfJourneyForFieldResearcher(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
        
        JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
        journeyFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
        journeyFieldResearcherDTO.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        JourneyFieldResearcher journeyFieldResearcher = factory.getJourneyFieldResearcherFacade().findJourneyOfFieldResearcherByStatus(journeyFieldResearcherDTO);
        
        if (null == journeyFieldResearcher) {
            throw Utils.throwAppException("No IN PROGRESS Journey for this Field Researcher", getClass().getName(), Response.Status.NOT_FOUND.getStatusCode());
        }
        
        journeyFieldResearcher.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_DONE);
        factory.getJourneyFieldResearcherFacade().edit(journeyFieldResearcher);
        
        return new RESTReponse("Journey has been marked as Completed");
    }

    @Override
    public TouchPointFieldResearcherListDTO getTouchPointFiedlResearcherListOfJourney(JourneyDTO journeyDTO) {
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = new TouchPointFieldResearcherListDTO();
        
        if (null == journeyDTO || null == journeyDTO.getJourneyName() || journeyDTO.getJourneyName().isEmpty()) {            
            touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(new ArrayList<>());
            return touchPointFieldResearcherListDTO;
        }
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = factory.getTouchPointFieldResearcherFacade().findByJourneyName(journeyDTO);
        
        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = new ArrayList<>();
        Iterator<TouchpointFieldResearcher> iterator = touchpointFieldResearcherList.iterator();
        while (iterator.hasNext()) {
            TouchpointFieldResearcher touchpointFieldResearcher = iterator.next();            
            
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();            
            TouchPointDTO touchPointDTO = new TouchPointDTO();
            FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();            
            SdtUserDTO sdtUserDTO = new SdtUserDTO();
            RatingDTO ratingDTO = new RatingDTO();
            
            try {
                BeanUtils.copyProperties(touchPointDTO, touchpointFieldResearcher.getTouchpointId());
                BeanUtils.copyProperties(sdtUserDTO, touchpointFieldResearcher.getFieldResearcherId().getSdtUser());
                BeanUtils.copyProperties(fieldResearcherDTO, touchpointFieldResearcher.getFieldResearcherId());
                fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
                BeanUtils.copyProperties(ratingDTO, touchpointFieldResearcher.getRatingId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);                
                touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
                touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
                touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
            touchPointFieldResearcherDTOList.add(touchPointFieldResearcherDTO);
        }
        
        touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(touchPointFieldResearcherDTOList);
        return touchPointFieldResearcherListDTO;
    }

    @Override
    public TouchPointFieldResearcherListDTO getTouchPointFiedlResearcherListByJourneyNameAndUsername(JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO) {
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = new TouchPointFieldResearcherListDTO();
        
        if (null == journeyDTO || null == journeyDTO.getJourneyName() || journeyDTO.getJourneyName().isEmpty() ||
                null == sdtUserDTO || null == sdtUserDTO.getUsername() || sdtUserDTO.getUsername().isEmpty()) {            
            touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(new ArrayList<>());
            return touchPointFieldResearcherListDTO;
        }        
        System.out.println(journeyDTO.getJourneyName());
        System.out.println(sdtUserDTO.getUsername());
        
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = factory.getTouchPointFieldResearcherFacade()
                                                                                    .findByJourneyNameAndUsername(journeyDTO, sdtUserDTO);
        
        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = new ArrayList<>();
        Iterator<TouchpointFieldResearcher> iterator = touchpointFieldResearcherList.iterator();
        while (iterator.hasNext()) {
            TouchpointFieldResearcher touchpointFieldResearcher = iterator.next();            
            
            TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();            
            TouchPointDTO touchPointDTO = new TouchPointDTO();
            FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();                        
            RatingDTO ratingDTO = new RatingDTO();            
            
            try {
                BeanUtils.copyProperties(touchPointDTO, touchpointFieldResearcher.getTouchpointId());                
                BeanUtils.copyProperties(fieldResearcherDTO, touchpointFieldResearcher.getFieldResearcherId());                
                BeanUtils.copyProperties(ratingDTO, touchpointFieldResearcher.getRatingId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);                
                touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
                touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
                touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
            touchPointFieldResearcherDTOList.add(touchPointFieldResearcherDTO);
        }
        
        touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(touchPointFieldResearcherDTOList);
        return touchPointFieldResearcherListDTO;
    }
}
