/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Manish
 */
@Named(value = "touchPointModel")
@SessionScoped
public class TouchPointModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new instance of TouchPointModel
     */
    public TouchPointModel() {
    }
    private String touchPointChannel;
    private String touchPointDesc;
    private String touchpointAction;
    private Double touchpointLatitude;
    private Double touchpointLongitude;
    private Integer touchpointRadius;

    @PostConstruct
	private void init() {
		System.out.println(">>> @PostConstruct: TouchPointModel");
	}
    
    @PreDestroy
	private void destroy() {
		System.out.println(">>> @PreDestry: TouchPointModel");
	}
    public String getTouchPointChannel() {
        return touchPointChannel;
    }

    public void setTouchPointChannel(String touchPointChannel) {
        this.touchPointChannel = touchPointChannel;
    }

    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
    }

    public String getTouchpointAction() {
        return touchpointAction;
    }

    public void setTouchpointAction(String touchpointAction) {
        this.touchpointAction = touchpointAction;
    }

    public Double getTouchpointLatitude() {
        return touchpointLatitude;
    }

    public void setTouchpointLatitude(Double touchpointLatitude) {
        this.touchpointLatitude = touchpointLatitude;
    }

    public Double getTouchpointLongitude() {
        return touchpointLongitude;
    }

    public void setTouchpointLongitude(Double touchpointLongitude) {
        this.touchpointLongitude = touchpointLongitude;
    }

    public Integer getTouchpointRadius() {
        return touchpointRadius;
    }

    public void setTouchpointRadius(Integer touchpointRadius) {
        this.touchpointRadius = touchpointRadius;
    }
    
    public TouchPointModel createCopy() {
        TouchPointModel model = new TouchPointModel();
            model.setTouchPointChannel(touchPointChannel);
            model.setTouchPointDesc(touchPointDesc);
            model.setTouchpointAction(touchpointAction);
            model.setTouchpointLatitude(touchpointLatitude);
            model.setTouchpointLongitude(touchpointLongitude);
            model.setTouchpointRadius(touchpointRadius);
       return model;
    }    
}