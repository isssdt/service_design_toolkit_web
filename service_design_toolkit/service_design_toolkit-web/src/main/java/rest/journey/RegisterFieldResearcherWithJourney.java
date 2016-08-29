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
import journey.dto.JourneyFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("register_field_researcher_with_journey")
@RequestScoped
public class RegisterFieldResearcherWithJourney {

    @Context
    private UriInfo context;
    
    @EJB
    private JourneyServiceLocal journeyService;

    /**
     * Creates a new instance of RegisterFieldResearcherWithJourney
     */
    public RegisterFieldResearcherWithJourney() {
    }

    /**
     * Retrieves representation of an instance of rest.journey.RegisterFieldResearcherWithJourney
     * @return an instance of journey.dto.JourneyFieldResearcherDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JourneyFieldResearcherDTO getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RegisterFieldResearcherWithJourney
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(JourneyFieldResearcherDTO content) {
        journeyService.registerFieldResearcherWithJourney(content);
    }
}