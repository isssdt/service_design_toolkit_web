/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.visualization;

import java.util.List;
import journey.dto.JourneyDTO;
import journey.dto.TouchPointDTO;
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
 * @author longnguyen
 */
public class JourneyVisualizationSnakeMap implements JourneyVisualizationStrategy {

    @Override
    public void visualize(JourneyDTO journeyDTO, Object visualizationModel) {  
        DefaultDiagramModel journeyVisualization = (DefaultDiagramModel)visualizationModel;
        if (null == journeyDTO || null == journeyDTO.getTouchPointListDTO() || null == journeyDTO.getTouchPointListDTO().getTouchPointDTOList() 
                || journeyDTO.getTouchPointListDTO().getTouchPointDTOList().isEmpty()) {
            initNonTouchPointJourney(journeyVisualization);
            return;
        }
        for (TouchPointDTO touchPointDTO : journeyDTO.getTouchPointListDTO().getTouchPointDTOList()) {       
            addElement(journeyVisualization, touchPointDTO);
        }
    }
    
    private void initNonTouchPointJourney(DefaultDiagramModel journeyVisualization) {       
        journeyVisualization.setMaxConnections(-1);

        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        journeyVisualization.setDefaultConnector(connector);

        Element start = new Element(new NetworkElement("Home", "",""), "6em", "2em");
        start.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));

        journeyVisualization.addElement(start);       
    }
    
    private void addElement(DefaultDiagramModel journeyVisualization, TouchPointDTO touchPointDTO) {        
        String X = journeyVisualization.getElements().get(journeyVisualization.getElements().size() - 1).getX();
        String Y = journeyVisualization.getElements().get(journeyVisualization.getElements().size() - 1).getY();
        
        int a = Integer.parseInt(X.split("em")[0]);
        int b = Integer.parseInt(Y.split("em")[0]);

        if (a < 40) {
            a = a + 20;
        } else {
            System.out.println("a>60");
            a = 6;
            b = b + 10;
        }

        String X1 = a + "em";
        String Y1 = b + "em";

        Element touch = new Element(new NetworkElement(touchPointDTO.getTouchPointDesc(), touchPointDTO.getChannelDTO().getChannelName(),
                touchPointDTO.getChannelDescription()), X1, Y1);
        
        if (journeyVisualization.getElements().contains(touch)) {
            return;
        }

        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        touch.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        journeyVisualization.addElement(touch);

        int size = journeyVisualization.getElements().size();

        if (size == 2) {
            journeyVisualization.connect(createConnection(journeyVisualization.getElements().get(0).getEndPoints().get(0),
                    touch.getEndPoints().get(0), null));
        } else {
            journeyVisualization.connect(createConnection(journeyVisualization.getElements().get(size - 2).getEndPoints().get(1),
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
