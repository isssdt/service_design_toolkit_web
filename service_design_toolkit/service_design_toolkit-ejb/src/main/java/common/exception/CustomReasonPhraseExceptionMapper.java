/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author longnguyen
 */

@Provider
public class CustomReasonPhraseExceptionMapper implements ExceptionMapper<CustomReasonPhraseException> {

	public Response toResponse(CustomReasonPhraseException bex) {
		return Response.status(new CustomReasonPhraseExceptionStatusType(Status.BAD_REQUEST))
				.entity("Custom Reason Phrase exception occured : " + bex.getMessage())
				.build();
	}

}
