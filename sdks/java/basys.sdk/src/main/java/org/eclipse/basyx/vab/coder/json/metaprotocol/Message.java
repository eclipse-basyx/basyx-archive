package org.eclipse.basyx.vab.coder.json.metaprotocol;

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

	@Override
	public String getText() {
		return (String) get(TEXT);
	}

	@Override
	public String getCode() {
		return (String) get(CODE);
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.getById((int) get(MESSAGETYPE));
	}
	
	@Override
	public String toString() {
		String code = getCode();
		if (code == null || code.isEmpty()) {
			return getMessageType().getId() + " | " + getText();
		} else {
			return getMessageType().getId() + " | " + getCode() + " - " + getText();
		}
	}

}
