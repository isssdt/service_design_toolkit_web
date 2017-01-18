/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.touchpoint;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.Utils;
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
import user.dto.TouchPointFieldResearcherDTO;
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
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(TouchPointFieldResearcherDTO content) throws AppException, CustomReasonPhraseException {
        return Utils.buildResponse(Response.Status.CREATED, touchPointService.saveResponse(content)); 
    }

    /**
     * PUT method for updating or creating an instance of UpdateResearchWork
     * @param content representation for the resource
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(TouchPointFieldResearcherDTO content) throws AppException, CustomReasonPhraseException {        
        return Utils.buildResponse(Response.Status.CREATED, touchPointService.saveResponse(content));        
    }
}
