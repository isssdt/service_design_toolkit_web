/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.ejb.view;

import common.ejb.business.ServiceFactory;
import common.view.AbstractView;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "auth_ejb_view_loginView")
@ViewScoped
public class LoginView extends AbstractView implements Serializable {
    @EJB
    ServiceFactory serviceFactory;
    
    private SdtUserDTO sdtUserDTO;

    /**
     * Creates a new instance of LoginView
     */
    public LoginView() {
    }

    public SdtUserDTO getSdtUserDTO() {
        return sdtUserDTO;
    }

    public void setSdtUserDTO(SdtUserDTO sdtUserDTO) {
        this.sdtUserDTO = sdtUserDTO;
    }

    @Override
    public void initController() {
        
    }

    @Override
    public void initData() {
        sdtUserDTO = new SdtUserDTO();
    }

    @Override
    public ServiceFactory getServices() {
        return serviceFactory;
    }
    
}
