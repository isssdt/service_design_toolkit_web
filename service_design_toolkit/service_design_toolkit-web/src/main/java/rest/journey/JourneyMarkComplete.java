/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.journey;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import common.rest.Utils;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import journey.ejb.business.JourneyServiceLocal;
import user.dto.SdtUserDTO;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("journey_mark_complete")
@RequestScoped
public class JourneyMarkComplete {

    @Context
    private UriInfo context;
    
    @EJB
    private JourneyServiceLocal journeyService;

    /**
     * Creates a new instance of JourneyMarkComplete
     */
    public JourneyMarkComplete() {
    }

    /**
     * Retrieves representation of an instance of rest.journey.JourneyMarkComplete
     * @return an instance of user.dto.SdtUserDTO
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(SdtUserDTO content) throws AppException, CustomReasonPhraseException {
        return Utils.buildResponse(Response.Status.CREATED, journeyService.updateStatusOfJourneyForFieldResearcher(content));
    }

    /**
     * PUT method for updating or creating an instance of JourneyMarkComplete
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(SdtUserDTO content) throws AppException, CustomReasonPhraseException {
        return Utils.buildResponse(Response.Status.CREATED, journeyService.updateStatusOfJourneyForFieldResearcher(content));        
    }
}
