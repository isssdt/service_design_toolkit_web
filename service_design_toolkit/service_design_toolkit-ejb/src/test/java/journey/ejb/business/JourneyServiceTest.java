/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.business;

import common.EJBTestInjector;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import journey.dto.ChannelDTO;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import journey.entity.Channel;
import journey.entity.Journey;
import journey.entity.Rating;
import journey.entity.TouchPoint;
import journey.entity.TouchpointFieldResearcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import touchpoint.dto.TouchPointFieldResearcherListDTO;
import user.dto.SdtUserDTO;
import user.ejb.eao.SdtUserFacadeLocal;
import user.entity.FieldResearcher;
import user.entity.SdtUser;

/**
 *
 * @author longnguyen
 */
public class JourneyServiceTest {

    JourneyService journeyService;
    EAOFactory factory;
    JourneyFacadeLocal journeyFacade;
    ChannelFacadeLocal channelFacade;
    TouchPointFacadeLocal touchPointFacade;
    RatingFacadeLocal ratingFacade;
    SdtUserFacadeLocal sdtUserFacade;
    TouchPointFieldResearcherFacadeLocal touchPointFieldResearcherFacade;

    public JourneyServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        this.journeyService = new JourneyService();
        this.factory = new EAOFactory();
        this.journeyFacade = Mockito.mock(JourneyFacadeLocal.class);
        this.channelFacade = Mockito.mock(ChannelFacadeLocal.class);
        this.touchPointFacade = Mockito.mock(TouchPointFacadeLocal.class);
        this.ratingFacade = Mockito.mock(RatingFacadeLocal.class);
        this.sdtUserFacade = Mockito.mock(SdtUserFacadeLocal.class);
        this.touchPointFieldResearcherFacade = Mockito.mock(TouchPointFieldResearcherFacadeLocal.class);

        // inject
        final EJBTestInjector injector = new EJBTestInjector();
        injector.assign(JourneyFacadeLocal.class, journeyFacade);
        injector.assign(ChannelFacadeLocal.class, channelFacade);
        injector.assign(TouchPointFacadeLocal.class, touchPointFacade);
        injector.assign(RatingFacadeLocal.class, ratingFacade);
        injector.assign(SdtUserFacadeLocal.class, sdtUserFacade);
        injector.assign(TouchPointFieldResearcherFacadeLocal.class, touchPointFieldResearcherFacade);
        injector.inject(factory);
        
        injector.assign(EAOFactory.class, factory);
        injector.inject(journeyService);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void testGetRegisteredFieldResearchersByJourneyName () {        
    }
    
    @Test
    public void testGetJourneyByName () {
        Journey journey = new Journey();
        journey.setJourneyName("jn");
       
        journey.dto.JourneyDTO journeyDTO = new journey.dto.JourneyDTO();
        journeyDTO.setJourneyName(journey.getJourneyName());
        
        Mockito.when(journeyFacade.findJourneyByName(journeyDTO)).thenReturn(journey);
        Assert.assertSame("jn",journeyService.getJourneyByName(journeyDTO).getJourneyName());
    }
    
    @Test
    public void testGetAllJourney () throws AppException, CustomReasonPhraseException {
        
        Journey journey1 = new Journey(1);
        journey1.setJourneyName("journeyname");
        Journey journey2 = new Journey(2);
        
        List<Journey> result = new ArrayList<>();
        result.add(journey1);
        result.add(journey2);
        Mockito.when(journeyFacade.findAll()).thenReturn(result); 
        Assert.assertEquals(2, journeyService.getAllJourney().size());
        Assert.assertEquals("journeyname", journeyService.getAllJourney().get(0).getJourneyName());
        
    }
    
    @Test
    public void testGetChannelList() {
        Channel channel1 = new Channel(1);
        channel1.setChannelName("Channel 1");
        Channel channel2 = new Channel(2);

        List<Channel> result = new ArrayList<>();
        result.add(channel1);
        result.add(channel2);
        Mockito.when(channelFacade.findAll()).thenReturn(result);
        Assert.assertEquals(2, journeyService.getChannelList().getChannelDTOList().size());
        Assert.assertEquals("Channel 1", journeyService.getChannelList().getChannelDTOList().get(0).getChannelName());
    }

    @Test
    public void testCreateJourney() throws CustomReasonPhraseException {  
        Date startDate = new Date();
        Date endDate = new Date();
        
        //create JourneyDTO
        JourneyDTO journeyDTO = new JourneyDTO("Journey Name", 10, 'Y', startDate, endDate, 'Y', "Description");       
        
        //create ChannelDTO
        ChannelDTO channelDTO = new ChannelDTO("Channel"); 
        
        //create 1st TouchPointDTO                
        TouchPointDTO touchPointDTO_1 = new TouchPointDTO(1, "1", "1", "1", "1", "1", "1", channelDTO, journeyDTO); 
        
        //create 2st TouchPointDTO                      
        TouchPointDTO touchPointDTO_2 = new TouchPointDTO(2, "2", "2", "2", "2", "2", "2", channelDTO, journeyDTO);
        
        //add 2 TouchPointDTO to list
        List<TouchPointDTO> touchPointDTOList = new ArrayList<>();
        touchPointDTOList.add(touchPointDTO_1);
        touchPointDTOList.add(touchPointDTO_2);
        
        journeyDTO.setTouchPointDTOList(touchPointDTOList);       
    }
    
