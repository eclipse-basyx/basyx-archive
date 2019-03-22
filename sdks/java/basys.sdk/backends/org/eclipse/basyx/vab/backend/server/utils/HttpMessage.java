package org.eclipse.basyx.vab.backend.server.utils;

/**
 * HTTP specific message
 * @author pschorn
 *
 */
@SuppressWarnings("serial")
public class HttpMessage extends Message {
	
	public HttpMessage(MessageType messageType, HttpStatusCode httpStatusCode) {
		super(messageType, httpStatusCode.toString(), Integer.toString(httpStatusCode.getCode()));
	}
}
