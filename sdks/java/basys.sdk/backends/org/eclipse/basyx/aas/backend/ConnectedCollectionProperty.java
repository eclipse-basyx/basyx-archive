package org.eclipse.basyx.aas.backend;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;

/**
 * 
 * @author pschorn
 *
 */
public class ConnectedCollectionProperty extends ConnectedProperty implements ICollectionProperty {

	public ConnectedCollectionProperty(String id, String submodelId, String path, String url, IBasysConnector connector, ConnectedAssetAdministrationShellManager aasMngr) {
		
		// Invoke base constructor
		super(id, submodelId, path, url, connector, aasMngr);
	}

	
	/**
	 * Get item from collection at index @objRef, starting at index 0
	 * @throws Exception 
	 */
	@Override
	public Object get(Integer index) throws Exception {
		
		// Get collection
		Object collection = this.getElement();
		
		// Fetch value at index @objRef
		Object value = null;
		if (collection instanceof List<?>) {
			// May throw IndexOutOfBoundsException
			value = ((List<?>) collection).get((Integer) index);	
		} else {
			throw new TypeMismatchException(this.propertyPath, "Collection");
		}
		
		// Return property value
		return value;
	}
	
	/**
	 * Sets new collection. Overwrites existing values
	 * @param collection
	 * @throws ServerException 
	 */
	@Override
	public void set(Collection<Object> collection) throws ServerException {
		
		// Post data to server
		basysConnector.basysSet(this.modelProviderURL, propertyPath, collection);
		
		// update Cache
		this.setElement(collection);
	}

	/**
	 * Add item to collection
	 * @throws Exception 
	 */
	@Override
	public void add(Object newValue) throws Exception {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "create" , newValue);
		
		// Update cache
		Object collection = this.getElement();
		if (collection instanceof List<?>) {
			
			// Check if element is already inside, delete old value in this case (because it is done in the JOP too)
			((List<?>) collection).remove(newValue);
			
			// Type safe add element to collection
			((List<Object>) collection).add(newValue);
		} else {
			throw new TypeMismatchException(this.propertyPath, "Collection");
		}
	}

	/**
	 * Delete item from collection
	 * @throws Exception 
	 */
	@Override
	public void remove(Object oldValue) throws Exception {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "delete" , oldValue);
		
		// Update cache
		Object collection = this.getElement();
		if (collection instanceof List<?>) {
			
			// Check if element is already inside, delete old value in this case (because it is done in the JOP too)
			((List<?>) collection).remove(oldValue);
		} else {
			throw new TypeMismatchException(this.propertyPath, "Collection");
		}
	}

	/**
	 * return all elements from collection
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getElements() {
		// Get collection
		return (Collection<Object>) this.getElement(); // type safe cast?
	
	}

	/**
	 * return number of elements in collection
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getElementCount() {
		// Get collection size
		return ((Collection<Object>) this.getElement()).size(); // type safe cast?
	
	}

}
