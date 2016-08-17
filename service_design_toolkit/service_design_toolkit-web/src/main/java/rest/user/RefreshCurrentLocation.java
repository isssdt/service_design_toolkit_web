/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.user;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import user.dto.FieldResearcherDTO;
import user.ejb.business.FieldResearcherServiceLocal;

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
    private FieldResearcherServiceLocal fieldResearcherService;
    /**
     * Creates a new instance of RefreshCurrentLocation
     */
    public RefreshCurrentLocation() {
    }

    /**
     * Retrieves representation of an instance of rest.user.RefreshCurrentLocation
     * @return an instance of user.dto.FieldResearcherDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FieldResearcherDTO getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RefreshCurrentLocation
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(FieldResearcherDTO content) {
        fieldResearcherService.refreshCurrentLocation(content);
    }
}
