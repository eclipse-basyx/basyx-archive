package org.eclipse.basyx.vab.backend.server.utils;

import java.util.HashMap;

/**
 * 
 * @author pschorn
 *
 */
@SuppressWarnings("serial")
public class Message extends HashMap<String, Object> implements IMessage {
	
	public static final String MESSAGETYPE = "messageType";
	public static final String CODE = "code";
	public static final String TEXT = "text";

	public Message(MessageType messageType, String text) {
		
		this(messageType, null, text);
	} 
	
	
	public Message(MessageType messageType, String code, String text) {
		put(MESSAGETYPE, messageType.getId());
		put(CODE, code);
		put(TEXT, text);
	}

	public String getText() {
		return (String) get(TEXT);
	}

	public String getCode() {
		return (String) get(CODE);
	}

	public MessageType getMessageType() {
		return MessageType.getById((int) get(MESSAGETYPE));
	}
	
	public String toString() {

		if (getCode()==null | getCode().isEmpty()){
			return getMessageType().getId() + " | " + getText();
		} else {
			return getMessageType().getId() + " | " + getCode() + " - " + getText();
		}
	}

}
