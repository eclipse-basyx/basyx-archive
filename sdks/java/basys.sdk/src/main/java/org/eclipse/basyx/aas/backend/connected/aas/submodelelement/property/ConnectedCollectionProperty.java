package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a
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
			getProxy().updateElementValue(constructPath(Property.VALUE), collection);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public void add(Object newValue) throws ServerException, TypeMismatchException {
		try {
			getProxy().createElement(constructPath(Property.VALUE), newValue);
		} catch (Exception e) {
			throw new ServerException(e.getClass().toString(), e.getMessage());
		}
	}

	@Override
	public void remove(Object objectRef) throws ServerException {
		try {
			getProxy().deleteElement(constructPath(Property.VALUE), objectRef);
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
		return (Collection<Object>) getProxy().readElementValue(constructPath(Property.VALUE));
	}

	@Override
	public void setValue(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueId(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValueId() {
		// TODO Auto-generated method stub
		return null;
	}



}
