/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import common.EJBTestInjector;
import common.constant.ConstantValues;
import common.ejb.eao.EAOFactory;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.dto.RESTReponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import user.dto.SdtUserDTO;
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
    EAOFactory factory;
    
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
        factory = new EAOFactory();
        this.userRoleFacade = Mockito.mock(UserRoleFacadeLocal.class);
        this.sdtUserFacade = Mockito.mock(SdtUserFacadeLocal.class);
        this.fieldResearcherFacade = Mockito.mock(FieldResearcherFacadeLocal.class);
        
        final EJBTestInjector injector = new EJBTestInjector();
        injector.assign(FieldResearcherFacadeLocal.class, fieldResearcherFacade);
        injector.assign(UserRoleFacadeLocal.class, userRoleFacade);
        injector.assign(SdtUserFacadeLocal.class, sdtUserFacade);
        
        injector.inject(factory);        
        injector.assign(EAOFactory.class, factory);
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
        Assert.assertEquals("un", userService.getFieldResearcherByName(fieldResearcherDTO).getSdtUserDTO().getUsername());
    }
    
    @Test
    public void testAuthenticate() throws AppException, CustomReasonPhraseException {
        //create SDTUser
        SdtUser sdtUser = new SdtUser("password", "username", null, null, null);
        
        //mocking the authenticate query
        Map<String, Object> params = new HashMap<>();
        params.put("username", "username");
        params.put("password", "password");
        Mockito.when(sdtUserFacade.findSingleByQueryName(ConstantValues.SDT_USER_QUERY_AUTHENTICATE, params)).thenReturn(sdtUser);
        
        //create SDTUserDTO with no username and password
        SdtUserDTO sdtUserDTO = new SdtUserDTO();        
        RESTReponse response = userService.authenticate(sdtUserDTO);        
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set username only
        sdtUserDTO.setUsername("username");
        response = userService.authenticate(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());

        //set password only
        sdtUserDTO.setUsername(null);
        sdtUserDTO.setPassword("password");
        response = userService.authenticate(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());   
        
        //set username and password
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword("password");
        response = userService.authenticate(sdtUserDTO);
        //it should be successful
        Assert.assertEquals(ConstantValues.SDT_USER_STATUS_AUTHENTICATED, response.getMessage());
        
    }
    
    @Test
    public void testResetPassword() throws AppException, CustomReasonPhraseException {
        //create SDTUser
        SdtUser sdtUser = new SdtUser(null, "username", null, null, null);
        
        //mocking the authenticate query
        Map<String, Object> params = new HashMap<>();
        params.put("username", "username");        
        Mockito.when(sdtUserFacade.findSingleByQueryName(ConstantValues.SDT_USER_QUERY_FIND_BY_USERNAME, params)).thenReturn(sdtUser);
        
        //create SDTUserDTO with no username
        SdtUserDTO sdtUserDTO = new SdtUserDTO();        
        RESTReponse response = userService.resetPassword(sdtUserDTO);        
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_NO_USERNAME, response.getMessage());
        
        //set username incorrectly
        sdtUserDTO.setUsername("wrong");
        response = userService.resetPassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME, response.getMessage());
        
        //set username correctly
        sdtUserDTO.setUsername("username");
        response = userService.resetPassword(sdtUserDTO);
        //it should be successful
        Assert.assertEquals(ConstantValues.SDT_USER_STATUS_PASSWORD_CHANGE, response.getMessage());
    }
    
    @Test
    public void testChangePassword() throws AppException, CustomReasonPhraseException {
        //create SDTUser
        SdtUser sdtUser = new SdtUser("oldpassword", "username", null, null, null);
        
        //mocking the authenticate query
        Map<String, Object> params = new HashMap<>();
        params.put("username", "username");   
        params.put("password", "oldpassword");
        Mockito.when(sdtUserFacade.findSingleByQueryName(ConstantValues.SDT_USER_QUERY_AUTHENTICATE, params)).thenReturn(sdtUser);
        
        //create SDTUserDTO with no username or password or old password
        SdtUserDTO sdtUserDTO = new SdtUserDTO();          
        //it should be failed
        RESTReponse response = userService.changePassword(sdtUserDTO);
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set username only
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword(null);
        sdtUserDTO.setOldPassword(null);
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set password only
        sdtUserDTO.setUsername(null);
        sdtUser.setPassword("newpassword");
        sdtUserDTO.setOldPassword(null);
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set old password only
        sdtUserDTO.setUsername(null);
        sdtUserDTO.setPassword(null);
        sdtUserDTO.setOldPassword("oldpassword");       
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set username and password only
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword("newpassword");
        sdtUserDTO.setOldPassword(null);       
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set username and password only
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword("newpassword");
        sdtUserDTO.setOldPassword(null);       
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set username and old password only
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword(null);
        sdtUserDTO.setOldPassword("oldpassword");       
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set new password and old password only
        sdtUserDTO.setUsername(null);
        sdtUserDTO.setPassword("newpassword");
        sdtUserDTO.setOldPassword("oldpassword");       
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, response.getMessage());
        
        //set old password and new password the same
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword("oldpassword");
        sdtUserDTO.setOldPassword("oldpassword");       
        response = userService.changePassword(sdtUserDTO);
        //it should be failed
        Assert.assertEquals(ConstantValues.SDT_USER_ERROR_NEW_OLD_PASSWORD_SAME, response.getMessage());
        
        //set everything correctly
        sdtUserDTO.setUsername("username");
        sdtUserDTO.setPassword("newpassword");
        sdtUserDTO.setOldPassword("oldpassword");       
        response = userService.changePassword(sdtUserDTO);
        //it should be successful
        Assert.assertEquals(ConstantValues.SDT_USER_STATUS_PASSWORD_CHANGE, response.getMessage());
    }
 
}
