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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import common.dto.ChannelDTO;
import common.dto.ChannelListDTO;
import common.dto.MasterDataDTO;
import journey.dto.JourneyDTO;
import user.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import common.dto.RatingDTO;
import touchpoint.dto.TouchPointDTO;
import user.dto.TouchPointFieldResearcherDTO;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.JourneyFieldResearcherFacadeLocal;
import common.entity.Channel;
import common.entity.MasterData;
import java.util.concurrent.TimeUnit;
import journey.entity.Journey;
import user.entity.JourneyFieldResearcher;
import touchpoint.entity.TouchPoint;
import user.entity.TouchpointFieldResearcher;
import org.apache.commons.beanutils.BeanUtils;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.FieldResearcherDTO;
import user.dto.JourneyFieldResearcherListDTO;
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
        journey.setIsGeo('Y');
        List<TouchPoint> touchPointList = new ArrayList<>();
        for (TouchPointDTO touchPointDTO : journeyDTO.getTouchPointListDTO().getTouchPointDTOList()) {
            TouchPoint touchPoint = new TouchPoint();
            Channel channel = factory.getChannelFacade().findChannelByName(touchPointDTO.getChannelDTO().getChannelName());

            try {
                BeanUtils.copyProperties(touchPoint, touchPointDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomReasonPhraseException(ConstantValues.GENERIC_APP_ERROR_CODE, ex.getMessage());
            }

            if ('Y' == journey.getIsGeo() && "NONE".equals(touchPointDTO.getLatitude())) {
                journey.setIsGeo('N');
            }

            touchPoint.setSequenceNo(journeyDTO.getTouchPointListDTO().getTouchPointDTOList().indexOf(touchPointDTO));
            touchPoint.setDurationUnit(new MasterData(touchPointDTO.getMasterDataDTO().getId()));
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
    public JourneyFieldResearcherDTO registerFieldResearcherWithJourney(JourneyFieldResearcherDTO journeyFieldResearcherDTO) throws AppException {
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
        
        if (journey.getJourneyFieldResearcherList().size() + 1 == journey.getNoOfFieldResearcher()) {
            journey.setCanBeRegistered('N');
            factory.getJourneyFacade().edit(journey);
        }

        journeyFieldResearcher = new JourneyFieldResearcher();
        journeyFieldResearcher.setJourneyId(journey);
        journeyFieldResearcher.setFieldResearcherId(sdtUser.getFieldResearcher());
        journeyFieldResearcher.setStatus(ConstantValues.JOURNEY_FIELD_RESEARCHER_STATUS_IN_PROGRESS);

        factory.getJourneyFieldResearcherFacade().create(journeyFieldResearcher);        

        for (TouchPoint touchPoint : journey.getTouchPointList()) {
            if (null != touchPoint.getSubSeqNo()) {
                continue;
            }
            TouchpointFieldResearcher touchpointFieldResearcher = new TouchpointFieldResearcher();
            touchpointFieldResearcher.setTouchpointId(touchPoint);
            touchpointFieldResearcher.setFieldResearcherId(sdtUser.getFieldResearcher());
            touchpointFieldResearcher.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
            touchpointFieldResearcher.setActionTime(new Date());
            factory.getTouchPointFieldResearcherFacade().create(touchpointFieldResearcher);
        }

        return journeyFieldResearcherDTO;
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
            touchPointFieldResearcherDTO.setDurationUnitDTO(new MasterDataDTO());
            touchPointDTO.setMasterDataDTO(new MasterDataDTO());

            try {
                BeanUtils.copyProperties(touchPointDTO, touchpointFieldResearcher.getTouchpointId());
                BeanUtils.copyProperties(sdtUserDTO, touchpointFieldResearcher.getFieldResearcherId().getSdtUser());
                BeanUtils.copyProperties(fieldResearcherDTO, touchpointFieldResearcher.getFieldResearcherId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO.getDurationUnitDTO(), touchpointFieldResearcher.getDurationUnit());
                BeanUtils.copyProperties(touchPointDTO.getMasterDataDTO(), touchpointFieldResearcher.getTouchpointId().getDurationUnit());
                BeanUtils.copyProperties(ratingDTO, touchpointFieldResearcher.getRatingId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);

            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
            fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
            touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
            touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
            touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);
            touchPointFieldResearcherDTO.setServerPhotoPath(ConstantValues.SERVER_PHOTO_PATH + journeyDTO.getJourneyName() + "_"
                    + touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc() + "_"
                    + touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername() + ".jpg");
            convertDurationToExpectedUnit(touchPointFieldResearcherDTO);
            touchPointFieldResearcherDTOList.add(touchPointFieldResearcherDTO);
        }

        touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(touchPointFieldResearcherDTOList);
        return touchPointFieldResearcherListDTO;
    }

    @Override
    public TouchPointFieldResearcherListDTO getTouchPointFiedlResearcherListByJourneyNameAndUsername(JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO) {
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = new TouchPointFieldResearcherListDTO();

        if (null == journeyDTO || null == journeyDTO.getJourneyName() || journeyDTO.getJourneyName().isEmpty()
                || null == sdtUserDTO || null == sdtUserDTO.getUsername() || sdtUserDTO.getUsername().isEmpty()) {
            touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(new ArrayList<>());
            return touchPointFieldResearcherListDTO;
        }

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
            touchPointFieldResearcherDTO.setDurationUnitDTO(new MasterDataDTO());
            touchPointDTO.setMasterDataDTO(new MasterDataDTO());

            try {
                BeanUtils.copyProperties(touchPointDTO, touchpointFieldResearcher.getTouchpointId());
                BeanUtils.copyProperties(fieldResearcherDTO, touchpointFieldResearcher.getFieldResearcherId());
                BeanUtils.copyProperties(ratingDTO, touchpointFieldResearcher.getRatingId());
                BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);
                BeanUtils.copyProperties(touchPointFieldResearcherDTO.getDurationUnitDTO(), touchpointFieldResearcher.getDurationUnit());
                BeanUtils.copyProperties(touchPointDTO.getMasterDataDTO(), touchpointFieldResearcher.getTouchpointId().getDurationUnit());
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
            touchPointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
            touchPointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
            touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);
            convertDurationToExpectedUnit(touchPointFieldResearcherDTO);
            touchPointFieldResearcherDTO.setServerPhotoPath(ConstantValues.SERVER_PHOTO_PATH + journeyDTO.getJourneyName() + "_"
                    + touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc() + "_"
                    + sdtUserDTO.getUsername() + ".jpg");
            touchPointFieldResearcherDTOList.add(touchPointFieldResearcherDTO);
        }

        touchPointFieldResearcherListDTO.setTouchPointFieldResearcherDTOList(touchPointFieldResearcherDTOList);
        return touchPointFieldResearcherListDTO;
    }

    @Override
    public JourneyListDTO findJourneyListForRegister(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        JourneyFacadeLocal journeyFacade = (JourneyFacadeLocal) factory.getFacade(JourneyFacadeLocal.class.toString());
        String query = "select b.* "
                + "from journey_field_researcher a, journey b, sdt_user c "
                + "where a.journey_id = b.id "
                + "and a.field_researcher_id = c.id "
                + "and c.username = ? "
                + "union "
                + "select * from journey a "
                + "where a.start_date <= ? "
                + "and a.end_date >= ? "
                + "and a.can_be_registered = 'Y'";

        List<Object> paramsList = new ArrayList<>();
        paramsList.add(sdtUserDTO.getUsername());
        paramsList.add(new Date());
        paramsList.add(new Date());

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", new Date());
        params.put("endDate", new Date());
//        List<Journey> journeyList = journeyFacade.findListByQueryName(ConstantValues.QUERY_JOURNEY_FIND_JOURNEY_THAT_CAN_BE_REGISTERED, params);
        List<Journey> journeyList = journeyFacade.findListByNativeQuery(query, paramsList);

        JourneyFieldResearcherFacadeLocal journeyFieldResearcherFacade = (JourneyFieldResearcherFacadeLocal) factory
                .getFacade(JourneyFieldResearcherFacadeLocal.class.toString());
        params.clear();
        params.put("username", sdtUserDTO.getUsername());
        List<JourneyFieldResearcher> journeyFieldResearcherList = journeyFieldResearcherFacade.findListByQueryName(
                ConstantValues.QUERY_JOURNEY_FIELD_RESEARCHER_FIND_JOURNEY_THAT_FIELD_RESEARCHER_REGISTERED, params);

        JourneyListDTO journeyListDTO = new JourneyListDTO();
        journeyListDTO.setJourneyDTOList(new ArrayList<>());
        for (Journey journey : journeyList) {
            JourneyDTO journeyDTO = new JourneyDTO();
            try {
                BeanUtils.copyProperties(journeyDTO, journey);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
            addJourneyFieldResearcherToJourney(journeyDTO, journeyFieldResearcherList);
            journeyListDTO.getJourneyDTOList().add(journeyDTO);
        }

        return journeyListDTO;
    }

    private void addJourneyFieldResearcherToJourney(JourneyDTO journeyDTO, List<JourneyFieldResearcher> journeyFieldResearcherList) {
        for (JourneyFieldResearcher journeyFieldResearcher : journeyFieldResearcherList) {
            JourneyFieldResearcherDTO journeyFieldResearcherDTO = new JourneyFieldResearcherDTO();
            try {
                BeanUtils.copyProperties(journeyFieldResearcherDTO, journeyFieldResearcher);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (journeyDTO.getJourneyName().equals(journeyFieldResearcher.getJourneyId().getJourneyName())) {
                if (null == journeyDTO.getJourneyFieldResearcherListDTO()) {
                    journeyDTO.setJourneyFieldResearcherListDTO(new JourneyFieldResearcherListDTO());
                    journeyDTO.getJourneyFieldResearcherListDTO().setJourneyFieldResearcherDTOList(new ArrayList<>());
                }
                journeyDTO.getJourneyFieldResearcherListDTO().getJourneyFieldResearcherDTOList().add(journeyFieldResearcherDTO);
                break;
            }
        }
    }

    private void convertDurationToExpectedUnit(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        if (null == touchPointFieldResearcherDTO.getDurationUnitDTO().getId()) {
            return;
        }
        if (touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getId().equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
            touchPointFieldResearcherDTO.setConvertedToExepectedDuration(touchPointFieldResearcherDTO.getDuration().doubleValue());
        } else if (common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID.equals(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getId())) {
            TimeUnit timeUnit = TimeUnit.MINUTES;
            if (common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID.equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
                touchPointFieldResearcherDTO.setConvertedToExepectedDuration(
                        (double) timeUnit.convert(touchPointFieldResearcherDTO.getDuration(), TimeUnit.HOURS));
            } else if (common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID.equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
                touchPointFieldResearcherDTO.setConvertedToExepectedDuration(
                        (double) timeUnit.convert(touchPointFieldResearcherDTO.getDuration(), TimeUnit.DAYS));
            }
        } else if (common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID.equals(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getId())) {
            TimeUnit timeUnit = TimeUnit.HOURS;
            if (common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID.equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
                touchPointFieldResearcherDTO.setConvertedToExepectedDuration(
                        (double) touchPointFieldResearcherDTO.getDuration() / 60);
            } else if (common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID.equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
                touchPointFieldResearcherDTO.setConvertedToExepectedDuration(
                        (double) timeUnit.convert(touchPointFieldResearcherDTO.getDuration(), TimeUnit.DAYS));
            }
        } else if (common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID.equals(touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getId())) {
            if (common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID.equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
                touchPointFieldResearcherDTO.setConvertedToExepectedDuration(
                        (double) touchPointFieldResearcherDTO.getDuration() / 60 / 24);
            } else if (common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID.equals(touchPointFieldResearcherDTO.getDurationUnitDTO().getId())) {
                touchPointFieldResearcherDTO.setConvertedToExepectedDuration(
                        (double) touchPointFieldResearcherDTO.getDuration() / 24);
            }
        }
//        System.out.println(touchPointFieldResearcherDTO.getTouchpointDTO().getTouchPointDesc() + "|" +
//                touchPointFieldResearcherDTO.getTouchpointDTO().getMasterDataDTO().getDataValue() + "|" +
//                touchPointFieldResearcherDTO.getTouchpointDTO().getDuration() + "|" +
//                touchPointFieldResearcherDTO.getDurationUnitDTO().getDataValue() + "|" +
//                touchPointFieldResearcherDTO.getDuration() + "|" +
//                touchPointFieldResearcherDTO.getConvertedToExepectedDuration());
    }
}
