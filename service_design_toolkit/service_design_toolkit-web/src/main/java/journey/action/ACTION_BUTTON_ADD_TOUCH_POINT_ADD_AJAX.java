/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journey.action;

import common.MasterData;
import common.ScreenTitles;
import common.action.ActionHandler;
import common.utils.Utils;
import common.view.AbstractView;
import common.visualization.JourneyVisualizationFactory;
import common.visualization.JourneyVisualizationSnakeMap;
import java.util.ArrayList;
import javax.faces.event.FacesEvent;
import touchpoint.dto.TouchPointDTO;
import journey.ejb.view.AddTouchPointView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import touchpoint.dto.TouchPointListDTO;

/**
 *
 * @author longnguyen
 */
public class ACTION_BUTTON_ADD_TOUCH_POINT_ADD_AJAX implements ActionHandler {

    @Override
    public void execute(AbstractView view, FacesEvent event) {        
        AddTouchPointView addTouchPointView = (AddTouchPointView) view; 
        SelectEvent selectEvent = (SelectEvent)event;
        TouchPointDTO touchPointDTO = (TouchPointDTO) selectEvent.getObject();
        RequestContext context = RequestContext.getCurrentInstance();
        
        if (MasterData.CHANNEL_WEBSITE.equals(touchPointDTO.getChannelDTO().getChannelName())) {            
            if (null == addTouchPointView.getJourneyDTO().getTouchPointDTOList()) {    
                addTouchPointView.getJourneyDTO().setTouchPointListDTO(new TouchPointListDTO());
                addTouchPointView.getJourneyDTO().getTouchPointListDTO().setTouchPointDTOList(new ArrayList<>());
            }
            touchPointDTO.setLatitude("NONE");
            touchPointDTO.setLongitude("NONE");
            touchPointDTO.setRadius("NONE");
            addTouchPointView.getJourneyDTO().getTouchPointListDTO().getTouchPointDTOList().add(touchPointDTO);            
            Utils.getVisualizationFactory(JourneyVisualizationFactory.class.toString()).
                    getJourneyVisualization(JourneyVisualizationSnakeMap.class.toString())
                    .visualize(addTouchPointView.getJourneyDTO(), addTouchPointView.getJourneyVisualization());            
            Utils.removeAttributeOfSession(touchPointDTO);            
            context.update(ScreenTitles.SCREEN_COMPONENT_DIAGRAM_ADD_TOUCH_POINT_TOUCH_POINT_VISUALIZATION_ID);
        }
        else {
            Utils.setAttributeOfSession(touchPointDTO);
            context.execute(ScreenTitles.SCREEN_COMPONENT_JS_FUNCTION_OPEN_TOUCH_POINT_LOCATION_DIALOG);
        }
    }
}
