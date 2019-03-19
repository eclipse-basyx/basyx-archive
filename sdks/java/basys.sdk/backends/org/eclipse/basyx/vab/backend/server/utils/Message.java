package org.eclipse.basyx.vab.backend.server.utils;

import java.util.HashMap;

/**
 * 
 * @author pschorn
 *
 */
@SuppressWarnings("serial")
public class Message extends HashMap<String, Object> implements IMessage {
	
	public Message(MessageType messageType, String text) {
		
		this(messageType, null, text);
	} 
	
	
	public Message(MessageType messageType, String code, String text) {
		put("messageType", messageType.getId());
		put("code", code);
		put("text", text);
	}

	public String getText() {
		return (String) get("text");
	}

	public String getCode() {
		return (String) get("code");
	}

	public MessageType getMessageType() {
		return MessageType.getById((int) get("messageType"));
	}
	
	public String toString() {

		if (getCode()==null | getCode().isEmpty()){
			return getMessageType().getId() + " | " + getText();
		} else {
			return getMessageType().getId() + " | " + getCode() + " - " + getText();
		}
	}

}
