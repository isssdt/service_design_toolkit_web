/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import javax.annotation.PostConstruct;
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
    private String touchPointName;
    private String touchPointChannel;
    private String touchPointDesc;
    private String touchpointAction;
    private Double latitude;
    private Double longitude;
    
    @PostConstruct
	private void init() {
		System.out.println(">>> @PostConstruct: TouchPointModel");
    
        }
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}