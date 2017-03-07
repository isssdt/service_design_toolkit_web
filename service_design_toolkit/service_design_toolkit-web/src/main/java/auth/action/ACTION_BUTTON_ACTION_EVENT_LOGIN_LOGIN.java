/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.action;

import auth.ejb.view.LoginView;
import common.action.GenericActionHandler;
import common.constant.ConstantValues;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.dto.RESTReponse;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import user.ejb.business.UserServiceLocal;
import user.ejb.controller.AuthController;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_ACTION_EVENT_LOGIN_LOGIN implements GenericActionHandler<ActionEvent> {

    @Override
    public void execute(AbstractView view, ActionEvent event) {
        LoginView loginView = (LoginView)view;
        UserServiceLocal userService = (UserServiceLocal)view.getServices().getBusinessService(UserServiceLocal.class.toString());
        RESTReponse response = null;
        try {
            response = userService.authenticate(loginView.getSdtUserDTO());            
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.postMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null, null);
        }

        if (null != response && ConstantValues.SDT_USER_STATUS_AUTHENTICATED.equals(response.getMessage())) {
            // get Http Session and store username
            HttpSession session = Utils.getSession();
            session.setAttribute("username", loginView.getSdtUserDTO().getUsername());
            try {            
                Utils.forwardToPage(FacesContext.getCurrentInstance(), ConstantValues.URI_DASHBORAD_PAGE);
            } catch (IOException ex) {
                Logger.getLogger(ACTION_BUTTON_ACTION_EVENT_LOGIN_LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Utils.postMessage(FacesContext.getCurrentInstance(), FacesMessage.SEVERITY_WARN, 
                    ConstantValues.SDT_USER_ERROR_INCORRECT_USERNAME_OR_PASSWORD, null, null, false);           
        }
    }
    
}