    /**
     * Test the method getTouchPointFiedlResearcherListOfJourney of JourneyService class
     */
    @Test
    public void testGetTouchPointFiedlResearcherListOfJourney() {
        //create 2 Rating
        Rating rating1 = new Rating(1);
        rating1.setValue("1");
        
        Rating rating2 = new Rating(2);
        rating2.setValue("2");
        
        //create 2 TouchPoint
        TouchPoint touchPoint1 = new TouchPoint(1);
        touchPoint1.setTouchPointDesc("1");
        
        TouchPoint touchPoint2 = new TouchPoint(2);
        touchPoint2.setTouchPointDesc("2");
        
        //create 2 SdtUser
        SdtUser sdtUser1 = new SdtUser(1);
        sdtUser1.setUsername("1");
        
        SdtUser sdtUser2 = new SdtUser(2);
        sdtUser2.setUsername("2");
        
        //create 2 FieldResearcher
        FieldResearcher fieldResearcher1 = new FieldResearcher(1);
        fieldResearcher1.setCurrentLatitude("1");
        fieldResearcher1.setSdtUser(sdtUser1);     
        
        FieldResearcher fieldResearcher2 = new FieldResearcher(2);
        fieldResearcher2.setCurrentLatitude("2");
        fieldResearcher2.setSdtUser(sdtUser2);
        
        //create 2 TouchPointFieldResearcher
        TouchpointFieldResearcher touchpointFieldResearcher1 = new TouchpointFieldResearcher(1);
        touchpointFieldResearcher1.setComments("1");
        touchpointFieldResearcher1.setFieldResearcherId(fieldResearcher1);
        touchpointFieldResearcher1.setRatingId(rating1);
        touchpointFieldResearcher1.setTouchpointId(touchPoint1);
        
        TouchpointFieldResearcher touchpointFieldResearcher2 = new TouchpointFieldResearcher(2);
        touchpointFieldResearcher2.setComments("2");
        touchpointFieldResearcher2.setFieldResearcherId(fieldResearcher2);
        touchpointFieldResearcher2.setRatingId(rating2);
        touchpointFieldResearcher2.setTouchpointId(touchPoint2);     
        
        //add those 2 TouchPointFieldResearcher to list
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = new ArrayList<>();
        touchpointFieldResearcherList.add(touchpointFieldResearcher1);
        touchpointFieldResearcherList.add(touchpointFieldResearcher2);
        
        //create a JourneyDTO
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName("NUS Bus");
        
        //mocking the findByJourneyName of TouchPointFieldResearcherFacadeLocal
        Mockito.when(touchPointFieldResearcherFacade.findByJourneyName(journeyDTO)).thenReturn(touchpointFieldResearcherList);
        
        //call getTouchPointFiedlResearcherListOfJourney method
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        //size of TouchPointFieldResearcherDTOList should be equal to size of TouchPointFieldResearcherList
        Assert.assertEquals(touchpointFieldResearcherList.size(), touchPointFieldResearcherDTOList.size());
        
        //get 2 DTO from TouchPointFieldResearcherDTOList
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO1 = touchPointFieldResearcherDTOList.get(0);
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO2 = touchPointFieldResearcherDTOList.get(1);
        
        //comment of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getComments(), touchPointFieldResearcherDTO1.getComments());
        Assert.assertEquals(touchpointFieldResearcher2.getComments(), touchPointFieldResearcherDTO2.getComments());
        
        //rating of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getRatingId().getValue(), touchPointFieldResearcherDTO1.getRatingDTO().getValue());
        Assert.assertEquals(touchpointFieldResearcher2.getRatingId().getValue(), touchPointFieldResearcherDTO2.getRatingDTO().getValue());
        
        //Touch Point description of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getTouchpointId().getTouchPointDesc(), 
                touchPointFieldResearcherDTO1.getTouchpointDTO().getTouchPointDesc());
        Assert.assertEquals(touchpointFieldResearcher2.getTouchpointId().getTouchPointDesc(), 
                touchPointFieldResearcherDTO2.getTouchpointDTO().getTouchPointDesc());
        
        //username of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getFieldResearcherId().getSdtUser().getUsername(), 
                touchPointFieldResearcherDTO1.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        Assert.assertEquals(touchpointFieldResearcher2.getFieldResearcherId().getSdtUser().getUsername(), 
                touchPointFieldResearcherDTO2.getFieldResearcherDTO().getSdtUserDTO().getUsername());
        
