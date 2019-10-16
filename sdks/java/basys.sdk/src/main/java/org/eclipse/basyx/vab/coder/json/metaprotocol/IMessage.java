package org.eclipse.basyx.vab.coder.json.metaprotocol;

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
