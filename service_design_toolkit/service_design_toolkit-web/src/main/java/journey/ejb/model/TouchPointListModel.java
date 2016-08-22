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
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author longnguyen
 */
@Named(value = "touchPointListModel")
@SessionScoped
public class TouchPointListModel implements Serializable {

    /**
     * Creates a new instance of TouchPointListModel
     */
    public TouchPointListModel() {
    }
    
    private MapModel geoModel;
    
    private Integer no_of_touch_point;
    
    @PostConstruct
    public void init() {
        geoModel = new DefaultMapModel();        
        no_of_touch_point = 0;
    }

    public Integer getNo_of_touch_point() {
        return no_of_touch_point;
    }

    public void setNo_of_touch_point(Integer no_of_touch_point) {
        this.no_of_touch_point = no_of_touch_point;
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }
    
}
