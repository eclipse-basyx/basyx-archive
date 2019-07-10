package org.eclipse.basyx.vab.backend.server.utils;

import java.util.List;

/**
 * 
 * @author pschorn
 *
 */
public interface IResult {

	public Class<?> getEntityType();
	
	public Object getEntity();
	
	public boolean success();
	
	public boolean isException();
	
	public List<IMessage> getMessages();
}
