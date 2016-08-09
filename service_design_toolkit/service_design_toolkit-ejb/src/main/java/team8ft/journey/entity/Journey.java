/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team8ft.journey.entity;

import java.io.Serializable;

/**
 *
 * @author longnguyen
 */
public class Journey implements Serializable {
    private String journey_name;
    
    public Journey() {
        
    }

    public Journey(String journey_name) {
        this.journey_name = journey_name;
    }

    public String getJourney_name() {
        return journey_name;
    }

    public void setJourney_name(String journey_name) {
        this.journey_name = journey_name;
    }
}
