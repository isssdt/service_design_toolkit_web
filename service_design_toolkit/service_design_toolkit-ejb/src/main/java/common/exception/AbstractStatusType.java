/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.exception;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 *
 * @author longnguyen
 */

public abstract class AbstractStatusType implements StatusType {

	public AbstractStatusType(final Family family, final int statusCode,
                          final String reasonPhrase) {
	    super();
	
	    this.family = family;
	    this.statusCode = statusCode;
	    this.reasonPhrase = reasonPhrase;
	}
	
	protected AbstractStatusType(final Status status,
	                             final String reasonPhrase) {
	    this(status.getFamily(), status.getStatusCode(), reasonPhrase);
	}
	
	@Override
	public Family getFamily() { return family; }
	
	@Override
	public String getReasonPhrase() { return reasonPhrase; }
	
	@Override
	public int getStatusCode() { return statusCode; }

	private final Family family;
	private final int statusCode;
	private final String reasonPhrase;
	
}
