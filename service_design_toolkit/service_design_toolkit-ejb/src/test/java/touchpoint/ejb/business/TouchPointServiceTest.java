/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.business;

import common.EJBTestInjector;
import common.constant.ConstantValues;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.rest.dto.RESTReponse;
import java.util.ArrayList;
import java.util.List;
import journey.dto.RatingDTO;
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.eao.RatingFacadeLocal;
import journey.ejb.eao.TouchPointFieldResearcherFacadeLocal;
import journey.entity.Rating;
import journey.entity.TouchpointFieldResearcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author longnguyen
 */
public class TouchPointServiceTest {
    TouchPointService touchPointService;
    EAOFactory factory;    
    TouchPointFieldResearcherFacadeLocal touchPointFieldResearcherFacade;
    RatingFacadeLocal ratingFacade;
    
    public TouchPointServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        touchPointService = new TouchPointService();
        factory = new EAOFactory();
        touchPointFieldResearcherFacade = Mockito.mock(TouchPointFieldResearcherFacadeLocal.class);
        ratingFacade = Mockito.mock(RatingFacadeLocal.class);
        
        // inject
        final EJBTestInjector injector = new EJBTestInjector();
        injector.assign(TouchPointFieldResearcherFacadeLocal.class, touchPointFieldResearcherFacade);
        injector.assign(RatingFacadeLocal.class, ratingFacade);

        injector.inject(factory);        
        injector.assign(EAOFactory.class, factory);   
        injector.inject(touchPointService);
    }
    
    @After
    public void tearDown() {
    }    
    
    @Test
    public void testSaveResponse() throws AppException {
        //create rating
        Rating rating = new Rating(1);
        rating.setValue("1");              
        
        //create 2 TouchPointFieldResearcher
        TouchpointFieldResearcher touchpointFieldResearcher1 = new TouchpointFieldResearcher(1);                
        touchpointFieldResearcher1.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        
        TouchpointFieldResearcher touchpointFieldResearcher2 = new TouchpointFieldResearcher(2);                    
        touchpointFieldResearcher2.setStatus(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_IN_PROGRESS);
        
        //add 2nd TouchPointFieldResearcher to list as the first TouchPointFieldResearcher will be updated first
        List<TouchpointFieldResearcher> touchpointFieldResearcherList = new ArrayList<>();        
        touchpointFieldResearcherList.add(touchpointFieldResearcher2);
        
        //create Rating DTO
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setValue("1");                         

        //create TouchPointFieldResearcherDTO
        TouchPointFieldResearcherDTO touchPointFieldResearcherDTO = new TouchPointFieldResearcherDTO();        
        touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);
        touchPointFieldResearcherDTO.setComments("1");
        touchPointFieldResearcherDTO.setReaction("1");
        
        //mocking the findByTouchpointIdAndFieldResearcherName of TouchPointFieldResearcherFacade
        Mockito.when(touchPointFieldResearcherFacade.findByTouchpointIdAndFieldResearcherName(touchPointFieldResearcherDTO))
                .thenReturn(touchpointFieldResearcher1);
        
        //mocking the findRatingByValue of RatingFacade
        Mockito.when(ratingFacade.findRatingByValue("1")).thenReturn(rating);
        
        //mocking the findByStatusAndFieldResearcherName of TouchPointFieldResearcherFacade
        Mockito.when(touchPointFieldResearcherFacade.findByStatusAndFieldResearcherName(touchPointFieldResearcherDTO))
                .thenReturn(touchpointFieldResearcherList);
        
        //mocking the edit of TouchPointFieldResearcherFacade
        Mockito.doNothing().when(touchPointFieldResearcherFacade).edit(touchpointFieldResearcher1);        
        
        RESTReponse reponse = touchPointService.saveResponse(touchPointFieldResearcherDTO);
        
        //check value of TouchPointFieldResearcher1
        assertEquals("1", touchpointFieldResearcher1.getComments());
        assertEquals("1", touchpointFieldResearcher1.getReaction());
        assertEquals("1", touchpointFieldResearcher1.getRatingId().getValue());
        assertEquals(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE, touchpointFieldResearcher1.getStatus());
        
        //check value of response
        assertEquals(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_RESPONSE_UPDATE_SUCCESSFUL, reponse.getMessage());       
        
        //update TouchPointFieldResearcherDTO        
        touchPointFieldResearcherDTO.setRatingDTO(ratingDTO);
        touchPointFieldResearcherDTO.setComments("2");
        touchPointFieldResearcherDTO.setReaction("2");
        
        //clear the list as 2nd TouchPointFieldResearcher will be updated now
        touchpointFieldResearcherList.clear();
        
        //mocking the findByTouchpointIdAndFieldResearcherName of TouchPointFieldResearcherFacade
        Mockito.when(touchPointFieldResearcherFacade.findByTouchpointIdAndFieldResearcherName(touchPointFieldResearcherDTO))
                .thenReturn(touchpointFieldResearcher2);
        
        reponse = touchPointService.saveResponse(touchPointFieldResearcherDTO);
        
        //check value of TouchPointFieldResearcher2
        assertEquals("2", touchpointFieldResearcher2.getComments());
        assertEquals("2", touchpointFieldResearcher2.getReaction());
        assertEquals("1", touchpointFieldResearcher1.getRatingId().getValue());
        assertEquals(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_STATUS_DONE, touchpointFieldResearcher2.getStatus());
        
        //check value of response
        assertEquals(ConstantValues.TOUCH_POINT_FIELD_RESEARCHER_RESPONSE_COMPLETE_JOURNEY, reponse.getMessage());   
        
        //try to update rating of TouchPointFieldResearcher2
        ratingDTO.setValue("2");        
        touchPointService.saveResponse(touchPointFieldResearcherDTO);
        
        //check value of TouchPointFieldResearcher2
        assertEquals("1", touchpointFieldResearcher1.getRatingId().getValue());
    }
}
