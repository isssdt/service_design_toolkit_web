/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import common.EJBTestInjector;
import common.exception.CustomReasonPhraseException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import user.dto.FieldResearcherDTO;
import user.ejb.eao.FieldResearcherFacadeLocal;
import user.ejb.eao.SdtUserFacadeLocal;
import user.ejb.eao.UserRoleFacadeLocal;
import user.entity.FieldResearcher;
import user.entity.SdtUser;

/**
 *
 * @author dingyi
 */
public class UserServiceTest {
    
    UserService userService;
    UserRoleFacadeLocal userRoleFacade;
    SdtUserFacadeLocal sdtUserFacade;
    FieldResearcherFacadeLocal fieldResearcherFacade;
    
    public UserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{
        this.userService = new UserService();
        this.userRoleFacade = Mockito.mock(UserRoleFacadeLocal.class);
        this.sdtUserFacade = Mockito.mock(SdtUserFacadeLocal.class);
        this.fieldResearcherFacade = Mockito.mock(FieldResearcherFacadeLocal.class);
        
        final EJBTestInjector injector = new EJBTestInjector();
        injector.assign(FieldResearcherFacadeLocal.class, fieldResearcherFacade);
        injector.assign(UserRoleFacadeLocal.class, userRoleFacade);
        injector.assign(SdtUserFacadeLocal.class, sdtUserFacade);
        
        injector.inject(userService);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testgetFieldResearcherByName () throws CustomReasonPhraseException {
        FieldResearcher result = new FieldResearcher();
        SdtUser sdtUser = new SdtUser();        
        result.setSdtUser(sdtUser);
        sdtUser.setUsername("un");
        sdtUser.setFieldResearcher(result);
        
        user.dto.FieldResearcherDTO fieldResearcherDTO = new user.dto.FieldResearcherDTO();
        user.dto.SdtUserDTO sdtUserDTO = new user.dto.SdtUserDTO();
        sdtUserDTO.setUsername(sdtUser.getUsername());
        fieldResearcherDTO.setSdtUserDTO(sdtUserDTO);
        
        
        Map<String, Object> params = new HashMap<>();
        params.put("username", fieldResearcherDTO.getSdtUserDTO().getUsername());
        Mockito.when(sdtUserFacade.findSingleByQueryName("SdtUser.findByUsername", params)).thenReturn(sdtUser);
        FieldResearcherDTO fieldResearcher = userService.getFieldResearcherByName(fieldResearcherDTO);
        Assert.assertEquals("un", fieldResearcher.getSdtUserDTO().getUsername());
    }
 
}
