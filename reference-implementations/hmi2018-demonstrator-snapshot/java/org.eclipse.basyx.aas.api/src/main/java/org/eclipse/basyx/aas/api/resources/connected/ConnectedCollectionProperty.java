package org.eclipse.basyx.aas.api.resources.connected;

import java.util.Collection;

public abstract class ConnectedCollectionProperty extends ConnectedProperty {
		
	public abstract void set(String key, Object value) throws Exception;
	public abstract Object get(String key) throws Exception;
	public abstract void remove(String key) throws Exception;
	public abstract Collection<String> getKeys() throws Exception;
	
}
