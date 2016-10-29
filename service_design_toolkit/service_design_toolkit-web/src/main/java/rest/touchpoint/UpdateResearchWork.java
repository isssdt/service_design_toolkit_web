/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.touchpoint;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
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
import javax.ws.rs.core.Response;
import journey.dto.TouchPointFieldResearcherDTO;
import touchpoint.ejb.business.TouchPointServiceLocal;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("update_research_work")
@RequestScoped
public class UpdateResearchWork {
    @EJB
    TouchPointServiceLocal touchPointService;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UpdateResearchWork
     */
    public UpdateResearchWork() {
    }

    /**
     * Retrieves representation of an instance of rest.touchpoint.UpdateResearchWork
     * @return an instance of journey.dto.TouchPointFieldResearcherDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TouchPointFieldResearcherDTO getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of UpdateResearchWork
     * @param content representation for the resource
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(TouchPointFieldResearcherDTO content) throws AppException, CustomReasonPhraseException {
        String message = touchPointService.saveResponse(content);
        return Response.status(Response.Status.CREATED)// 201
				.entity(message)
				.build();
    }
}
