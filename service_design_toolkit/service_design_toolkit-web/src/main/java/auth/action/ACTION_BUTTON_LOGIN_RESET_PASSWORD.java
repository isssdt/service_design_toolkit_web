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
public class ACTION_BUTTON_LOGIN_RESET_PASSWORD implements GenericActionHandler<ActionEvent> {

    @Override
    public void execute(AbstractView view, ActionEvent event) {
        RESTReponse response = null;
        try {
            UserServiceLocal userService = (UserServiceLocal) view.getServices().getBusinessService(UserServiceLocal.class.toString());
            LoginView loginView = (LoginView) view;
            response = userService.resetPassword(loginView.getSdtUserDTO());

            if (!ConstantValues.SDT_USER_STATUS_PASSWORD_CHANGE.equals(response.getMessage())) {
                Utils.postMessage(FacesMessage.SEVERITY_WARN, response.getMessage(), null, null);
                return;
            }
            
            Utils.postMessage(FacesMessage.SEVERITY_INFO, response.getMessage(), null, null);
        } catch (AppException | CustomReasonPhraseException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.postMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null, null);
        }   
    }

}
