/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.action;

import common.action.ActionHandler;
import common.view.AbstractView;
import java.util.List;
import org.primefaces.model.map.LatLng;
import javax.faces.event.FacesEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.GeocodeResult;
import touchpoint.ejb.view.GeoMapView;
/**
 *
 * @author Manish
 */
//public class ACTION_GMAP_TOUCHPOINT_ONPOINT_SELECT implements ActionHandler{
//    
//    @Override
//    public void onRadiusEntered(AjaxBehaviorEvent event) {
//        Circle circle1 = new Circle(latlng, Double.parseDouble(radius));
//        circle1.setStrokeColor("#d93c3c");
//        circle1.setFillColor("#d93c3c");
//        circle1.setFillOpacity(0.5);
//
//        touchPointLocationModel.addOverlay(circle1);
//    }
//
//    public void onPointSelect(PointSelectEvent event) {
//        latlng = event.getLatLng();
//    }
