package org.eclipse.basyx.vab.backend.type;

import java.util.HashMap;

/**
 * Wrapper class that holds meta-data and entity body
 * 
 * @author pschorn
 *
 */
public class ProtocolWrapper extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	/**
	 * For debugging purposes only!
	 */
	public ProtocolWrapper(Object value) {
		this.put("success", true);
		this.put("isException", false);
		this.put("entityType", "string");

		HashMap<String, Object> messages = new HashMap<String, Object>();
		messages.put("messageType", "msgType");
		messages.put("code", "string");
		messages.put("text", "string");

		this.put("messages", messages);
		this.put("entity", value);
	}

	public ProtocolWrapper(String entityType, String messageType, String code, String text, Object value) {

		this.put("success", true);
		this.put("isException", false);
		this.put("entityType", entityType);

		HashMap<String, Object> messages = new HashMap<String, Object>();
		messages.put("messageType", messageType);
		messages.put("code", code);
		messages.put("text", text);

		this.put("messages", messages);
		this.put("entity", value);

	}

	public ProtocolWrapper(Exception e) {
		this.put("success", false);
		this.put("isException", true);

		HashMap<String, Object> entity = new HashMap<String, Object>();
		entity.put("type", e.getClass().getName());
		entity.put("message", e.getMessage());

		// For debugging only
		HashMap<String, Object> messages = new HashMap<String, Object>();
		messages.put("messageType", "msgType");
		messages.put("code", "string");
		messages.put("text", "string");

		this.put("messages", messages);
		this.put("entityType", "string");
	}

}
