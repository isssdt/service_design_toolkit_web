/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.journey;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.Utils;
import java.lang.annotation.Annotation;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import user.dto.JourneyFieldResearcherDTO;
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
     * @param journeyFieldResearcherDTO
     * @return an instance of journey.dto.JourneyFieldResearcherDTO
     * @throws common.exception.AppException
     * @throws common.exception.CustomReasonPhraseException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(JourneyFieldResearcherDTO journeyFieldResearcherDTO) throws AppException, CustomReasonPhraseException {
        return Utils.buildResponse(Response.Status.CREATED, 
                journeyService.registerFieldResearcherWithJourney(journeyFieldResearcherDTO));
    }

    /**
     * PUT method for updating or creating an instance of RegisterFieldResearcherWithJourney
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(JourneyFieldResearcherDTO content) throws AppException, CustomReasonPhraseException {        
        return Response.status(Response.Status.CREATED)
				.entity(journeyService.registerFieldResearcherWithJourney(content), new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();          
    }
}
