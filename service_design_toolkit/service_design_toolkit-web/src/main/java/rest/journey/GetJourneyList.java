/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.journey;

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
import journey.dto.JourneyDTO;
import journey.dto.JourneyListDTO;
import journey.ejb.business.JourneyService;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_journey_list")
@RequestScoped
public class GetJourneyList {

    @Context
    private UriInfo context;
    
    @EJB
    private JourneyService journeyService;

    /**
     * Creates a new instance of Get_journeyResource
     */
    public GetJourneyList() {
    }

    /**
     * Retrieves representation of an instance of team8ft.rest.journey.GetJourneyList
     * @param content
     * @return an instance of team8ft.journey.entity.Journey
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JourneyListDTO getJson(JourneyDTO content) {     
        return journeyService.getJourneyList(content);
    }

    /**
     * PUT method for updating or creating an instance of GetJourneyList
     * @param journeyListDTO 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(JourneyListDTO journeyListDTO) {
        
    }
}
