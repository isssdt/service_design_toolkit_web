/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.model;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author longnguyen
 */
@Named(value = "loginModel")
@RequestScoped
public class LoginModel {

    /**
     * Creates a new instance of LoginModel
     */
    public LoginModel() {
    }
    
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
