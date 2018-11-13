package org.eclipse.basyx.aas.backend.connected.property;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by VWiD containing a
 * collection
 * 
 * @author schnicke
 *
 */
public class ConnectedCollectionProperty extends ConnectedProperty implements ICollectionProperty {

	public ConnectedCollectionProperty(String path, VABElementProxy proxy) {
		super(PropertyType.Collection, path, proxy);
	}

	@Override
	public void set(Collection<Object> collection) throws ServerException {
		try {
			getProxy().updateElementValue(constructPath("value"), collection);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public void add(Object newValue) throws ServerException, TypeMismatchException {
		try {
			getProxy().createElement(constructPath("value"), newValue);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public void remove(Object objectRef) throws ServerException {
		try {
			getProxy().deleteElement(constructPath("value"), objectRef);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public Collection<Object> getElements() throws ServerException {
		return getCollection();
	}

	@Override
	public int getElementCount() throws ServerException {
		return getCollection().size();
	}

	@SuppressWarnings("unchecked")
	private Collection<Object> getCollection() {
		return (Collection<Object>) getProxy().readElementValue(constructPath("value"));
	}

}
