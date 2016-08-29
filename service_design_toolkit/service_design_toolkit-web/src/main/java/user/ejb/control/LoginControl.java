/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.control;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author longnguyen
 */
@Named(value = "loginControl")
@RequestScoped
public class LoginControl {

    /**
     * Creates a new instance of LoginControl
     */
    public LoginControl() {
    }
    
    public String validateUsername() {
        return "/journey/create.xhtml?faces-redirect=true";
    }
    
}
