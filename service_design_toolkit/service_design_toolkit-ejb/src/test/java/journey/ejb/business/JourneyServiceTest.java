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
import common.dto.ChannelDTO;
import journey.dto.JourneyDTO;
import touchpoint.dto.TouchPointDTO;
import user.dto.TouchPointFieldResearcherDTO;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import common.entity.Channel;
import common.entity.MasterData;
import journey.entity.Journey;
import common.entity.Rating;
import touchpoint.entity.TouchPoint;
import user.entity.TouchpointFieldResearcher;
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
        //First TouchPointFieldResearcher
        TouchpointFieldResearcher touchpointFieldResearcher1 = new TouchpointFieldResearcher(1);        
        touchpointFieldResearcher1.setComments("1");
        
        //set Field Researcher
        touchpointFieldResearcher1.setFieldResearcherId(new FieldResearcher(1));
        touchpointFieldResearcher1.getFieldResearcherId().setCurrentLatitude("1");
        touchpointFieldResearcher1.getFieldResearcherId().setSdtUser(new SdtUser(1));
        touchpointFieldResearcher1.getFieldResearcherId().getSdtUser().setUsername("1");
        
        //set Rating
        touchpointFieldResearcher1.setRatingId(new Rating(1));
        touchpointFieldResearcher1.getRatingId().setValue("1");
        
        //set Touch Point
        touchpointFieldResearcher1.setTouchpointId(new TouchPoint(1));
        touchpointFieldResearcher1.getTouchpointId().setTouchPointDesc("1");      
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData()); 
        
        //set dummy duration unit
        touchpointFieldResearcher1.setDurationUnit(new MasterData());        
        
        //Second TouchPointFieldResearcher
        TouchpointFieldResearcher touchpointFieldResearcher2 = new TouchpointFieldResearcher(2);
        touchpointFieldResearcher2.setComments("2");
        
        //set Field Researcher
        touchpointFieldResearcher2.setFieldResearcherId(new FieldResearcher(2));
        touchpointFieldResearcher2.getFieldResearcherId().setCurrentLatitude("2");
        touchpointFieldResearcher2.getFieldResearcherId().setSdtUser(new SdtUser(2));
        touchpointFieldResearcher2.getFieldResearcherId().getSdtUser().setUsername("2");
        
        //set Rating
        touchpointFieldResearcher2.setRatingId(new Rating(2));
        touchpointFieldResearcher2.getRatingId().setValue("2");
        
        //set Touch Point
        touchpointFieldResearcher2.setTouchpointId(new TouchPoint(2));
        touchpointFieldResearcher2.getTouchpointId().setTouchPointDesc("2"); 
        touchpointFieldResearcher2.getTouchpointId().setDurationUnit(new MasterData());
        
        //set dummy duration unit
        touchpointFieldResearcher2.setDurationUnit(new MasterData());             
        
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
        
        testConvertToExpectedDuration(touchpointFieldResearcher1, journeyDTO, null);
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
        touchPoint1.setDurationUnit(new MasterData());
        
        TouchPoint touchPoint2 = new TouchPoint(2);
        touchPoint2.setTouchPointDesc("2");
        touchPoint2.setDurationUnit(new MasterData());
        
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
        touchpointFieldResearcher1.setDurationUnit(new MasterData());
        
        TouchpointFieldResearcher touchpointFieldResearcher2 = new TouchpointFieldResearcher(2);
        touchpointFieldResearcher2.setComments("2");
        touchpointFieldResearcher2.setFieldResearcherId(fieldResearcher2);
        touchpointFieldResearcher2.setRatingId(rating2);
        touchpointFieldResearcher2.setTouchpointId(touchPoint2);   
        touchpointFieldResearcher2.setDurationUnit(new MasterData());
        
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
        
        testConvertToExpectedDuration(touchpointFieldResearcher1, journeyDTO, sdtUserDTO);
        
    }
    
    private void testConvertToExpectedDuration(TouchpointFieldResearcher touchpointFieldResearcher1, JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO) {
        //test case: Expected: Day, Actual: Minute
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(45);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        TouchPointFieldResearcherListDTO touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        List<TouchPointFieldResearcherDTO> touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(45 / 60 / 24, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Day, Actual: Hour
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID));
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(2);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID)); 
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(2 / 24, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Day, Actual: Day
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(2);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(2, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Hour, Actual: Minute
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(6);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(6 / 60, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Hour, Actual: Hour
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(2);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(2, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Hour, Actual: Day
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(1);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(24, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Minute, Actual: Minute
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(2);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(2, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Minute, Actual: Hour
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(1);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_HOURS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(60, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
        
        //test case: Expected: Minute, Actual: Day
        touchpointFieldResearcher1.getTouchpointId().setDuration(1);
        touchpointFieldResearcher1.getTouchpointId().setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_MINS_ID));        
        //set duration and duration unit
        touchpointFieldResearcher1.setDuration(1);        
        touchpointFieldResearcher1.setDurationUnit(new MasterData(common.constant.MasterData.TOUCH_POINT_DURATION_DAYS_ID));
        
        //call getTouchPointFiedlResearcherListOfJourney method
        touchPointFieldResearcherListDTO = getListByID(journeyDTO, sdtUserDTO);
        touchPointFieldResearcherDTOList = touchPointFieldResearcherListDTO.getTouchPointFieldResearcherDTOList();
        
        Assert.assertEquals(60 * 24, touchPointFieldResearcherDTOList.get(0).getConvertedToExepectedDuration(), 0.0001);
    }
    
    private TouchPointFieldResearcherListDTO getListByID(JourneyDTO journeyDTO, SdtUserDTO sdtUserDTO) {
        if (null == sdtUserDTO) {
            return journeyService.getTouchPointFiedlResearcherListOfJourney(journeyDTO);
        }
        else {
            return journeyService.getTouchPointFiedlResearcherListByJourneyNameAndUsername(journeyDTO, sdtUserDTO);
        }
    }
}