/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team8ft.rest.journey;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import team8ft.journey.ejb.JourneyService;
import team8ft.journey.entity.Journey;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_journey")
@RequestScoped
public class Get_journeyResource {

    @Context
    private UriInfo context;
    
    @EJB
    private JourneyService journeyService;

    /**
     * Creates a new instance of Get_journeyResource
     */
    public Get_journeyResource() {
    }

    /**
     * Retrieves representation of an instance of team8ft.rest.journey.Get_journeyResource
     * @return an instance of team8ft.journey.entity.Journey
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Journey getJson() {     
        return journeyService.getJourney();
    }

    /**
     * PUT method for updating or creating an instance of Get_journeyResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Journey content) {
        journeyService.createJourney(content);
    }
}
