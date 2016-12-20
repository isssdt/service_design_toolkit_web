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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import user.ejb.business.UserServiceLocal;
import user.ejb.model.LoginModel;

/**
 *
 * @author longnguyen
 */
@Named(value = "loginControl")
@RequestScoped
public class LoginController {

    @Inject
    LoginModel loginModel;

    @EJB
    UserServiceLocal userService;

    /**
     * Creates a new instance of LoginControl
     */
    public LoginController() {
    }

    public void authenticate() {
        RESTReponse response = null;
        try {
            response = userService.authenticate(loginModel.getSdtUserDTO());
        } catch (AppException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomReasonPhraseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        if (null != response && ConstantValues.SDT_USER_STATUS_AUTHENTICATED.equals(response.getMessage())) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", loginModel.getSdtUserDTO().getUsername());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
        
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void resetPassword() {
        try {
            userService.resetPassword(loginModel.getSdtUserDTO());
        } catch (AppException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomReasonPhraseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword() {
        try {
            userService.changePassword(loginModel.getSdtUserDTO());
        } catch (AppException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomReasonPhraseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
