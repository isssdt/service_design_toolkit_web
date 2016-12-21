/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.ejb.view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Leon
 */
@Named(value = "loginView")
@SessionScoped
public class LoginView implements Serializable {
    private Boolean loggedIn = false;

    /**
     * Creates a new instance of LoginView
     */
    public LoginView() {
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
}
