/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.ejb.controller;

import common.constant.ConstantValues;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Leon
 */
@Named(value = "mainController")
@SessionScoped
public class MainController implements Serializable {

    /**
     * Creates a new instance of MainController
     */
    public MainController() {
    }
    
    public String loginPage() {
        return ConstantValues.URI_LOGIN_PAGE;
    }
    
    public String createJourneyPage() {
        return ConstantValues.URI_CREATE_JOURNEY_PAGE;
    }
    
    public String dashboardPage() {
        return ConstantValues.URI_DASHBORAD_PAGE;
    }
    
    public String changePasswordPage() {
        return ConstantValues.URI_PASSWORD_CHANGE_PAGE;
    }
    
    public String addTouchPointPage() {
        return ConstantValues.URI_ADD_TOUCH_POINT_PAGE;
    }
}
