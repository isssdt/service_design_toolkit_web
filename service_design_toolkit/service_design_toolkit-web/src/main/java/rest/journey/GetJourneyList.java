/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.journey;

import common.exception.AppException;
import common.exception.CustomReasonPhraseException;
import java.lang.annotation.Annotation;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import journey.dto.JourneyDTO;
import journey.ejb.business.JourneyServiceLocal;
import user.dto.SdtUserDTO;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_journey_list_for_register")
@RequestScoped
public class GetJourneyList {

    @Context
    private UriInfo context;
    
    @EJB
    private JourneyServiceLocal journeyService;

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
    public Response getJson() throws AppException, CustomReasonPhraseException {                     
        return Response.status(200)
				.entity(journeyService.findJourneyListForRegister(), new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();    
    }

    /**
     * PUT method for updating or creating an instance of GetJourneyList
     * @param journeyDTO
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {        
        return Response.status(200)
				.entity(journeyService.findJourneyListForRegister(sdtUserDTO), new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();   
    }
}
