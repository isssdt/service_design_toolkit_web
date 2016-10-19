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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import journey.dto.ChannelDTO;
import journey.dto.ChannelListDTO;
import journey.dto.JourneyDTO;
import journey.dto.JourneyFieldResearcherDTO;
import journey.dto.JourneyListDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFieldResearcherFacadeLocal;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import journey.entity.Channel;
import journey.entity.Journey;
import journey.entity.JourneyFieldResearcher;
import journey.entity.TouchPoint;
import journey.entity.TouchpointFieldResearcher;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import user.dto.FieldResearcherDTO;
import user.dto.SdtUserDTO;
import user.ejb.business.UserServiceLocal;
import user.ejb.eao.FieldResearcherFacadeLocal;
import user.ejb.eao.SdtUserFacadeLocal;
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
    private ChannelFacadeLocal channelFacade;

    @EJB
    private RatingFacadeLocal ratingFacade;

    @EJB
    private TouchPointFacadeLocal touchPointFacade;

    @EJB
    private SdtUserFacadeLocal sdtUserFacade;

    @EJB
    private FieldResearcherFacadeLocal fieldResearcherFacade;

    @EJB
    private TouchPointFieldResearcherFacadeLocal touchPointFieldResearcherFacade;

    @EJB
    private UserServiceLocal userService;

    @Override
    public JourneyListDTO getJourneyList(JourneyDTO content, String queryName) {
        JourneyListDTO journeyListDTO = new JourneyListDTO();
        try {
            Map<String, Object> queryParams = BeanUtilsBean.getInstance().getPropertyUtils().describe(content);
            for (Journey journey : journeyFacade.findListByQueryName(queryName, queryParams)) {
                JourneyDTO journeyDTO = new JourneyDTO();
                BeanUtils.copyProperties(journeyDTO, journey);
                journeyListDTO.getJourneyDTOList().add(journeyDTO);

            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return journeyListDTO;
    }

    @Override
    public void createJourney(JourneyDTO journeyDTO) {
        try {
            Journey journey = new Journey();
            BeanUtils.copyProperties(journey, journeyDTO);
            List<TouchPoint> touchPointList = new ArrayList<>();
            for (TouchPointDTO touchPointDTO : journeyDTO.getTouchPointDTOList()) {
                TouchPoint touchPoint = new TouchPoint();
                Channel channel = channelFacade.findChannelByName(touchPointDTO.getChannelDTO().getChannelName());
                BeanUtils.copyProperties(touchPoint, touchPointDTO);

                touchPoint.setChannelId(channel);
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
        List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
        for (TouchPoint touchPoint : journey.getTouchPointList()) {
            try {
                TouchPointDTO touchPointDTO = new TouchPointDTO();
                BeanUtils.copyProperties(touchPointDTO, touchPoint);
                touchPointDTOList.add(touchPointDTO);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        journeyDTO.setTouchPointDTOList(touchPointDTOList);
        return journeyDTO;
    }

    @Override
    public JourneyDTO getJourneyByName(JourneyDTO journeyDTO) {
        Journey journey = journeyFacade.findSingleByQueryName("Journey.findByJourneyName",
                new QueryParamValue[]{new QueryParamValue("journeyName", journeyDTO.getJourneyName())});
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
        if (journey.getNoOfFieldResearcher() <= journey.getJourneyFieldResearcherList().size()) {
            return "OK";
        }
        FieldResearcher fieldResearcher = userService.getFieldResearcherByName(journeyFieldResearcherDTO.getFieldResearcherDTO());

        String query = "select a.* from journey a, sdt_user b, journey_field_researcher c "
                + "where a.id = c.journey_id and b.id = c.field_researcher_id "
                + "and a.journey_name = ? and b.username = ?";
        List<Object> params = new ArrayList<>();
        params.add(journey.getJourneyName());
        params.add(fieldResearcher.getSdtUser().getUsername());
        if (null != journeyFacade.findSingleByNativeQuery(query, params)) {
            return "OK";
        }
        journeyFieldResearcher.setJourneyId(journey);
        journeyFieldResearcher.setFieldResearcherId(fieldResearcher);

        journeyFieldResearcherFacade.create(journeyFieldResearcher);

        if (journey.getJourneyFieldResearcherList().size() + 1 == journey.getNoOfFieldResearcher()) {
            journey.setCanBeRegistered('N');
            journeyFacade.edit(journey);
        }

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

    @Override
    public ChannelListDTO getChannelList() {
        ChannelListDTO channelListDTO = new ChannelListDTO();

        for (Channel channel : channelFacade.findAll()) {
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
    public void saveResponse(TouchPointFieldResearcherDTO touchpointFieldResearcherDTO) {
        touchPointFieldResearcherFacade.create(buildTouchpointFieldResearcher(touchpointFieldResearcherDTO));        
    }
    
    protected TouchpointFieldResearcher buildTouchpointFieldResearcher(TouchPointFieldResearcherDTO touchpointFieldResearcherDTO) {
        TouchpointFieldResearcher touchpointFieldResearcher = new TouchpointFieldResearcher();
        
        touchpointFieldResearcher.setRatingId(ratingFacade.findRatingByValue(touchpointFieldResearcherDTO.getRatingDTO().getValue()));
        touchpointFieldResearcher.setComments(touchpointFieldResearcherDTO.getComments());
        touchpointFieldResearcher.setReaction(touchpointFieldResearcherDTO.getReaction());
        touchpointFieldResearcher.setTouchpointId(touchPointFacade.find(touchpointFieldResearcherDTO.getTouchpointDTO().getId()));        
        
        Map<String, Object> params = new HashMap<>();
        params.put("username", touchpointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());       
        FieldResearcher fieldResearcher = sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername", params).getFieldResearcher();                          
        
        touchpointFieldResearcher.setFieldResearcherId(fieldResearcher);  
        
        return touchpointFieldResearcher;
    }
    
    
    @Override
    public TouchPointFieldResearcherDTO getTouchPointDetails(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        
       TouchpointFieldResearcher touchpointFieldResearcher= new TouchpointFieldResearcher();
              
        Map<String, Object> params = new HashMap<>();
        params.put("username", touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO().getUsername());       
        FieldResearcher fieldResearcher = sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername", params).getFieldResearcher();                          
        String query = "SELECT a.* FROM touchpoint_field_researcher a "
                        + "where a.field_researcher_id=? and a.touchpoint_id=? ";
        List<Object> param = new ArrayList<>();
        
        param.add(fieldResearcher.getSdtUser().getUsername());
        param.add(touchPointFieldResearcherDTO.getTouchpointDTO().getId());
       
        if (null != touchPointFacade.findSingleByNativeQuery(query, param)) {
           touchpointFieldResearcher= touchPointFieldResearcherFacade.findSingleByNativeQuery(query, param); 
        }
        try{
        BeanUtils.copyProperties(touchPointFieldResearcherDTO, touchpointFieldResearcher);
       
        } catch (IllegalAccessException | InvocationTargetException ex) {
             Logger.getLogger(JourneyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return touchPointFieldResearcherDTO;
    }


}
