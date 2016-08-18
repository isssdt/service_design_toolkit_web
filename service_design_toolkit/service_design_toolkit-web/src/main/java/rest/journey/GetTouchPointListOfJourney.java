/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.journey;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import journey.dto.JourneyDTO;
import journey.ejb.business.JourneyServiceLocal;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_touch_point_list_of_journey")
@RequestScoped
public class GetTouchPointListOfJourney {

    @Context
    private UriInfo context;
    
    @EJB
    private JourneyServiceLocal journeyService;

    /**
     * Creates a new instance of GetTouchPointListOfJourney
     */
    public GetTouchPointListOfJourney() {
    }

    /**
     * Retrieves representation of an instance of rest.journey.GetTouchPointListOfJourney
     * @param journeyDTO
     * @return an instance of journey.dto.JourneyDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JourneyDTO getJson(JourneyDTO journeyDTO) {
        return journeyService.getTouchPointListOfJourney(journeyDTO);
    }

    /**
     * PUT method for updating or creating an instance of GetTouchPointListOfJourney
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(JourneyDTO content) {
    }
}
