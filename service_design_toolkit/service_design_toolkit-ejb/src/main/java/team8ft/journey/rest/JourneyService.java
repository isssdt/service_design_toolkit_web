/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team8ft.journey.rest;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import team8ft.journey.entity.Journey;

/**
 *
 * @author longnguyen
 */
@Stateless
@LocalBean
@Path("journey")
public class JourneyService {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Journey getJourney(@PathParam("name") String name) {
        return new Journey(name);
    }
}
