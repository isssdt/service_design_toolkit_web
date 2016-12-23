/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.model;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import user.dto.SdtUserDTO;

/**
 *
 * @author longnguyen
 */
@Named(value = "loginModel")
@SessionScoped
public class LoginModel implements Serializable {
    private SdtUserDTO sdtUserDTO;      

    /**
     * Creates a new instance of LoginModel
     */
    public LoginModel() {
    }   
    
    @PostConstruct
    public void init() {
        sdtUserDTO = new SdtUserDTO();
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
