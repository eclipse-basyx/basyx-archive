package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ICollectionProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.PropertyType;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a
 * collection
 * 
 * @author schnicke
 *
 */
public class ConnectedCollectionProperty extends ConnectedProperty implements ICollectionProperty {

	public ConnectedCollectionProperty(VABElementProxy proxy) {
		super(PropertyType.Collection, proxy);
	}

	@Override
	public void set(Collection<Object> collection) throws ServerException {
		try {
			getProxy().setModelPropertyValue(Property.VALUE, collection);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public void add(Object newValue) throws ServerException, TypeMismatchException {
		try {
			getProxy().createValue(Property.VALUE, newValue);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public void remove(Object objectRef) throws ServerException {
		try {
			getProxy().deleteValue(Property.VALUE, objectRef);
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
		return (Collection<Object>) getProxy().getModelPropertyValue(Property.VALUE);
	}
}
