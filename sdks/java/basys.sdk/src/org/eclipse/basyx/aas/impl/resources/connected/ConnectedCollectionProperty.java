package org.eclipse.basyx.aas.impl.resources.connected;

import java.util.Collection;

public abstract class ConnectedCollectionProperty extends ConnectedProperty {

	public abstract void set(Object value) throws Exception;
	public abstract Object get(Object objectRef) throws Exception;
	public abstract void remove(Object objectRef) throws Exception;
	public abstract Collection<Object> getElements() throws Exception;
}
