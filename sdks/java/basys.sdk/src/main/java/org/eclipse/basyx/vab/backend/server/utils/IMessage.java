package org.eclipse.basyx.vab.backend.server.utils;

/**
 * 
 * @author pschorn
 *
 */
public interface IMessage {
	
	public MessageType getMessageType();

	public String getText();

	public String getCode();
	
	public String toString();
}
