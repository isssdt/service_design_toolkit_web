/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.journey;

import common.rest.Utils;
import common.rest.dto.RESTReponse;
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
import journey.dto.TouchPointFieldResearcherDTO;
import journey.ejb.business.JourneyServiceLocal;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_research_work_list_by_journey_name_and_username")
@RequestScoped
public class GetResearchWorkListByJourneyNameAndUsername {
    @EJB
    private JourneyServiceLocal journeyService;
            
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetResearchWorkListByJourneyNameAndUsername
     */
    public GetResearchWorkListByJourneyNameAndUsername() {
    }

    /**
     * Retrieves representation of an instance of rest.journey.GetResearchWorkListByJourneyNameAndUsername
     * @return an instance of common.rest.dto.RESTReponse
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RESTReponse getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * POST method for get list of research work of a Journey that has been done by a User
     * @param touchPointFieldResearcherDTO contains name of the Journey and name of a User     
     * @return Response contains list of research work
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(TouchPointFieldResearcherDTO touchPointFieldResearcherDTO) {
        return Utils.buildResponse(Response.Status.CREATED, journeyService.getTouchPointFiedlResearcherListByJourneyNameAndUsername(
                touchPointFieldResearcherDTO.getTouchpointDTO().getJourneyDTO(), 
                touchPointFieldResearcherDTO.getFieldResearcherDTO().getSdtUserDTO()));
    }
}
