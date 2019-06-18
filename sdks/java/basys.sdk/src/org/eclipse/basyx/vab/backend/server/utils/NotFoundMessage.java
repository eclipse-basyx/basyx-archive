package org.eclipse.basyx.vab.backend.server.utils;

@SuppressWarnings("serial")
public class NotFoundMessage extends Message {

	public NotFoundMessage() {
		super(MessageType.Information, "NotFound", "404") ;
		
	}
}
