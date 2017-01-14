/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.visualization;

/**
 *
 * @author longnguyen
 */
public class JourneyVisualizationFactory extends VisualizationAbstractFactory {

    @Override
    public JourneyVisualizationStrategy getJourneyVisualization(String strategy) {
        if (JourneyVisualizationSnakeMap.class.toString().equals(strategy)) {
            return new JourneyVisualizationSnakeMap();       
        }
        return null;
    }   
}
