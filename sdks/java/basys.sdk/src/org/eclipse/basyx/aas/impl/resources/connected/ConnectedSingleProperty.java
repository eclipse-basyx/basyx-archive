package org.eclipse.basyx.aas.impl.resources.connected;

public abstract class ConnectedSingleProperty extends ConnectedProperty {

	@Override
	public abstract void push();

	@Override
	public abstract void pull();
	
	public abstract void set(Object value) throws Exception;
	public abstract Object get() throws Exception;
	
}
