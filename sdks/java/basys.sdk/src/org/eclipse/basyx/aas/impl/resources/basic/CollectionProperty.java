package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;

public class CollectionProperty extends Property implements ICollectionProperty {

	Collection<Object> collection;
	
	
	
	public CollectionProperty(Collection<Object> collection) {
		super();
		this.collection = collection;
		setCollection(true);
	}

	@Override
	public Object get(Object objRef) {
		return null; //?
	}

	@Override
	public void set(Collection<Object> collection) throws ServerException {
		this.collection.clear();
		this.collection.addAll(collection);
	}

	@Override
	public void add(Object newValue) throws ServerException, TypeMismatchException {
		collection.add(newValue);
	}

	@Override
	public void remove(Object objectRef) throws ServerException {
		collection.remove(objectRef);
	}

	@Override
	public Collection<Object> getElements() {
		return collection;
	}

	@Override
	public int getElementCount() {
		return collection.size();
	}

}
