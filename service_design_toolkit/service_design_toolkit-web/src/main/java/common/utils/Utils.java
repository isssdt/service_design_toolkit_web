/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.utils;

import common.constant.ConstantValues;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Leon
 */
public class Utils {
    public static void forwardToPage(String uri) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().dispatch(uri);
    }
    
    public static HttpSession getSession() {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }
       
      public static HttpServletRequest getRequest() {
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().getRequest();
      }
 
      public static String getUserName()
      {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  session.getAttribute("username").toString();
      }
       
      public static String getUserId()
      {
        HttpSession session = getSession();
        if ( session != null )
            return (String) session.getAttribute("userid");
        else
            return null;
      }
      
      public static void postMessage(FacesMessage.Severity severity, String summary, String detail, String clientID) {
          FacesMessage message = new FacesMessage(severity, summary, detail);  
          FacesContext.getCurrentInstance().addMessage(clientID, message);
      }
}
