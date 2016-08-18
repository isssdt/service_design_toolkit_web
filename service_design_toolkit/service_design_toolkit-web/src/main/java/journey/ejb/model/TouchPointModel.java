/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author longnguyen
 */
@Named(value = "touchPointModel")
@RequestScoped
public class TouchPointModel {

    /**
     * Creates a new instance of TouchPointModel
     */
    public TouchPointModel() {
    }
    
    private String touchPointDesc;
    private Double latitude;
    private Double longitude;

    public String getTouchPointDesc() {
        return touchPointDesc;
    }

    public void setTouchPointDesc(String touchPointDesc) {
        this.touchPointDesc = touchPointDesc;
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
