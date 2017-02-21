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
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import touchpoint.dto.TouchPointDTO;
import touchpoint.ejb.business.TouchPointServiceLocal;
import user.dto.TouchPointFieldResearcherDTO;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("add_touch_point")
@RequestScoped
public class AddTouchPoint {

    @Context
    private UriInfo context;
    
    @EJB
    private TouchPointServiceLocal touchPointService;

    /**
     * Creates a new instance of AddTouchPoint
     */
    public AddTouchPoint() {
    }

    /**
     * Retrieves representation of an instance of rest.touchpoint.AddTouchPoint
     * @return an instance of touchpoint.dto.TouchPointDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TouchPointDTO getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AddTouchPoint
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(TouchPointFieldResearcherDTO content) throws AppException, CustomReasonPhraseException {
        return Utils.buildResponse(Response.Status.CREATED, touchPointService.addTouchPointToJourney(content)); 
    }
}
