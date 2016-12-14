/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import journey.ejb.model.TouchPointListModel;
import journey.ejb.model.TouchPointModel;
import journey.ejb.view.TouchPointFlowView;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author samru
 */
@Named(value = "touchPointController")
@SessionScoped
public class TouchPointController implements Serializable  {
    
    
    private static final long serialVersionUID = 1L;
    
    private DefaultDiagramModel model;
    
     @Inject
    private TouchPointListModel touchPointListModel;
    
    @Inject
    private TouchPointModel touchPointModel;
    
    
    
     @PostConstruct
    public void init() {      
    
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
         
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);
        
        Element start = new Element(new NetworkElement("Home",""), "6em", "2em");
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
       
        model.addElement(start);
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
         System.out.println("inside addTouchPoint "+touchPointModel.createCopy().getTouchPointName());
         
        touchPointListModel.getTouchPointListModel().add(touchPointModel.createCopy());
         System.out.println("in list "+touchPointListModel.getTouchPointListModel().size());
        
        addElement();
         System.out.println("after addition of element ");
         return touchPointListModel;
     }
     
    public void addElement(){
         
      System.out.println("inside add element"+touchPointModel.getTouchPointName());
      String X,Y,X1 = null,Y1;
        int a,b;
        X= model.getElements().get(model.getElements().size()-1).getX();
        Y= model.getElements().get(model.getElements().size()-1).getY();
        System.out.println("x"+X);
        System.out.println("Y"+Y);
        a = Integer.parseInt(X.split("em")[0]);
        b = Integer.parseInt(Y.split("em")[0]);

        if (a<40){
            a = a+20;
             }
        else{
            System.out.println("a>60");
            a=6;
            b = b+10;
        }
        
       
        X1=a+"em";
        Y1=b+"em";
       
        Element touch = new Element(new NetworkElement(touchPointModel.createCopy().getTouchPointName(),touchPointModel.createCopy().getChannelDesc()),X1,Y1);
       
        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        model.addElement(touch);
      
        int size = model.getElements().size();        
        
        if( size == 2){
            
        model.connect(createConnection(model.getElements().get(0).getEndPoints().get(0), touch.getEndPoints().get(0), null));
        }
        else{
            
       
        model.connect(createConnection(model.getElements().get(size-2).getEndPoints().get(1), touch.getEndPoints().get(0), null));
        }
       
        }
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));
         
        if(label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }
         
        return conn;
    }
    
    public class NetworkElement implements Serializable {
         
        private String name;
        private String image;
 
        public NetworkElement() {
        }
 
        public NetworkElement(String name, String image) {
            this.name = name;
            this.image = image;
        }
 
        public String getName() {
            return name;
        }
 
        public void setName(String name) {
            this.name = name;
        }
 
        public String getImage() {
            return image;
        }
 
        public void setImage(String image) {
            this.image = image;
        }
 
        @Override
        public String toString() {
            return name;
        }
         
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }
       
}
