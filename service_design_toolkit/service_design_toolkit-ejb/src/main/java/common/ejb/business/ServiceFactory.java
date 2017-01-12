/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.ejb.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import journey.ejb.business.JourneyServiceLocal;
import touchpoint.ejb.business.TouchPointServiceLocal;
import user.ejb.business.UserServiceLocal;

/**
 *
 * @author longnguyen
 */
@Stateless
@LocalBean
public class ServiceFactory {
    @EJB
    JourneyServiceLocal journeyService;
    
    @EJB
    TouchPointServiceLocal touchPointService;
    
    @EJB
    UserServiceLocal userService;

    public BusinessService getBusinessService(String className) {
        if (className.equals(JourneyServiceLocal.class.toString())) {
            return journeyService;
        }
        if (className.equals(TouchPointServiceLocal.class.toString())) {
            return touchPointService;
        }
        if (className.equals(UserServiceLocal.class.toString())) {
            return userService;
        }
        return null;
    }

    
}
