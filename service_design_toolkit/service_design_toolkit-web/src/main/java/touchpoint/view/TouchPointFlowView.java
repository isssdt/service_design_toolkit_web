/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchpoint.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author samru
 */
@Named(value = "touchPointFlowView")
@SessionScoped
public class TouchPointFlowView implements Serializable {

    private static final long serialVersionUID = 1L;

    private DefaultDiagramModel model;

    @PostConstruct
    public void init() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);

        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);

        Element start = new Element(new NetworkElement("Home", "",""), "6em", "2em");
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));

        model.addElement(start);
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

}
