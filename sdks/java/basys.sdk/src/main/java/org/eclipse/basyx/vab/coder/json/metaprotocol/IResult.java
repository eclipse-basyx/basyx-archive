package org.eclipse.basyx.vab.coder.json.metaprotocol;

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
