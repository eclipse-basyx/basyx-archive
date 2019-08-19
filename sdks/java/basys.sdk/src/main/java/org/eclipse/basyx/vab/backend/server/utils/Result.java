package org.eclipse.basyx.vab.backend.server.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Wrapper class that handles meta-data
 * 
 * @author pschorn
 *
 */
@SuppressWarnings("serial")
public class Result  extends HashMap<String, Object> implements IResult {
		
	public static final String SUCCESS = "success";
	public static final String ISEXCEPTION = "isException";
	public static final String MESSAGES = "messages";
	public static final String ENTITY = "entity";
	public static final String ENTITYTYPE = "entityType";

	public Result(boolean success) {
		this(success, null, null, null);
	}
	
	public Result(boolean success, IMessage message) {
		this(success, null, null, new LinkedList<IMessage>(Arrays.asList(message)));
	}
	
	public Result(boolean success, List<IMessage> messages) {
		this(success, null, null, messages);
	}
	
	public Result(boolean success, Object entity,  Class<?> entityType, List<IMessage> messages) {
		put(SUCCESS, success);
		
		if (messages != null) {
			
			List<Map<String, Object>> messageslist = new LinkedList<Map<String, Object>> ();
			for (IMessage msg : messages) {
				
				MessageType type = msg.getMessageType();
				
				if (type.equals(MessageType.Exception)) {
					put(ISEXCEPTION, true); // make sure isException is set!
				}
				
				messageslist.add((Message) msg);
			}
			
			put(MESSAGES, messageslist);
		}
		
		if (entity != null && entityType != null) {
			put(ENTITY, entity);
			put(ENTITYTYPE, entityType.getName());
		}
	}
	
	public Result(IResult result) {
		this(result.success(), result.getEntity(), result.getEntityType(), result.getMessages());
	}
	
	public Result(Exception e) {
		this(false, getMessageListFromException(e));
	}
	
	
	private static List<IMessage> getMessageListFromException(Exception e){
		
		List<IMessage> messageList = new LinkedList<IMessage>();
		
		
		if (e.getCause() != null) {
			messageList.addAll(getMessageListFromException((Exception) e.getCause())); 
		}
		
		// prepare stacktrace
		String trace = "\n";
		for (StackTraceElement s : e.getStackTrace()) {

			trace = trace + " at " + s.toString() + "\n";
		}

		messageList.add(
				new Message(MessageType.Exception, e.getClass().getName() + ": " + trace)); // replace
																															// with
																															// desired
																															// debugging
																															// output
		
		return messageList;
	}

	@Override
	public Class<?> getEntityType() {
		return (Class<?>) get(ENTITYTYPE);
	}

	@Override
	public Object getEntity() {
		return get(ENTITY);
	}

	@Override
	public boolean success() {
		return (boolean) get(SUCCESS);
	}

	@Override
	public boolean isException() {
		return (boolean) get(ISEXCEPTION);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IMessage> getMessages() {
		return (List<IMessage>) get(MESSAGES);
	}

}