        //latitude of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getFieldResearcherId().getCurrentLatitude(), 
                touchPointFieldResearcherDTO1.getFieldResearcherDTO().getCurrentLatitude());
        Assert.assertEquals(touchpointFieldResearcher2.getFieldResearcherId().getCurrentLatitude(), 
                touchPointFieldResearcherDTO2.getFieldResearcherDTO().getCurrentLatitude());  
    }
    
    @Test
    public void testGetTouchPointFiedlResearcherListByJourneyNameAndUsername() {
        //create 2 Rating
        Rating rating1 = new Rating(1);
        rating1.setValue("1");
        
        Rating rating2 = new Rating(2);
        rating2.setValue("2");
        
        //create 2 TouchPoint
        TouchPoint touchPoint1 = new TouchPoint(1);
        touchPoint1.setTouchPointDesc("1");
        
        TouchPoint touchPoint2 = new TouchPoint(2);
        touchPoint2.setTouchPointDesc("2");
        
        //create 2 FieldResearcher
        FieldResearcher fieldResearcher1 = new FieldResearcher(1);
        fieldResearcher1.setCurrentLatitude("1");            
        
        FieldResearcher fieldResearcher2 = new FieldResearcher(2);
        fieldResearcher2.setCurrentLatitude("2");        
        
        //create 2 TouchPointFieldResearcher
        TouchpointFieldResearcher touchpointFieldResearcher1 = new TouchpointFieldResearcher(1);
        touchpointFieldResearcher1.setComments("1");
        touchpointFieldResearcher1.setFieldResearcherId(fieldResearcher1);
        touchpointFieldResearcher1.setRatingId(rating1);
        touchpointFieldResearcher1.setTouchpointId(touchPoint1);
        
        TouchpointFieldResearcher touchpointFieldResearcher2 = new TouchpointFieldResearcher(2);
        touchpointFieldResearcher2.setComments("2");
        touchpointFieldResearcher2.setFieldResearcherId(fieldResearcher2);
        touchpointFieldResearcher2.setRatingId(rating2);
        touchpointFieldResearcher2.setTouchpointId(touchPoint2);     
        
        //add those 2 TouchPointFieldResearcher to list
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = new ArrayList<>();
        touchpointFieldResearcherList.add(touchpointFieldResearcher1);
        touchpointFieldResearcherList.add(touchpointFieldResearcher2);
        
        //create a JourneyDTO
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName("NUS Bus");
        
        //create a SdtUsesDTO
        SdtUserDTO sdtUserDTO = new SdtUserDTO();
        sdtUserDTO.setUsername("Long Nguyen");
        
        //mocking the findByJourneyName of TouchPointFieldResearcherFacadeLocal
        Mockito.when(touchPointFieldResearcherFacade.findByJourneyNameAndUsername(journeyDTO, sdtUserDTO)).thenReturn(touchpointFieldResearcherList);
        
        //call getTouchPointFiedlResearcherListOfJourney method
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = 
                journeyService.getTouchPointFiedlResearcherListByJourneyNameAndUsername(journeyDTO, sdtUserDTO);
        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        //size of TouchPointFieldResearcherDTOList should be equal to size of TouchPointFieldResearcherList
        Assert.assertEquals(touchpointFieldResearcherList.size(), touchPointFieldResearcherDTOList.size());
        
        //get 2 DTO from TouchPointFieldResearcherDTOList
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO1 = touchPointFieldResearcherDTOList.get(0);
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO2 = touchPointFieldResearcherDTOList.get(1);
        
        //comment of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getComments(), touchPointFieldResearcherDTO1.getComments());
        Assert.assertEquals(touchpointFieldResearcher2.getComments(), touchPointFieldResearcherDTO2.getComments());
        
        //rating of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getRatingId().getValue(), touchPointFieldResearcherDTO1.getRatingDTO().getValue());
        Assert.assertEquals(touchpointFieldResearcher2.getRatingId().getValue(), touchPointFieldResearcherDTO2.getRatingDTO().getValue());
        
        //Touch Point description of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getTouchpointId().getTouchPointDesc(), 
                touchPointFieldResearcherDTO1.getTouchpointDTO().getTouchPointDesc());
        Assert.assertEquals(touchpointFieldResearcher2.getTouchpointId().getTouchPointDesc(), 
                touchPointFieldResearcherDTO2.getTouchpointDTO().getTouchPointDesc());
        
        //latitude of those DTO should be equal to the one of entity
        Assert.assertEquals(touchpointFieldResearcher1.getFieldResearcherId().getCurrentLatitude(), 
                touchPointFieldResearcherDTO1.getFieldResearcherDTO().getCurrentLatitude());
        Assert.assertEquals(touchpointFieldResearcher2.getFieldResearcherId().getCurrentLatitude(), 
                touchPointFieldResearcherDTO2.getFieldResearcherDTO().getCurrentLatitude());        
        
    }
}