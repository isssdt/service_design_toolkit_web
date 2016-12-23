/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.controller;

import common.constant.ConstantValues;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.dto.RESTReponse;
import common.utils.Utils;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import user.ejb.business.UserServiceLocal;
import user.ejb.model.LoginModel;

/**
 *
 * @author longnguyen
 */
@Named(value = "authControl")
@SessionScoped
public class AuthController implements Serializable {

    @Inject
    LoginModel loginModel;

    @EJB
    UserServiceLocal userService;

    /**
     * Creates a new instance of LoginControl
     */
    public AuthController() {
    }

    public String authenticate() {        
        RESTReponse response = null;
        try {
            response = userService.authenticate(loginModel.getSdtUserDTO());
        } catch (AppException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomReasonPhraseException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }         
        
        if (null != response && ConstantValues.SDT_USER_STATUS_AUTHENTICATED.equals(response.getMessage())) {
            // get Http Session and store username
            HttpSession session = Utils.getSession();
            session.setAttribute("username", loginModel.getSdtUserDTO().getUsername());         
            return ConstantValues.URI_DASHBORAD_PAGE;
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");  
            FacesContext.getCurrentInstance().addMessage(null, message);
            return ConstantValues.URI_LOGIN_PAGE;
        }     
       
    }
    
    public String logout() {
        Utils.getSession().invalidate();
        return ConstantValues.URI_INDEX_PAGE;
    }

    public void resetPassword() {
        try {
            userService.resetPassword(loginModel.getSdtUserDTO());
        } catch (AppException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomReasonPhraseException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword() {
        try {
            userService.changePassword(loginModel.getSdtUserDTO());
        } catch (AppException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomReasonPhraseException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
