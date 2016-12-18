/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.model;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "loginModel")
@RequestScoped
public class LoginModel {
    private SdtUserDTO sdtUserDTO;

    /**
     * Creates a new instance of LoginModel
     */
    public LoginModel() {
    }   
    
    @PostConstruct
    public void init() {
        
    }
    
    @PreDestroy
    public void destroy() {
        
    }

    public SdtUserDTO getSdtUserDTO() {
        return sdtUserDTO;
    }

    public void setSdtUserDTO(SdtUserDTO sdtUserDTO) {
        this.sdtUserDTO = sdtUserDTO;
    }   
}
