/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.visualization;

import journey.dto.JourneyDTO;

/**
 *
 * @author longnguyen
 */
public interface JourneyVisualizationStrategy {
    public void visualize(JourneyDTO journeyDTO, Object visualizationModel);
}
