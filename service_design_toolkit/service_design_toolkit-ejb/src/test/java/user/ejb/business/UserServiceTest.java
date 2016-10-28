/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.business;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
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
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testRefreshCurrentLocation () {
        
    }
    
    @Test
    public void testgetFieldResearcherByName () {
        
    }
    
    
}
