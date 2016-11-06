/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.touchpoint;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import java.lang.annotation.Annotation;
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
import touchpoint.ejb.business.TouchPointServiceLocal;
import user.dto.SdtUserDTO;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_touch_point_list_of_registered_journey")
@RequestScoped
public class GetTouchPointListOfRegisteredJourney {

    @Context
    private UriInfo context;
    
    @EJB
    private TouchPointServiceLocal touchPointService;

    /**
     * Creates a new instance of GetTouchPointListOfJourney
     */
    public GetTouchPointListOfRegisteredJourney() {
    }

    /**
     * Retrieves representation of an instance of rest.journey.GetTouchPointListOfRegisteredJourney
     * @param fieldResearcherDTO
     * @return an instance of journey.dto.JourneyDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getJson(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        return Response.status(200)
				.entity(touchPointService.getTouchPointListOfRegisteredJourney(sdtUserDTO), new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();        
    }

    /**
     * PUT method for updating or creating an instance of GetTouchPointListOfRegisteredJourney
     * @param journeyDTO
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        return Response.status(200)
				.entity(touchPointService.getTouchPointListOfRegisteredJourney(sdtUserDTO), new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();     
    }
}
