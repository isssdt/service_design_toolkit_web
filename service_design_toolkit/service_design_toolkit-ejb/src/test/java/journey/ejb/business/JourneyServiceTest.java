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
import java.util.List;
import journey.dto.JourneyDTO;
import journey.ejb.eao.ChannelFacadeLocal;
import journey.ejb.eao.JourneyFacadeLocal;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import journey.entity.Channel;
import journey.entity.Journey;
import journey.entity.JourneyFieldResearcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import user.dto.FieldResearcherDTO;
import user.ejb.eao.SdtUserFacadeLocal;

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
        Journey journey = new Journey();
        journey.setJourneyName("jn");
        List<JourneyFieldResearcher> journeyFieldResearcherList = new ArrayList<>();
        journey.setJourneyFieldResearcherList(journeyFieldResearcherList);
        
        JourneyDTO journeyDTO = new JourneyDTO();
        journeyDTO.setJourneyName(journey.getJourneyName());
        //List<FieldResearcherDTO> fieldResearcherDTOList = new ArrayList<>();
        
        Mockito.when(journeyFacade.findJourneyByName(journeyDTO)).thenReturn(journey);
        Assert.assertEquals(journey.getJourneyFieldResearcherList(),journeyService.getRegisteredFieldResearchersByJourneyName(journeyDTO));
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
    public void testSaveResponse() {
//        TouchPointFieldResearcherDTO touchpointFieldResearcherDTO = new TouchPointFieldResearcherDTO();
//
//        TouchPointDTO touchPointDTO = new TouchPointDTO();
//        touchPointDTO.setId(1);
//        touchPointDTO.setTouchPointDesc("TP1");
//        touchpointFieldResearcherDTO.setTouchpointDTO(touchPointDTO);
//        TouchPoint touchPoint = new TouchPoint();
//        touchPoint.setTouchPointDesc(touchPointDTO.getTouchPointDesc());
//
//        RatingDTO ratingDTO = new RatingDTO();
//        ratingDTO.setValue("Rating");
//        touchpointFieldResearcherDTO.setRatingDTO(ratingDTO);
//        Rating rating = new Rating(1, ratingDTO.getValue());
//
//        SdtUserDTO sdtUserDTO = new SdtUserDTO();
//        sdtUserDTO.setUsername("long");
//        FieldResearcherDTO fieldResearcherDTO = new FieldResearcherDTO();
//        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
//        touchpointFieldResearcherDTO.setFieldResearcherDTO(fieldResearcherDTO);
//        SdtUser sdtUser = new SdtUser(1, sdtUserDTO.getUsername());
//
//        FieldResearcher fieldResearcher = new FieldResearcher(1);
//        sdtUser.setFieldResearcher(fieldResearcher);
//        fieldResearcher.setSdtUser(sdtUser);
//
//        Mockito.when(touchPointFacade.find(touchpointFieldResearcherDTO.getTouchpointDTO().getId())).thenReturn(touchPoint);
//        Mockito.when(ratingFacade.findRatingByValue(ratingDTO.getValue())).thenReturn(rating);
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", fieldResearcherDTO.getSdtUserDTO().getUsername());
//        Mockito.when(sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername", params)).thenReturn(sdtUser);
//        Mockito.when(touchPointFieldResearcherFacade.create(new TouchpointFieldResearcher())).thenReturn(null);
//
//        TouchpointFieldResearcher touchpointFieldResearcher;
//        touchpointFieldResearcher = journeyService.buildTouchpointFieldResearcher(touchpointFieldResearcherDTO);
//        Assert.assertEquals("TP1", touchpointFieldResearcher.getTouchpointId().getTouchPointDesc());
//        Assert.assertEquals("Rating", touchpointFieldResearcher.getRatingId().getValue());
//        Assert.assertEquals("long", touchpointFieldResearcher.getFieldResearcherId().getSdtUser().getUsername());

    }
}