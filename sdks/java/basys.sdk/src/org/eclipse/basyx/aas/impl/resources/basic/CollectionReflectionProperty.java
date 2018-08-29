package org.eclipse.basyx.aas.impl.resources.basic;

import java.lang.reflect.Field;
import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;

/**
 * Provides reflective access to a collection based on a property
 * 
 * @author schnicke
 *
 */
public class CollectionReflectionProperty extends AbstractReflectionProperty implements ICollectionProperty {

	public CollectionReflectionProperty(Field f, Object o) {
		super(f, o);
		setCollection(true);
	}

	@Override
	public Object get(Object objRef) {
		return null; // TODO: ?
	}

	@Override
	public void set(Collection<Object> collection) throws ServerException {
		try {
			f.set(getObject(), collection);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServerException("Reflection failed");
		}
	}

	@Override
	public void add(Object newValue) throws ServerException, TypeMismatchException {
		getCollection().add(newValue);
	}

	@Override
	public void remove(Object objectRef) throws ServerException {
		getCollection().remove(objectRef);
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
	private Collection<Object> getCollection() throws ServerException {
		try {
			return (Collection<Object>) f.get(getObject());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServerException("Reflection failed");
		}
	}

}
