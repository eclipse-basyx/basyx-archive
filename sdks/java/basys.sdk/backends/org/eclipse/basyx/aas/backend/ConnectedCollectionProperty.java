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
	 * @param objRef is expected to be an Integer
	 */
	@Override
	public Object get(Object objRef) {
		
		// Check objRef type valid FIXME this should happen on the server side with a proper exception!
		if (!(objRef instanceof Integer)) {return null;}
		
		// Get collection
		Object collection = this.getElement();
		
		// Fetch value at index @objRef
		Object value = null;
		if (collection instanceof List<?>) {
			try  {
				// Type safe cast to List<?>. If integer cast fails, value is null.
				value = ((List<?>) collection).get((Integer) objRef);
				
				// FIXME exception handling
			} catch (IndexOutOfBoundsException e) { 
				return null;
			}
		}
		
		// Return property value
		return value;
	}

	/**
	 * Sets new collection. Overwrites existing values
	 * @param collection to be set
	 * @throws ServerException 
	 */
	@Override
	public void set(Collection<Object> collection) throws ServerException {
		
		// Set collection on server
		basysConnector.basysSet(this.modelProviderURL, propertyPath, collection);
		
		// update Cache
		this.setElement(collection);
	}

	/**
	 * Add item to collection
	 * 
	 * Note: Whenever more than one overloaded methods can be applied to the argument list, the most specific method is used.
	 * @param the value to be added
	 * @throws Exception 
	 */
	@Override
	public void add(Object newValue) throws ServerException, TypeMismatchException {
		
		// Add value to collection. Need "foo" to distinguish overloaded method
		basysConnector.basysSet(this.modelProviderURL, propertyPath, newValue, "foo");
		
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
	 * @throws ServerException 
	 */
	@Override
	public void remove(Object oldValue) throws ServerException {
		
		// Post data to server
		basysConnector.basysDelete(this.modelProviderURL, propertyPath, oldValue);
		
		// Update cache
		Object collection = this.getElement();
		if (collection instanceof List<?>) {
			
			// Check if element is already inside, delete old value in this case (because it is done in the JOP too)
			((List<?>) collection).remove(oldValue);
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
