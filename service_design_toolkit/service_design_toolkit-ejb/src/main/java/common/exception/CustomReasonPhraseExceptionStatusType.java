/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.exception;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author longnguyen
 */

public class CustomReasonPhraseExceptionStatusType extends AbstractStatusType{
	
	private static final String CUSTOM_EXCEPTION_REASON_PHRASE = "Custom error message";
	
	public CustomReasonPhraseExceptionStatusType(Status httpStatus) {
		super(httpStatus, CUSTOM_EXCEPTION_REASON_PHRASE);
	}

}