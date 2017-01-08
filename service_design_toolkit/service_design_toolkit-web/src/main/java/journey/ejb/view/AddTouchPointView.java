/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.ejb.view;

import common.ejb.business.ServiceFactory;
import common.utils.Utils;
import common.view.AbstractView;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import journey.controller.JourneyController;
import journey.dto.JourneyDTO;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import touchpoint.view.NetworkElement;

/**
 *
 * @author longnguyen
 */
@Named(value = "journey_ejb_view_addTouchPointView")
@ViewScoped
public class AddTouchPointView extends AbstractView implements Serializable {
    @EJB
    ServiceFactory serviceFactory;
    
    
    private JourneyDTO journeyDTO;
    private DefaultDiagramModel journeyVisualization;

    /**
     * Creates a new instance of AddTouchPointView
     */
    public AddTouchPointView() {
        super();
    }

    public JourneyDTO getJourneyDTO() {
        return journeyDTO;
    }

    public void setJourneyDTO(JourneyDTO journeyDTO) {
        this.journeyDTO = journeyDTO;
    }

    public DefaultDiagramModel getJourneyVisualization() {
        return journeyVisualization;
    }

    public void setJourneyVisualization(DefaultDiagramModel journeyVisualization) {
        this.journeyVisualization = journeyVisualization;
    }

    @Override
    public void initController() {
        controller = new JourneyController(this);
    }

    @Override
    public void initData() {
        journeyDTO = (JourneyDTO)Utils.getAttributeOfSession(JourneyDTO.class.toString());
        
        journeyVisualization = new DefaultDiagramModel();
        journeyVisualization.setMaxConnections(-1);

        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        journeyVisualization.setDefaultConnector(connector);

        Element start = new Element(new NetworkElement("Home", "",""), "6em", "2em");
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));

        journeyVisualization.addElement(start);
    }    

    @Override
    public ServiceFactory getServices() {
        return serviceFactory;
    }
}
