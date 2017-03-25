/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.action;

import auth.ejb.view.ChangePasswordView;
import common.action.GenericActionHandler;
import common.constant.ConstantValues;
import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.dto.RESTReponse;
import common.utils.Utils;
import common.view.AbstractView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import user.ejb.business.UserServiceLocal;
import user.ejb.controller.AuthController;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_ACTION_EVENT_CHANGE_PASSWORD_CHANGE implements GenericActionHandler<ActionEvent>{

    @Override
    public void execute(AbstractView view, ActionEvent event) {
        ChangePasswordView changePasswordView = (ChangePasswordView)view;
        UserServiceLocal userService = (UserServiceLocal)view.getServices().getBusinessService(UserServiceLocal.class.toString());
        RESTReponse response = null;
        try {
            response = userService.changePassword(changePasswordView.getSdtUserDTO());
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.postMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null, null);
        }
        
        if (!ConstantValues.SDT_USER_STATUS_PASSWORD_CHANGE.equals(response.getMessage())) {
            Utils.postMessage(FacesMessage.SEVERITY_WARN, response.getMessage(), null, null);            
        }
        else {
            Utils.postMessage(FacesMessage.SEVERITY_INFO, response.getMessage(), null, null);
        }               
    }
    
}
