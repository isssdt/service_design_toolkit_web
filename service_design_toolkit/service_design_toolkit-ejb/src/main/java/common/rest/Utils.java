/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.rest;

import java.lang.annotation.Annotation;
import javax.ws.rs.core.Response;

/**
 *
 * @author longnguyen
 */
public class Utils {
    public static Response buildResponse(Response.Status status, Object object) {
        return Response.status(status)
				.entity(object, new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build(); 
    }
}
