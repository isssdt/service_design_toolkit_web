/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.view;

import static com.sun.javafx.logging.PulseLogger.addMessage;
import common.constant.ConstantValues;
import common.ejb.business.ServiceFactory;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.Circle;
import touchpoint.dto.TouchPointDTO;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import touchpoint.controller.TouchPointController;

/**
 *
 * @author longnguyen
 */
@Named(value = "touchpoint_ejb_view_GeoMapView")
@ViewScoped
public class GeoMapView extends AbstractView implements Serializable {

    private TouchPointDTO touchPointDTO;
    private String centerGeoMap;
    private MapModel touchPointLocationModel;
    private String radius;
    private LatLng latlng;

    /**
     * Creates a new instance of GeoMapView
     */
    public GeoMapView() {
        super();
    }

    @Override
    public void initController() {
        controller = new TouchPointController(this);
    }

    @Override
    public void initData() {
        touchPointDTO = (TouchPointDTO) Utils.getAttributeOfSession(TouchPointDTO.class.toString());
        centerGeoMap = ConstantValues.CONSTANT_GEO_MAP_CENTER;
        touchPointLocationModel = new DefaultMapModel();
    }

    @Override
    public ServiceFactory getServices() {
        return null;
    }

    public TouchPointDTO getTouchPointDTO() {
        return touchPointDTO;
    }

    public void setTouchPointDTO(TouchPointDTO touchPointDTO) {
        this.touchPointDTO = touchPointDTO;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public MapModel getTouchPointLocationModel() {
        return touchPointLocationModel;
    }

    public void setTouchPointLocationModel(MapModel touchPointLocationModel) {
        this.touchPointLocationModel = touchPointLocationModel;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
}
