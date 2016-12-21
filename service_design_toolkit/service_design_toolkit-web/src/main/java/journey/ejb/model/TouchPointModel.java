/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

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
    
    @PostConstruct
	private void init() {
		System.out.println(">>> @PostConstruct: TouchPointModel");
	}
    
    @PreDestroy
	private void destroy() {
		System.out.println(">>> @PreDestry: TouchPointModel");
	}
    private String touchPointName;
    private String touchPointChannel;
    private String channelDesc;
    private String touchpointAction;
    private Double touchpointLatitude;
    private Double touchpointLongitude;
    private Integer touchpointRadius;

    public String getTouchPointName() {
        return touchPointName;
    }

    public void setTouchPointName(String touchPointName) {
        this.touchPointName = touchPointName;
    }

    public String getTouchPointChannel() {
        return touchPointChannel;
    }

    public void setTouchPointChannel(String touchPointChannel) {
        this.touchPointChannel = touchPointChannel;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
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
            model.setTouchPointName(touchPointName);
            model.setTouchPointChannel(touchPointChannel);
            model.setChannelDesc(channelDesc);
            model.setTouchpointAction(touchpointAction);
            model.setTouchpointLatitude(touchpointLatitude);
            model.setTouchpointLongitude(touchpointLongitude);
            model.setTouchpointRadius(touchpointRadius);
       return model;
    }    
}