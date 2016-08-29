/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.user;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import user.dto.FieldResearcherListDTO;

/**
 * REST Web Service
 *
 * @author longnguyen
 */
@Path("get_field_researcher_list")
@RequestScoped
public class GetFieldResearcherList {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetFieldResearcherList
     */
    public GetFieldResearcherList() {
    }

    /**
     * Retrieves representation of an instance of rest.user.GetFieldResearcherList
     * @return an instance of user.dto.FieldResearcherListDTO
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FieldResearcherListDTO getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GetFieldResearcherList
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(FieldResearcherListDTO content) {
    }
}
