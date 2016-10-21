/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author longnguyen
 */
@Named(value = "touchPointListModel")
@SessionScoped
public class TouchPointListModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of TouchPointListModel
     */
    
    public TouchPointListModel() {
    }
    
   // @Inject
   // private TouchPointModel touchPointModel;
    private List<TouchPointModel> touchPointListModel = new ArrayList();
    private MapModel geoModel;
    private Integer no_of_touch_point;
    
     @PostConstruct
	private void init() {
            geoModel = new DefaultMapModel();        
            no_of_touch_point = 0;
            System.out.println(">>> @PostConstruct: TouchPointListModel");
	}
    
    @PreDestroy
	private void destroy() {
	System.out.println(">>> @PreDestry: TouchPointListModel");
        }
   

    public List<TouchPointModel> getTouchPointListModel() {
        return touchPointListModel;
    }

    public void setTouchPointListModel(List<TouchPointModel> touchPointListModel) {
        this.touchPointListModel = touchPointListModel;
    }
    
    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public Integer getNo_of_touch_point() {
        return no_of_touch_point;
    }

    public void setNo_of_touch_point(Integer no_of_touch_point) {
        this.no_of_touch_point = no_of_touch_point;
    }    
    
    public TouchPointListModel createCopy(){
        TouchPointListModel model = new TouchPointListModel();
        model.setGeoModel(geoModel);
        model.setNo_of_touch_point(no_of_touch_point);
        model.setTouchPointListModel(touchPointListModel);
        return model;
    }
}
