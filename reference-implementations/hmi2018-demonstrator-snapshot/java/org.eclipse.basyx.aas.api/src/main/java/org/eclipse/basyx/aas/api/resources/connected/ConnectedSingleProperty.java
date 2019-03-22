package org.eclipse.basyx.aas.api.resources.connected;

public abstract class ConnectedSingleProperty extends ConnectedProperty {
	
	public abstract void set(Object value) throws Exception;
	public abstract Object get() throws Exception;
	
}
