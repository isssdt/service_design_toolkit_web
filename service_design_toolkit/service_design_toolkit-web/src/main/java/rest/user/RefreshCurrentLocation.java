/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.user;

import common.ejb.business.ServiceFactory;
import common.rest.Utils;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import user.dto.FieldResearcherDTO;
import user.ejb.business.UserServiceLocal;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("refresh_current_location")
@RequestScoped
public class RefreshCurrentLocation {

    @Context
    private UriInfo context;
    
    @EJB
    ServiceFactory factory;
    /**
     * Creates a new instance of RefreshCurrentLocation
     */
    public RefreshCurrentLocation() {
    }

    /**
     * Retrieves representation of an instance of rest.user.RefreshCurrentLocation
     * @param fieldResearcherDTO
     * @return an instance of user.dto.FieldResearcherDTO
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(FieldResearcherDTO fieldResearcherDTO) {
        UserServiceLocal service = (UserServiceLocal)factory.getBusinessService(UserServiceLocal.class.toString());
        return Utils.buildResponse(Response.Status.CREATED, service.refreshCurrentLocation(fieldResearcherDTO));
    }

    /**
     * PUT method for updating or creating an instance of RefreshCurrentLocation
     * @param content representation for the resource
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(FieldResearcherDTO content) {
        throw new UnsupportedOperationException();
    }
}
