/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import journey.ejb.controller.TouchPointController;
import journey.ejb.model.TouchPointModel;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
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

@Named(value = "touchPointFlowView")
@SessionScoped
public class TouchPointFlowView implements Serializable  {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private DefaultDiagramModel model;
     @Inject
    private TouchPointController touchPointController;

    public DiagramModel getModel() {
        return touchPointController.getModel();
    }

}
