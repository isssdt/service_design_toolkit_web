/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.ejb.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import journey.ejb.model.TouchPointListModel;
import journey.ejb.model.TouchPointModel;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import touchpoint.view.NetworkElement;
import touchpoint.view.TouchPointFlowView;

/**
 *
 * @author samru
 */
@Named(value = "touchPointController")
@SessionScoped
public class TouchPointController implements Serializable {

    private static final long serialVersionUID = 1L;
 

    @Inject
    private TouchPointListModel touchPointListModel;

    @Inject
    private TouchPointModel touchPointModel;
    
    @Inject
    TouchPointFlowView touchPointFlowView;

    @PostConstruct
    public void init() {

    }

    public TouchPointListModel getTouchPointListModel() {
        return touchPointListModel;
    }

    public void setTouchPointListModel(TouchPointListModel touchPointListModel) {
        this.touchPointListModel = touchPointListModel;
    }

    public TouchPointModel getTouchPointModel() {
        return touchPointModel;
    }

    public void setTouchPointModel(TouchPointModel touchPointModel) {
        this.touchPointModel = touchPointModel;
    }

    public TouchPointListModel addTouchPoint() {       
        touchPointListModel.getTouchPointListModel().add(touchPointModel.createCopy());
        addElement();  

        touchPointModel.setChannelDesc(null);
        return touchPointListModel;
    }

    public void addElement() {        
        String X, Y, X1 = null, Y1;
        int a, b;   
        X = touchPointFlowView.getModel().getElements().get(touchPointFlowView.getModel().getElements().size() - 1).getX();
        Y = touchPointFlowView.getModel().getElements().get(touchPointFlowView.getModel().getElements().size() - 1).getY();
        System.out.println("x" + X);
        System.out.println("Y" + Y);
        a = Integer.parseInt(X.split("em")[0]);
        b = Integer.parseInt(Y.split("em")[0]);

        if (a < 40) {
            a = a + 20;
        } else {
            System.out.println("a>60");
            a = 6;
            b = b + 10;
        }

        X1 = a + "em";
        Y1 = b + "em";

        Element touch = new Element(new NetworkElement(touchPointModel.getTouchPointName(),touchPointModel.getTouchPointChannel(), touchPointModel.getChannelDesc()), X1, Y1);

        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        touchPointFlowView.getModel().addElement(touch);

        int size = touchPointFlowView.getModel().getElements().size();

        if (size == 2) {
            touchPointFlowView.getModel().connect(createConnection(touchPointFlowView.getModel().getElements().get(0).getEndPoints().get(0), 
                    touch.getEndPoints().get(0), null));
        } else {
            touchPointFlowView.getModel().connect(createConnection(touchPointFlowView.getModel().getElements().get(size - 2).getEndPoints().get(1), 
                    touch.getEndPoints().get(0), null));
        }

    }

    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
    }

}
