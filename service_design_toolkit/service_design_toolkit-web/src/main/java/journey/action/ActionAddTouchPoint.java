/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.action;

import common.ScreenTitles;
import common.action.AbstractAction;
import common.controller.AbstractController;
import java.util.ArrayList;
import javax.faces.event.FacesEvent;
import journey.dto.TouchPointDTO;
import journey.ejb.view.AddTouchPointView;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import touchpoint.view.NetworkElement;

/**
 *
 * @author longnguyen
 */
public class ActionAddTouchPoint extends AbstractAction {

    @Override
    protected boolean checkSource(FacesEvent event) {
        return ScreenTitles.SCREEN_COMPONENT_BUTTON_ADD_TOUCH_POINT_ADD_AJAX_ID.equals(event.getComponent().getId());
    }

    @Override
    public void actionHandler(AbstractController controller, FacesEvent event) {                
        AddTouchPointView addTouchPointView = (AddTouchPointView)controller.getView();
        SelectEvent selectEvent = (SelectEvent)event;
        TouchPointDTO touchPointDTO = (TouchPointDTO)selectEvent.getObject();
        
        addElement(addTouchPointView.getJourneyVisualization(), touchPointDTO);
        if (null == addTouchPointView.getJourneyDTO().getTouchPointDTOList()) {
            addTouchPointView.getJourneyDTO().setTouchPointDTOList(new ArrayList<>());           
        }
        addTouchPointView.getJourneyDTO().getTouchPointDTOList().add(touchPointDTO);
    }

    public void addElement(DefaultDiagramModel journeyVisualization, TouchPointDTO touchPointDTO) {        
        String X, Y, X1 = null, Y1;
        int a, b;   
        X = journeyVisualization.getElements().get(journeyVisualization.getElements().size() - 1).getX();
        Y = journeyVisualization.getElements().get(journeyVisualization.getElements().size() - 1).getY();
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

        Element touch = new Element(new NetworkElement(touchPointDTO.getTouchPointDesc(), touchPointDTO.getChannelDTO().getChannelName(), 
                touchPointDTO.getChannelDescription()), X1, Y1);

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
