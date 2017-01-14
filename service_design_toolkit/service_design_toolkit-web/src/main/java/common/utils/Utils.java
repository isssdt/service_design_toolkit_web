/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.utils;

import common.visualization.JourneyVisualizationFactory;
import common.visualization.VisualizationAbstractFactory;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Leon
 */
public class Utils {

    public static void forwardToPage(FacesContext facesContext, String uri) throws IOException {
        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        facesContext.getExternalContext().redirect(url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + uri);
    }

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.
                getCurrentInstance().
                getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userid");
        } else {
            return null;
        }
    }

    public static void postMessage(FacesMessage.Severity severity, String summary, String detail, String clientID) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(clientID, message);
    }

    public static void postMessage(FacesContext facesContext, FacesMessage.Severity severity, String summary, String detail, String clientID, boolean redirect) {
        if (redirect) {
            Flash flash = facesContext.getExternalContext().getFlash();
            flash.setKeepMessages(redirect);
            flash.setRedirect(redirect);
        }
        FacesMessage message = new FacesMessage(severity, summary, detail);
        facesContext.addMessage(clientID, message);
    }

    public static void postMessageInDialog(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public static void setAttributeOfSession(Object value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute(value.getClass().toString(), value);
    }

    public static Object getAttributeOfSession(Object value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute(value.getClass().toString());
    }
    
    public static void removeAttributeOfSession(Object value) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.removeAttribute(value.getClass().toString());
    }
    
    public static VisualizationAbstractFactory getFactory(String factory) {
        if (JourneyVisualizationFactory.class.toString().equals(factory)) {
            return new JourneyVisualizationFactory();
        }
        return null;
    }
}
