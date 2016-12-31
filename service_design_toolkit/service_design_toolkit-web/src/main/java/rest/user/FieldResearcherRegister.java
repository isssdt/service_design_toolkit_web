/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.user;

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
import user.dto.SdtUserDTO;
import user.ejb.business.UserServiceLocal;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("field_researcher_register")
@RequestScoped
public class FieldResearcherRegister {

    @Context
    private UriInfo context;
    
    @EJB
    private UserServiceLocal userService;

    /**
     * Creates a new instance of FieldResearcherRegister
     */
    public FieldResearcherRegister() {
    }

    /**
     * Retrieves representation of an instance of rest.user.FieldResearcherRegister
     * @param sdtUserDTO
     * @return an instance of user.dto.SdtUserDTO
     * @throws common.exception.AppException
     * @throws common.exception.CustomReasonPhraseException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(SdtUserDTO sdtUserDTO) throws AppException, CustomReasonPhraseException {
        return Utils.buildResponse(Response.Status.CREATED, userService.registerFieldResearcher(sdtUserDTO));
    }

    /**
     * PUT method for updating or creating an instance of FieldResearcherRegister
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(SdtUserDTO content) throws AppException, CustomReasonPhraseException {        
        return Response.status(Response.Status.CREATED)
				.entity(userService.registerFieldResearcher(content), new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();         
    }
}
