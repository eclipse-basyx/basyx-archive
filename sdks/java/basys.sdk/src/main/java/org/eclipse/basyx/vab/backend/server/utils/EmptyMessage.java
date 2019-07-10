package org.eclipse.basyx.vab.backend.server.utils;

/**
 * 
 * @author pschorn
 *
 */
@SuppressWarnings("serial")
public class EmptyMessage extends Message {

	public EmptyMessage() {
		super(MessageType.Information, "Empty");
	}
}
