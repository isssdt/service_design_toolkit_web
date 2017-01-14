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
public abstract class VisualizationAbstractFactory {
    public abstract JourneyVisualizationStrategy getJourneyVisualization(String strategy);
}
