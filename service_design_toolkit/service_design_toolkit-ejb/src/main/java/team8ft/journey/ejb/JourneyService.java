/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team8ft.journey.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import team8ft.journey.entity.Journey;

/**
 *
 * @author longnguyen
 */
@Stateless
@LocalBean
public class JourneyService {

    @EJB
    private JourneyFacadeLocal journeyFacade;
    
    public Journey getJourney() {
        return journeyFacade.find(1);
    }
}
